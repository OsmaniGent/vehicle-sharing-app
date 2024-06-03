import React, { useState } from 'react';
import { Form, Button, Alert, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { addUser, addCar } from '../api';
import AddCarForm from './AddCarForm';
import './Signup.css';

const Signup = ({ onSignupSuccess }) => {
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('passenger');
    const [carDetails, setCarDetails] = useState(null);
    const [message, setMessage] = useState('');

    const navigate = useNavigate();

    const handleSignup = async (e) => {
        e.preventDefault();
        const user = {
            email,
            username,
            hashedPassword: password,
            role
        };

        try {
            const response = await addUser(user);
            const userId = response.id;

            if (role === 'driver' && carDetails) {
                const carData = { ...carDetails, driverId: userId };
                await addCar(carData);
            }
            setMessage('User registered successfully.');
            setTimeout(() => {
                onSignupSuccess();
                navigate('/login');
            }, 1500);
        } catch (error) {
            console.error('Error during signup:', error.message || error);
            setMessage('Registration failed. Please try again.');
        }
    };

    return (
        <Container className="signup-container border p-4 rounded bg-light shadow animated-container">
            <h2 className="text-center mb-4">Signup</h2>
            <Form onSubmit={handleSignup}>
                <Form.Group controlId="email">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        type="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        size="lg"
                    />
                </Form.Group>
                <Form.Group controlId="username" className="mt-3">
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
                <Form.Group controlId="role" className="mt-4">
                    <Form.Label>Role</Form.Label>
                    <div className="role-options">
                        <Form.Check
                            type="radio"
                            label="Passenger"
                            id="passenger"
                            name="role"
                            value="passenger"
                            checked={role === 'passenger'}
                            onChange={(e) => setRole(e.target.value)}
                        />
                        <Form.Check
                            type="radio"
                            label="Driver"
                            id="driver"
                            name="role"
                            value="driver"
                            checked={role === 'driver'}
                            onChange={(e) => setRole(e.target.value)}
                        />
                    </div>
                </Form.Group>
                {role === 'driver' && (
                    <AddCarForm onCarDetailsChange={setCarDetails} />
                )}
                <Button className="mt-4 animated-button" variant="primary" type="submit" size="lg">
                    Signup
                </Button>
            </Form>
            {message && <Alert className="mt-3" variant={message.includes('successfully') ? 'success' : 'danger'}>{message}</Alert>}
        </Container>
    );
};

export default Signup;
