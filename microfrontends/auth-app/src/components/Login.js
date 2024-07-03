import React, { useState } from 'react';
import axios from 'axios';
import { useGoogleLogin } from '@react-oauth/google';
import { Form, Button, Alert, Container } from 'react-bootstrap';
import { setTokenCookie } from '../utils/auth';
import {jwtDecode} from 'jwt-decode';
import { loginUser, getUserRole } from '../api';


const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await loginUser(username, password);
            setMessage(`Welcome ${username}`);
            const token = response.data.token;
            const redirectUrl = await getUserRole(username) === 'PASSENGER' ? `http://localhost:3002?token=${token}` : `http://localhost:3001?token=${token}`;
            window.location.href = redirectUrl;
        } catch (error) {
            console.error('Login error:', error);
            setMessage('Login failed. Please check your credentials.');
        }
    };

    const googleLogin = useGoogleLogin({
        onSuccess: async (tokenResponse) => {
            console.log('Google Login Success:', tokenResponse);

            const accessToken = tokenResponse.access_token;
            try {
                const serverResponse = await axios.post('http://localhost:8765/USER/api/auth/google-login', { accessToken });
                console.log('Google login server response:', serverResponse);
                const jwtToken = serverResponse.data.token;
                setTokenCookie(jwtToken);

                const decodedToken = jwtDecode(jwtToken);
                const email = decodedToken.email;
                setMessage(`Login with Google successful! Welcome ${email}`);

                window.location.href = `http://localhost:3002?token=${jwtToken}`;
            } catch (error) {
                console.error('Google login error:', error);
                setMessage('Login with Google failed. Please ensure you have an account.');
            }
        },
        onError: () => {
            setMessage('Login with Google failed.');
        }
    });

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
                <Button onClick={() => googleLogin()} variant="outline-primary">
                    Login with Google
                </Button>
            </div>
        </Container>
    );
};

export default Login;
