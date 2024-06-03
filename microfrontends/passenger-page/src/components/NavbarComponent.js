import React from 'react';
import { Navbar, Nav, Button, Container } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const NavbarComponent = () => {
    const handleLogout = () => {
        window.location.href = 'http://localhost:3000';
    };

    const handleNavigation = (path) => {
        window.location.href = path;
    };

    return (
        <Navbar bg="light" expand="lg" className="navbar-animated">
            <Container>
                <Navbar.Brand href="#">Route Planner</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Button variant="outline-primary" onClick={() => handleNavigation('http://localhost:3001')} className="m-2">
                            Create a Trip
                        </Button>
                        <Button variant="outline-secondary" onClick={() => handleNavigation('http://localhost:3002')} className="m-2">
                            Join a Trip
                        </Button>
                        <Button variant="outline-info" onClick={() => handleNavigation('http://localhost:3003')} className="m-2">
                            Pending Routes
                        </Button>
                    </Nav>
                    <Nav className="ms-auto">
                        <Button variant="outline-warning" onClick={() => handleNavigation('http://localhost:3004')} className="m-2">
                            Profile
                        </Button>
                        <Button variant="outline-danger" onClick={handleLogout} className="m-2">
                            Logout
                        </Button>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default NavbarComponent;
