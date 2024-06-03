import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import MapComponent from './components/MapComponent';
import RouteControls from './components/RouteControls';
import './App.css';
import NavbarComponent from './components/NavbarComponent'; 

const App = () => {
  const [routes, setRoutes] = useState([]);
  const [currentSelectedRouteId, setCurrentSelectedRouteId] = useState(null);

  useEffect(() => {
    getAllRoutes();

    const socket = new SockJS('http://localhost:8765/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      debug: (str) => {
        console.log(str);
      },
      onConnect: () => {
        console.log('Connected to WebSocket');
        stompClient.subscribe('/topic/routes', (message) => {
          const newRoute = JSON.parse(message.body);
          setRoutes((prevRoutes) => [newRoute, ...prevRoutes]);
        });
      },
      onDisconnect: () => {
        console.log('Disconnected from WebSocket');
      },
    });

    stompClient.activate();

    return () => {
      stompClient.deactivate();
    };
  }, []);

  const getAllRoutes = async () => {
    try {
      const response = await axios.get('http://localhost:8765/USER/user/allRoutes');
      setRoutes(response.data.filter(route => route.isOpen));
    } catch (error) {
      console.error("Error fetching routes: ", error);
    }
  };

  const handleRouteSelect = (routeId) => {
    setCurrentSelectedRouteId(routeId);
  };

  const acceptRoute = async () => {
    try {
      await axios.post(`http://localhost:8765/USER/user/update/${currentSelectedRouteId}`);
    } catch (error) {
      console.error("Error updating route: ", error);
    }
  };

  const denyRoute = async () => {
    try {
      await axios.delete(`http://localhost:8765/USER/user/delete/${currentSelectedRouteId}`);
    } catch (error) {
      console.error("Error deleting route: ", error);
    }
  };

  return (
    <div className="App animated-container">
      <NavbarComponent />
      <div className="main-container">
        <h2 className="animated-title">Pending Routes</h2>
        <RouteControls 
          currentSelectedRouteId={currentSelectedRouteId} 
          acceptRoute={acceptRoute} 
          denyRoute={denyRoute} 
        />
        <div id="mapsContainer" className="maps-container">
          {routes.map((route, index) => (
            <div key={route.id} className="map-and-info-container">
              <MapComponent 
                route={route} 
                index={index} 
                onRouteSelect={handleRouteSelect} 
                isSelected={route.id === currentSelectedRouteId} 
              />
              <div className="route-info">
                <strong>Passenger:</strong>Genti<br />
                <strong>Date Of Route:</strong> {route.routeDate}<br />
                <strong>Duration:</strong> {route.duration / 60} min<br />
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default App;
