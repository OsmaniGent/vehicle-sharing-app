import React, { useEffect, useState, useRef } from 'react';
import { getAllRoutes, filterRoutes, sendStop } from './api';
import MapContainer from './components/MapContainer';
import RouteControls from './components/RouteControls';
import Geocoder from './components/Geocoder';
import NavbarComponent from './components/NavbarComponent';
import './App.css';

const App = () => {
    const [routes, setRoutes] = useState([]);
    const [currentSelectedRouteId, setCurrentSelectedRouteId] = useState(null);
    const [maps, setMaps] = useState({});
    const geocoderContainer = useRef(null);

    useEffect(() => {
        getAllRoutes().then(setRoutes);
    }, []);

    const handleRouteSelect = (routeId) => {
        setCurrentSelectedRouteId(routeId);
        document.getElementById('selectedRouteControls').style.display = 'block';
    };

    const handleFilterRoutes = (coordinates) => {
        filterRoutes(coordinates).then(setRoutes);
    };

    return (
        <div className="App animated-container">
            <NavbarComponent />
            <div className="main-container">
                <h2 className="animated-title">Route Updates</h2>
                <Geocoder containerRef={geocoderContainer} onResult={handleFilterRoutes} />
                <RouteControls currentSelectedRouteId={currentSelectedRouteId} sendStop={sendStop} />
                <div id="mapsContainer" className="maps-container">
                    {routes.length === 0 ? (
                        <p>Loading routes...</p>
                    ) : (
                        routes.map((route, index) => (
                            <div key={route.id} className="map-and-info-container">
                                <MapContainer
                                    key={route.id}
                                    route={route}
                                    index={index}
                                    onRouteSelect={handleRouteSelect}
                                    isSelected={currentSelectedRouteId === route.id}
                                    maps={maps}
                                    setMaps={setMaps}
                                    currentSelectedRouteId={currentSelectedRouteId}
                                />
                                {/* <div className="route-info">
                                    <strong>Driver:</strong>Edoni<br />
                                    <strong>Date Of Route:</strong> {route.routeDate}<br />
                                    <strong>Duration:</strong> {route.duration / 60} min<br />
                                    <strong>Distance:</strong> {route.distance / 1000} min<br />
                                    <strong>Price:</strong> {route.cost} $<br />
                                </div> */}
                            </div>
                        ))
                    )}
                </div>
            </div>
        </div>
    );
};

export default App;
