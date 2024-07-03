import {jwtDecode} from 'jwt-decode';

export const setTokenCookie = (token) => {
    const expires = new Date();
    expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000));
    document.cookie = `token=${token};expires=${expires.toUTCString()};path=/;SameSite=Lax`;
};

export const getToken = () => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; token=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
};

export const isAuthenticated = () => {
    const token = getToken();
    if (!token) return false;

    try {
        const decoded = jwtDecode(token);
        return decoded.exp * 1000 > Date.now();
    } catch (error) {
        console.error('Failed to decode token', error);
        return false;
    }
};

export const getUsernameFromToken = () => {
    const token = getToken();
    if (!token) return null;

    try {
        const decoded = jwtDecode(token);
        return decoded.sub;
    } catch (error) {
        console.error('Failed to decode token', error);
        return null;
    }
};
