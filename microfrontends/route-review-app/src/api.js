import axios from 'axios';

export const getRouteDetails = async (routeId) => {
    return await axios.get(`http://localhost:8765/ROUTE-FILTERING/filter/details/${routeId}`);
};

export const getAllUsers = async (routeId) => {
    try {
        const response = await axios.get(`http://localhost:8765/USER/user/usersByRoute/${routeId}`);
        console.log('All users:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
};

export const addReview = async (review) => {
    try {
        await axios.post('http://localhost:8765/USER/user/addReview', review);
    } catch (error) {
        console.error('Error adding review:', error);
        throw error;
    }
};