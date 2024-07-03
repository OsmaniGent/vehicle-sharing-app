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

export const getUserDetails = async (userId) => {
  try {
    const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details-username/${userId}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching user details: ", error);
    throw error;
  }
};

export const changeRole= async (userId) => {
  try {
    const response = await axiosInstance.get(`http://localhost:8765/USER/user/changeRole/${userId}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching user details: ", error);
    throw error;
  }
};

export const addCarForUser = async (carDetails) => {
  try {
    const response = await axiosInstance.post(`http://localhost:8765/USER/car/addCar`, carDetails);
    return response.data;
  } catch (error) {
    console.error("Error adding car: ", error);
    throw error;
  }
};

export const getYears = async () => {
  try {
    const response = await axiosInstance.get('http://localhost:8765/VEHICLE-FUEL/vehicles/years');
    return response.data;
  } catch (error) {
    console.error("Error fetching years: ", error);
    throw error;
  }
};

export const getMakes = async (year) => {
  try {
    const response = await axiosInstance.get(`http://localhost:8765/VEHICLE-FUEL/vehicles/makes?year=${year}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching makes: ", error);
    throw error;
  }
};

export const getModels = async (year, make) => {
  try {
    const response = await axiosInstance.get(`http://localhost:8765/VEHICLE-FUEL/vehicles/models?year=${year}&make=${make}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching models: ", error);
    throw error;
  }
};

export const updateUserRole = (userId, role) => {

  const data = {
    userId: userId,
    role: role,
  };

  return data;

};