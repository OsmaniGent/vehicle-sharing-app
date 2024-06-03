import React, { useEffect, useRef, useState } from 'react';
import mapboxgl from 'mapbox-gl';
import * as turf from '@turf/turf';
import 'mapbox-gl/dist/mapbox-gl.css';

mapboxgl.accessToken = 'pk.eyJ1IjoiZ2VudG9zbWFuaSIsImEiOiJjbHJ6ZDB6NmQxaWtuMmpteDVodnd0NzRwIn0.Ol6l6RCXj1_pKA6-JcYbkg';

const MapContainer = ({ route, index, onRouteSelect, isSelected, maps, setMaps, currentSelectedRouteId }) => {
    const mapContainer = useRef(null);
    const [map, setMap] = useState(null);
    const [markersData, setMarkersData] = useState({
        'type': 'FeatureCollection',
        'features': []
    });

    useEffect(() => {
        const initializeMap = () => {
            const map = new mapboxgl.Map({
                container: mapContainer.current,
                style: 'mapbox://styles/mapbox/streets-v12',
                center: route.geometry.coordinates[0],
                zoom: 12
            });

            map.on('load', () => {
                map.addSource('route', {
                    'type': 'geojson',
                    'data': {
                        'type': 'Feature',
                        'properties': {},
                        'geometry': route.geometry
                    }
                });

                map.addLayer({
                    'id': 'route',
                    'type': 'line',
                    'source': 'route',
                    'layout': {
                        'line-join': 'round',
                        'line-cap': 'round'
                    },
                    'paint': {
                        'line-color': '#3887be',
                        'line-width': 5
                    }
                });

                map.addSource('markers', {
                    'type': 'geojson',
                    'data': markersData
                });

                map.addLayer({
                    'id': 'markers',
                    'type': 'circle',
                    'source': 'markers',
                    'paint': {
                        'circle-radius': 6,
                        'circle-color': [
                            'match',
                            ['get', 'type'],
                            'pickupMarker', 'blue',
                            'dropoffMarker', 'red',
                            /* other */ '#ccc'
                        ]
                    }
                });

                setMaps(prevMaps => ({
                    ...prevMaps,
                    [route.id]: { mapInstance: map }
                }));

                setMap(map);

                map.on('click', (e) => {
                    if (currentSelectedRouteId === route.id) {
                        console.log('Map clicked at: ', e.lngLat);
                        addStopToList(e.lngLat, route);
                    }
                });
            });

            map.on('error', (e) => {
                console.error('Map error:', e.error.message);
            });

            return () => map.remove();
        };

        if (mapContainer.current) {
            initializeMap();
        }
    }, [route, setMaps, currentSelectedRouteId, markersData]);

    const addStopToList = (coordinates, route) => {
        const stopType = document.getElementById('stopType').value;
        const clickedPoint = turf.point([coordinates.lng, coordinates.lat]);
        const routePoints = turf.points(route.geometry.coordinates);
        const nearest = turf.nearestPoint(clickedPoint, routePoints);
        const snappedCoords = nearest.geometry.coordinates;

        console.log('Snapped coordinates: ', snappedCoords);

        if (stopType === 'pickUp') {
            document.getElementById('pickupCoordinates').value = snappedCoords.join(',');
            addMarker(snappedCoords, 'pickupMarker');
        } else if (stopType === 'dropOff') {
            document.getElementById('dropoffCoordinates').value = snappedCoords.join(',');
            addMarker(snappedCoords, 'dropoffMarker');
        } else if (stopType === 'both') {
            if (!document.getElementById('pickupCoordinates').value) {
                document.getElementById('pickupCoordinates').value = snappedCoords.join(',');
                addMarker(snappedCoords, 'pickupMarker');
            } else if (!document.getElementById('dropoffCoordinates').value) {
                document.getElementById('dropoffCoordinates').value = snappedCoords.join(',');
                addMarker(snappedCoords, 'dropoffMarker');
            } else {
                document.getElementById('pickupCoordinates').value = snappedCoords.join(',');
                addMarker(snappedCoords, 'pickupMarker');
                document.getElementById('dropoffCoordinates').value = '';
            }
        }
    };

    const addMarker = (coordinates, type) => {
        const newFeature = {
            'type': 'Feature',
            'properties': {
                'type': type
            },
            'geometry': {
                'type': 'Point',
                'coordinates': coordinates
            }
        };

        const updatedMarkers = {
            ...markersData,
            features: [...markersData.features, newFeature]
        };

        setMarkersData(updatedMarkers);

        if (map && map.getSource('markers')) {
            map.getSource('markers').setData(updatedMarkers);
        } else {
            console.error('Markers source is not found');
        }
    };

    useEffect(() => {
        if (map && map.isStyleLoaded() && map.getSource('markers')) {
            map.getSource('markers').setData(markersData);
        }
    }, [markersData, map]);

    return (
        <div className={`route-container ${isSelected ? 'selected' : ''}`} onClick={() => onRouteSelect(route.id)}>
            <div
                ref={mapContainer}
                className={`route-map-container ${isSelected ? 'selected' : ''}`}
                style={{ width: '300px', height: '200px' }}
            />
            <div className="route-info">
                <p><strong>Driver: </strong>Edon</p>
                <p><strong>Distance:</strong> {route.distance / 1000} km</p>
                <p><strong>Cost:</strong> ${route.cost}</p>
            </div>
        </div>
    );
};

export default MapContainer;
