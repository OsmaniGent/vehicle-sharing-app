import React, { useEffect, useState } from 'react';
import { getToken, isAuthenticated, setTokenCookie } from '../utils/auth';

const withAuth = (WrappedComponent) => {
    return (props) => {
        const [authChecked, setAuthChecked] = useState(null);
        const [refreshed, setRefreshed] = useState(null);

        useEffect(() => {
            const checkAuth = async () => {
                // Extract token from URL and set it in the cookie
                const urlParams = new URLSearchParams(window.location.search);
                const token = urlParams.get('token');
                if (token) {
                    setTokenCookie(token);
                    setRefreshed(false);
                    console.log('Token set from URL in withAuth:', token);
                    window.history.replaceState({}, document.title, window.location.pathname); // Remove token from URL
                }

                // Check authentication status after setting the token
                const isAuth = isAuthenticated();
                setAuthChecked(isAuth);
            };

            checkAuth();
        }, []);

        useEffect(() => {
            if (authChecked !== null && refreshed === false) {
                if (authChecked) {
                    setRefreshed(true);
                    window.location.href = 'http://localhost:3001';
                }else {
                    window.location.href = 'http://localhost:3000';
                }
            }else if(!getToken()) {
                window.location.href = 'http://localhost:3000';
            }
        }, [authChecked]);

        if (authChecked === null) {
            return <div>Loading...</div>;
        }

        return <WrappedComponent {...props} />;
    };
};

export default withAuth;
