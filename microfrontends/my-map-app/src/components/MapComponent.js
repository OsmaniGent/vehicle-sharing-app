import React, { useEffect, useRef } from 'react';
import mapboxgl from 'mapbox-gl';
import MapboxGeocoder from '@mapbox/mapbox-gl-geocoder';
import '@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css';
import { fetchRoute } from '../api';

mapboxgl.accessToken = 'pk.eyJ1IjoiZ2VudG9zbWFuaSIsImEiOiJjbHJ6ZDB6NmQxaWtuMmpteDVodnd0NzRwIn0.Ol6l6RCXj1_pKA6-JcYbkg';

const MapComponent = ({ onSetStart, onSetEnd, routeData, onGeocoderResult }) => {
    const mapContainer = useRef(null);
    const map = useRef(null);
    const startMarkerRef = useRef(null);
    const endMarkerRef = useRef(null);
    const clickCountRef = useRef(0);

    useEffect(() => {
        if (map.current) return;

        map.current = new mapboxgl.Map({
            container: mapContainer.current,
            style: 'mapbox://styles/mapbox/streets-v12',
            center: [-84.518641, 39.134270],
            zoom: 12
        });

        map.current.addControl(new mapboxgl.NavigationControl());

        const geocoderStart = new MapboxGeocoder({
            accessToken: mapboxgl.accessToken,
            placeholder: 'Enter start location',
            mapboxgl: mapboxgl
        });

        const geocoderEnd = new MapboxGeocoder({
            accessToken: mapboxgl.accessToken,
            placeholder: 'Enter end location',
            mapboxgl: mapboxgl
        });

        const startContainer = document.getElementById('geocoder-start');
        const endContainer = document.getElementById('geocoder-end');

        if (startContainer && endContainer) {
            startContainer.appendChild(geocoderStart.onAdd(map.current));
            endContainer.appendChild(geocoderEnd.onAdd(map.current));
        }

        geocoderStart.on('result', (e) => {
            const placeName = e.result.place_name;
            onGeocoderResult(placeName, 'start');
        });

        geocoderEnd.on('result', (e) => {
            const placeName = e.result.place_name;
            onGeocoderResult(placeName, 'end');
        });

        map.current.on('click', async (e) => {
            const coords = e.lngLat.toArray();
            clickCountRef.current += 1;

            if (clickCountRef.current % 3 === 0) {
                if (startMarkerRef.current) startMarkerRef.current.remove();
                if (endMarkerRef.current) endMarkerRef.current.remove();
                startMarkerRef.current = null;
                endMarkerRef.current = null;
                onSetStart(null);
                onSetEnd(null);
                if (map.current.getSource('route')) {
                    map.current.removeLayer('route');
                    map.current.removeSource('route');
                }
            } else if (clickCountRef.current % 3 === 1) {
                if (startMarkerRef.current) {
                    startMarkerRef.current.setLngLat(coords);
                } else {
                    startMarkerRef.current = new mapboxgl.Marker({ color: 'green' })
                        .setLngLat(coords)
                        .addTo(map.current);
                }
                onSetStart(coords);
            } else if (clickCountRef.current % 3 === 2) {
                if (endMarkerRef.current) {
                    endMarkerRef.current.setLngLat(coords);
                } else {
                    endMarkerRef.current = new mapboxgl.Marker({ color: 'red' })
                        .setLngLat(coords)
                        .addTo(map.current);
                }
                onSetEnd(coords);

                if (startMarkerRef.current) {
                    const startCoords = startMarkerRef.current.getLngLat().toArray();
                    fetchRoute(startCoords, coords).then((route) => {
                        drawRoute(route);
                    });
                }
            }
        });
    }, [onSetStart, onSetEnd, onGeocoderResult]);

    useEffect(() => {
        if (map.current && map.current.isStyleLoaded() && routeData) {
            drawRoute(routeData);
        } else {
            map.current.on('styledata', () => {
                if (routeData) {
                    drawRoute(routeData);
                }
            });
        }
    }, [routeData]);

    const isValidGeoJSON = (data) => {
        if (data && data.type && data.type === 'FeatureCollection' && Array.isArray(data.features)) {
            return data.features.every(feature => feature.type === 'Feature' && feature.geometry);
        }
        return false;
    };

    const drawRoute = (geojsonData) => {
        if (!isValidGeoJSON(geojsonData)) {
            console.error("Input data is not a valid GeoJSON object.");
            return;
        }

        if (map.current.getSource('route')) {
            map.current.getSource('route').setData(geojsonData);
        } else {
            map.current.addSource('route', {
                type: 'geojson',
                data: geojsonData
            });
            if (!map.current.getLayer('route')) {
                map.current.addLayer({
                    id: 'route',
                    type: 'line',
                    source: 'route',
                    layout: {
                        'line-join': 'round',
                        'line-cap': 'round'
                    },
                    paint: {
                        'line-color': '#3887be',
                        'line-width': 5,
                        'line-opacity': 0.75
                    }
                });
            }
        }

        const coordinates = geojsonData.features[0].geometry.coordinates;
        const bounds = coordinates.reduce((bounds, coord) => {
            return bounds.extend(coord);
        }, new mapboxgl.LngLatBounds(coordinates[0], coordinates[0]));

        map.current.fitBounds(bounds, {
            padding: 20
        });

        if (startMarkerRef.current) {
            startMarkerRef.current.setLngLat(coordinates[0]);
        } else {
            startMarkerRef.current = new mapboxgl.Marker({ color: 'green' })
                .setLngLat(coordinates[0])
                .addTo(map.current);
        }

        if (endMarkerRef.current) {
            endMarkerRef.current.setLngLat(coordinates[coordinates.length - 1]);
        } else {
            endMarkerRef.current = new mapboxgl.Marker({ color: 'red' })
                .setLngLat(coordinates[coordinates.length - 1])
                .addTo(map.current);
        }
    };

    return (
        <div>
            <div ref={mapContainer} style={{ width: '100%', height: '500px' }} />
            <div className="geocoder-container">
                <div id="geocoder-start" className="geocoder" />
                <div id="geocoder-end" className="geocoder" />
            </div>
        </div>
    );
};

export default MapComponent;
