import React, { useEffect, useRef } from 'react';
import mapboxgl from 'mapbox-gl';
import './MapComponent.css';

const MapComponent = ({ route, index, onRouteSelect, isSelected }) => {
  const mapContainer = useRef(null);

  useEffect(() => {
    mapboxgl.accessToken = 'pk.eyJ1IjoiZ2VudG9zbWFuaSIsImEiOiJjbHJ6ZDB6NmQxaWtuMmpteDVodnd0NzRwIn0.Ol6l6RCXj1_pKA6-JcYbkg';
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
    });

    return () => map.remove();
  }, [route]);

  return (
    <div 
      id={`route-map-${index}`} 
      ref={mapContainer} 
      className={`route-map-container ${isSelected ? 'selected' : ''}`} 
      onClick={() => onRouteSelect(route.id)}
      style={{ width: '300px', height: '200px' }}
    ></div>
  );
};

export default MapComponent;
