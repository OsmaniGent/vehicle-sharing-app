import React, { useEffect, useRef } from 'react';
import mapboxgl from 'mapbox-gl';
import axios from 'axios';
import { getRouteDetails } from '../api';

const MapComponent = () => {
    const mapContainer = useRef(null);
    const map = useRef(null);

    useEffect(() => {
        const fetchRoute = async () => {
            try {
                const response = await getRouteDetails('665a2c3794694c21bb7093dc');
                const route = response.data;

                mapboxgl.accessToken = 'pk.eyJ1IjoiZ2VudG9zbWFuaSIsImEiOiJjbHJ6ZDB6NmQxaWtuMmpteDVodnd0NzRwIn0.Ol6l6RCXj1_pKA6-JcYbkg';
                if (map.current) return;
                map.current = new mapboxgl.Map({
                    container: mapContainer.current,
                    style: 'mapbox://styles/mapbox/streets-v12',
                    center: route.geometry.coordinates[0],
                    zoom: 12,
                    attributionControl: false
                });

                map.current.on('load', () => {
                    map.current.addSource('route', {
                        type: 'geojson',
                        data: {
                            type: 'Feature',
                            properties: {},
                            geometry: route.geometry
                        }
                    });

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
                            'line-width': 5
                        }
                    });
                });
            } catch (error) {
                console.error('Error fetching route:', error);
            }
        };

        fetchRoute();
    }, []);

    return <div ref={mapContainer} className="map-container"></div>;
};

export default MapComponent;
