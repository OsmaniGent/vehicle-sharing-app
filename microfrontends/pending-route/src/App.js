import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import MapComponent from './components/MapComponent';
import RouteControls from './components/RouteControls';
import './App.css';
import NavbarComponent from './components/NavbarComponent'; 
import { Alert } from 'react-bootstrap';
import { setToken, getToken, getUserIdFromToken } from './utils/auth';
import withAuth from './components/withAuth';


const axiosInstance = axios.create({
  withCredentials: true,
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const App = () => {
  const [routes, setRoutes] = useState([]);
  const [currentSelectedRouteId, setCurrentSelectedRouteId] = useState(null);
  const [usernames, setUsernames] = useState({});
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    const checkUserRole = async () => {
        try {
            const token = getToken();
            const userRole = await getUserRole(token);
            console.log('User Role:', userRole);
            if (userRole === 'PASSENGER') {
                window.location.href = 'http://localhost:3002';
            }
        } catch (error) {
            console.error('Failed to fetch user role:', error);
        }
    };

    checkUserRole();
}, []);


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
      const response = await axiosInstance.get(`http://localhost:8765/USER/user/allRoutes/${await getUserId()}`);
      const openRoutes = response.data.filter(route => route.isOpen);
      setRoutes(openRoutes);
  
      // Fetch usernames for each passenger
      openRoutes.forEach(route => {
        if (route.stops && route.stops.length > 0) {
          const passengerId = route.stops[route.stops.length - 1].passengerId;
          if (passengerId) {
            getUsernameForPending(passengerId);
          }
        }
      });
    } catch (error) {
      console.error("Error fetching routes: ", error);
      setError("Error fetching routes.");
      setTimeout(() => setError(''), 3000); // Clear error message after 3 seconds
    }
  };
  

  const getUserId = async () => {
    try {
        const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details-username/${getUserIdFromToken()}`);
        console.log('Response:', response);
        return response.data.id;
      } catch (error) {
        console.error("Error fetching user details: ", error);
        throw error;
      }
};

const getUserRole = async () => {
  try {
      const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details-username/${getUserIdFromToken()}`);
      console.log('Response:', response);
      return response.data.role;
    } catch (error) {
      console.error("Error fetching user details: ", error);
      throw error;
    }
};

const getUsernameForPending = async (passengerId) => {
  if (!passengerId) return 'No passenger';
  if (usernames[passengerId]) return usernames[passengerId];

  try {
    const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details/${passengerId}`);
    const username = response.data.username;
    setUsernames(prevUsernames => ({ ...prevUsernames, [passengerId]: username }));
    return username;
  } catch (error) {
    console.error("Error fetching user details: ", error);
    return 'Error fetching username';
  }
};


  const handleRouteSelect = (routeId) => {
    setCurrentSelectedRouteId(routeId);
  };

  const acceptRoute = async () => {
    try {
      await axiosInstance.post(`http://localhost:8765/USER/user/update/${currentSelectedRouteId}`);
      setSuccess("Route accepted successfully.");
      setTimeout(() => setSuccess(''), 3000); // Clear success message after 3 seconds
    } catch (error) {
      console.error("Error updating route: ", error);
      setError("Error accepting route.");
      setTimeout(() => setError(''), 3000); // Clear error message after 3 seconds
    }
  };

  const denyRoute = async () => {
    try {
      await axiosInstance.delete(`http://localhost:8765/USER/user/delete/${currentSelectedRouteId}`);
      setSuccess("Route denied successfully.");
      setTimeout(() => setSuccess(''), 3000); // Clear success message after 3 seconds
    } catch (error) {
      console.error("Error deleting route: ", error);
      setError("Error denying route.");
      setTimeout(() => setError(''), 3000); // Clear error message after 3 seconds
    }
  };

  return (
    <div className="App animated-container">
      <NavbarComponent />
      <div className="main-container">
        {error && <Alert variant="danger">{error}</Alert>}
        {success && <Alert variant="success">{success}</Alert>}
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
                <strong>Passenger:</strong> 
                {route.stops && route.stops.length > 0 ? 
                  (usernames[route.stops[route.stops.length - 1].passengerId] || 'Loading...') 
                  : 'No passenger'}<br />
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

export default withAuth(App);
