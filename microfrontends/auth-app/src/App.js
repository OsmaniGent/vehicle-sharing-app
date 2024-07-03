import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, useNavigate } from 'react-router-dom';
import { GoogleOAuthProvider, useGoogleLogin } from '@react-oauth/google';
import { Container, Button } from 'react-bootstrap';
import Login from './components/Login';
import Signup from './components/Signup';
import { isAuthenticated } from './utils/auth';
import { gapi } from 'gapi-script';
import './App.css';

const App = () => {
    const clientId = '200532252873-lodfab3bpsak271e3vilnsi0f77sfbak.apps.googleusercontent.com';
    const [isLogin, setIsLogin] = useState(true);
    const navigate = useNavigate();

    const toggleForm = () => {
        setIsLogin(!isLogin);
        navigate(isLogin ? '/signup' : '/login');
    };

    useEffect(() => {
        function start() {
            gapi.client.init({
                clientId: clientId,
                scope: ""
            });
        }
        gapi.load('client:auth2', start);
    }, []);

    return (
        <GoogleOAuthProvider clientId={clientId}>
            <Container className="d-flex justify-content-center align-items-center app-container">
                <div className="w-100 form-container">
                    <div className="d-flex justify-content-center mb-3">
                        <Button onClick={toggleForm} variant="outline-primary">
                            {isLogin ? 'Switch to Signup' : 'Switch to Login'}
                        </Button>
                    </div>
                    <Routes>
                        <Route path="/login" element={<Login />} />
                        <Route path="/signup" element={<Signup onSignupSuccess={() => setIsLogin(true)} />} />
                        <Route path="/dashboard" element={<ProtectedRoute component={Dashboard} />} />
                        <Route path="*" element={<Navigate to={isLogin ? "/login" : "/signup"} />} />
                    </Routes>
                </div>
            </Container>
        </GoogleOAuthProvider>
    );
};

const ProtectedRoute = ({ component: Component }) => {
    return isAuthenticated() ? <Component /> : <Navigate to="/login" />;
};

const Dashboard = () => {
    return <div className="text-center mt-5">Protected Dashboard</div>;
};

const AppWrapper = () => (
    <Router>
        <App />
    </Router>
);

export default AppWrapper;
