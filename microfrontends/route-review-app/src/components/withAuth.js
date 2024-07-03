import React from 'react';
import { Navigate } from 'react-router-dom';
import { isAuthenticated } from '../utils/auth';

const withAuth = (WrappedComponent) => {
    return (props) => {
        console.log('Checking authentication status...');
        if (!isAuthenticated()) {
            console.log('User is not authenticated, redirecting to login...');
            return window.location.href = 'http://localhost:3000';
        }
        console.log('User is authenticated, rendering component...');
        return <WrappedComponent {...props} />;
    };
};

export default withAuth;
