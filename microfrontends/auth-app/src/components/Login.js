import React, { useState } from 'react';
import axios from 'axios';
import { GoogleLogin } from '@react-oauth/google';
import { Form, Button, Alert, Container } from 'react-bootstrap';
import { loginUser } from '../api';
import { setToken } from '../utils/auth';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await loginUser(username, password);
            // const { token } = response.data.jwt;
            // setToken(token);
            setMessage(`Welcome ${username}`);
            const redirectUrl = username === 'Genti' ? 'http://localhost:3002' : 'http://localhost:3001';
            window.location.href = redirectUrl;
        } catch (error) {
            setMessage('Login failed. Please check your credentials.');
        }
    };

    const handleGoogleSuccess = async (response) => {
        const serverResponse = await axios.post('http://localhost:3001/google-login', { token: response.credential });
        const { token } = serverResponse.data;
        setToken(token);
        window.location.href = `http://localhost:3001`;
        setMessage('Login with Google successful!');
    };

    const handleGoogleError = () => {
        setMessage('Login with Google failed.');
    };

    return (
        <Container className="login-container border p-4 rounded bg-light shadow animated-container">
            <h2 className="text-center mb-4 title">Login</h2>
            <Form onSubmit={handleLogin}>
                <Form.Group controlId="username">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Enter your username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                        size="lg"
                    />
                </Form.Group>
                <Form.Group controlId="password" className="mt-3">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        placeholder="Enter your password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        size="lg"
                    />
                </Form.Group>
                <Button className="mt-4 animated-button" variant="primary" type="submit" size="lg">
                    Login
                </Button>
            </Form>
            {message && <Alert className="mt-3" variant="danger">{message}</Alert>}
            <hr />
            <h3 className="text-center">Or login with Google</h3>
            <div className="d-flex justify-content-center">
                <GoogleLogin
                    onSuccess={handleGoogleSuccess}
                    onError={handleGoogleError}
                />
            </div>
        </Container>
    );
};

export default Login;
