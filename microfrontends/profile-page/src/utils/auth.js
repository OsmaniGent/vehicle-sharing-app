import {jwtDecode} from 'jwt-decode';

export const setTokenCookie = (token) => {
    const expires = new Date();
    expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000)); // 1 day expiration
    document.cookie = `token=${token};expires=${expires.toUTCString()};path=/;SameSite=Lax`;
    console.log('Token set in cookie:', token);
};

export const getToken = () => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; token=`);
    if (parts.length === 2) {
        const token = parts.pop().split(';').shift();
        console.log('Token retrieved from cookie:', token);
        return token;
    }
    console.log('No token found in cookie');
    return null;
};

export const isAuthenticated = () => {
    const token = getToken();
    if (!token) return false;

    try {
        const decoded = jwtDecode(token);
        console.log('Token decoded:', decoded);
        return decoded.exp * 1000 > Date.now();
    } catch (error) {
        console.error('Failed to decode token', error);
        return false;
    }
};

export const getUserIdFromToken = () => {
    const token = getToken();
    if (!token) return null;

    try {
        const decoded = jwtDecode(token);
        console.log('User ID retrieved from token:', decoded.sub);
        return decoded.sub;
    } catch (error) {
        console.error('Failed to decode token', error);
        return null;
    }
};
