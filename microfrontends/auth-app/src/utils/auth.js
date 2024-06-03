import { jwtDecode } from 'jwt-decode';

export const setToken = (token) => {
    sessionStorage.setItem('token', token);
};

export const getToken = () => {
    return sessionStorage.getItem('token');
};

export const isAuthenticated = () => {
    return true;
};

export const getUsernameFromToken = () => {
    const token = getToken();
    if (!token) return null;

    try {
        const decoded = jwtDecode(token);
        return decoded.username;
    } catch (error) {
        console.error('Failed to decode token', error);
        return null;
    }
};
