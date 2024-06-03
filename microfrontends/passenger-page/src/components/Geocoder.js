import React, { useEffect } from 'react';
import mapboxgl from 'mapbox-gl';
import MapboxGeocoder from '@mapbox/mapbox-gl-geocoder';
import 'mapbox-gl/dist/mapbox-gl.css';
import '@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css';

mapboxgl.accessToken = 'pk.eyJ1IjoiZ2VudG9zbWFuaSIsImEiOiJjbHJ6ZDB6NmQxaWtuMmpteDVodnd0NzRwIn0.Ol6l6RCXj1_pKA6-JcYbkg';

const Geocoder = ({ onResult, containerRef }) => {
    useEffect(() => {
        const geocoder = new MapboxGeocoder({
            accessToken: mapboxgl.accessToken,
            mapboxgl: mapboxgl,
            placeholder: 'Enter location to filter routes'
        });

        geocoder.on('result', function (e) {
            onResult(e.result.geometry.coordinates);
        });

        containerRef.current.appendChild(geocoder.onAdd());

        return () => {
            geocoder.onRemove();
        };
    }, [onResult, containerRef]);

    return <div ref={containerRef} className="geocoder-container"></div>;
};

export default Geocoder;
