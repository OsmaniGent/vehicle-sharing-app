import React, { useEffect, useState, useRef } from 'react';
import { getAllRoutes, filterRoutes, sendStop } from './api';
import MapContainer from './components/MapContainer';
import RouteControls from './components/RouteControls';
import Geocoder from './components/Geocoder';
import NavbarComponent from './components/NavbarComponent';
import { Alert } from 'react-bootstrap';
import withAuth from './components/withAuth';
import './App.css';

const App = () => {
    const [routes, setRoutes] = useState([]);
    const [currentSelectedRouteId, setCurrentSelectedRouteId] = useState(null);
    const [maps, setMaps] = useState({});
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
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

    const handleStopAdded = (message) => {
        setSuccess(message);
        setTimeout(() => {
            setSuccess('');
        }, 3000); // Clear success message after 3 seconds
    };

    return (
        <div className="App animated-container">
            <NavbarComponent />
            <div className="main-container">
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}
                <h2 className="animated-title">Route Updates</h2>
                <Geocoder containerRef={geocoderContainer} onResult={handleFilterRoutes} />
                <RouteControls
                    currentSelectedRouteId={currentSelectedRouteId}
                    sendStop={sendStop}
                    onStopAdded={handleStopAdded}
                />
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

export default withAuth(App);
