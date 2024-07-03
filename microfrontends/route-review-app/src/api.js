import axios from 'axios';
import { setToken, getToken } from './utils/auth';


const axiosInstance = axios.create({
  withCredentials: true,
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const getRouteDetails = async (routeId) => {
    return await axiosInstance.get(`http://localhost:8765/ROUTE-FILTERING/filter/details/${routeId}`);
};

export const getAllUsers = async (routeId) => {
    try {
        const response = await axiosInstance.get(`http://localhost:8765/USER/user/usersByRoute/${routeId}`);
        console.log('All users:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
};

export const addReview = async (review) => {
    try {
        await axiosInstance.post('http://localhost:8765/USER/user/addReview', review);
    } catch (error) {
        console.error('Error adding review:', error);
        throw error;
    }
};