import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Container, Row, Col, Button, Alert, Spinner } from 'react-bootstrap';
import MapComponent from './components/MapComponent';
import RouteForm from './components/RouteForm';
import NavbarComponent from './components/NavbarComponent';
import { fetchRoute, addRoute, fetchFuelCost, translatePlace, getUserId, getUserRole, getUserCar } from './api';
import { getToken, setTokenCookie } from './utils/auth';
import withAuth from './components/withAuth';
import './App.css';

const App = () => {
    const [formData, setFormData] = useState({
        startLat: '',
        startLng: '',
        endLat: '',
        endLng: '',
        passengersAllowed: false,
        passengerNumber: 1,
        cost: '',
        timer: '',
        startDate: '2024-06-21T12:00'
    });
    const [routeData, setRouteData] = useState(null);
    const [userCar, setUserCar] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const token = getToken();
                const userRole = await getUserRole(token);
                console.log('User Role:', userRole);
                if (userRole === 'PASSENGER') {
                    window.location.href = 'http://localhost:3002';
                } else {
                    const userId = await getUserId();
                    const car = await getUserCar(userId);
                    setUserCar(car);
                }
            } catch (error) {
                console.error('Failed to fetch user data:', error);
            }
        };

        fetchUserData();
    }, []);

    const handleFieldChange = (field, value) => {
        setFormData(prev => ({ ...prev, [field]: value }));
    };

    const handleSubmit = async () => {
        setLoading(true);
        setError('');
        setSuccess('');
        const { startLat, startLng, endLat, endLng } = formData;
        const startCoords = [parseFloat(startLng), parseFloat(startLat)];
        const endCoords = [parseFloat(endLng), parseFloat(endLat)];

        try {
            const routes = await fetchRoute(startCoords, endCoords);
            await setRouteData(routes);
            await displayRouteDetails(routes);
            setSuccess('Route fetched successfully.');
        } catch (error) {
            console.error('Failed to fetch route:', error);
            setError('Failed to fetch route. Please try again.');
        }
        setLoading(false);
    };

    const handleAddRoute = async () => {
        setLoading(true);
        setSuccess('');
        try {
            const payload = {
                cost: formData.cost,
                distance: routeData.features[0].properties.distance,
                driverId: await getUserId(),
                duration: routeData.features[0].properties.duration,
                expiry: calculateExpiryTime(formData.timer),
                geometry: routeData.features[0].geometry,
                isOpen: true,
                maxStopTime: 1000,  // Replace with actual value if needed
                routeDate: formData.startDate,
                seatsAvailable: formData.passengerNumber
            };

            const response = await addRoute(payload);
            console.log('Route Added Successfully:', response);
            setSuccess('Route added successfully.');
        } catch (error) {
            console.error('Error adding route:', error);
            setError('Error adding route.');
        }
        setLoading(false);
    };

    const handleCalculateFuelCost = async () => {
        setLoading(true);
        setSuccess('');
        try {
            const userId = await getUserId();
            const userCar = await getUserCar(userId);
            let costData = await fetchFuelCost(userCar.carYear, userCar.carMake, userCar.carModel);

            if (!costData || Object.keys(costData).length === 0) {
                costData = await fetchFuelCost(2012, 'Honda', 'Fit');
            }

            const cost = calculateCost(costData, routeData);
            setFormData(prev => ({ ...prev, cost: cost.toString() }));
            setSuccess('Fuel cost calculated successfully.');
        } catch (error) {
            console.error('Error fetching fuel cost:', error);
            setError('Error fetching fuel cost.');
        }
        setLoading(false);
    };

    const calculateCost = (costData, routeData) => {
        if (!routeData || !costData) {
            return 'N/A';
        }
        return ((routeData.features[0].properties.distance / 100) / (costData.avgMpg * 1.6)) * 2.5;
    };

    const calculateExpiryTime = (timer) => {
        const timerValue = parseInt(timer);
        const expiryTime = new Date(new Date().getTime() + timerValue * 60000);
        return expiryTime;
    };

    const handleSetStart = (coords) => {
        setFormData(prev => ({ ...prev, startLat: coords[1], startLng: coords[0] }));
    };

    const handleSetEnd = (coords) => {
        setFormData(prev => ({ ...prev, endLat: coords[1], endLng: coords[0] }));
    };

    const handleGeocoderResult = async (placeName, type) => {
        try {
            const coordinates = await translatePlace(placeName);
            if (type === 'start') {
                await handleSetStart(coordinates);
            } else {
                await handleSetEnd(coordinates);
            }
        } catch (error) {
            console.error('Failed to get coordinates from backend:', error);
        }
    };

    const displayRouteDetails = (routeData) => {
        if (!routeData) return;
        const instructionsElement = document.getElementById('instructions');
        instructionsElement.innerHTML = '';

        if (routeData.features && routeData.features.length > 0) {
            const firstRoute = routeData.features[0];
            instructionsElement.innerHTML += `Duration: ${Math.floor(firstRoute.properties.duration / 60)} minutes<br>`;
            instructionsElement.innerHTML += `Distance: ${Math.floor(firstRoute.properties.distance / 1000)} km<br>`;
        }
    };

    return (
        <Router>
            <div className="App">
                <NavbarComponent />
                <Container className="mt-4">
                    {error && <Alert variant="danger">{error}</Alert>}
                    {success && <Alert variant="success">{success}</Alert>}
                    <Row>
                        <Col md={6}>
                            <MapComponent onSetStart={handleSetStart} onSetEnd={handleSetEnd} routeData={routeData} onGeocoderResult={handleGeocoderResult} />
                        </Col>
                        <Col md={6}>
                            <RouteForm
                                formData={formData}
                                onFieldChange={handleFieldChange}
                                onSubmit={handleSubmit}
                                onCalculateFuelCost={handleCalculateFuelCost}
                                loading={loading}
                                seatsAvailable={userCar ? userCar.seatsAvailable : 1}
                            />
                            <Button className="mt-3" onClick={handleAddRoute} disabled={loading}>
                                {loading ? <Spinner animation="border" size="sm" /> : 'Add Route'}
                            </Button>
                            <div id="instructions" className="mt-3"></div>
                        </Col>
                    </Row>
                </Container>
            </div>
        </Router>
    );
};

export default withAuth(App);
