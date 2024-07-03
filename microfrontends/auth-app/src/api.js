import axios from 'axios';
import { setToken, getToken } from './utils/auth';

const API_URL = 'http://localhost:8765/USER/auth';

const axiosInstance = axios.create({
  baseURL: API_URL,
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

export const loginUser = async (username, password) => {
  const response = await axios.post(`${API_URL}/login`, {
      username,
      hashedPassword: password
  });
  console.log('Response:', response.data);
  const { token } = response.data;
  setTokenCookie(token);
  return response;
};


export const getUserDetails = async (userEmail) => {
    try {
        const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details-email/${userEmail}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching user details: ", error);
        throw error;
    }
};

export const getUserRole = async (username) => {
  try {
      const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details-username/${username}`);
      return response.data.role;
  } catch (error) {
      console.error("Error fetching user details: ", error);
      throw error;
  }
};

export const getUserRoleFromEmail = async (userEmail) => {
  try {
      const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details-email/${userEmail}`);
      return response.data.role;
  } catch (error) {
      console.error("Error fetching user details: ", error);
      throw error;
  }
};

export const addUser = async (user) => {
    try {
        const response = await axios.post(`${API_URL}/register`, user);
        if (!response.data) {
            throw new Error('Invalid response from server: User ID is missing');
        }
        return response;
    } catch (error) {
        console.error('Error adding user:', error);
        throw error;
    }
}

export const addCar = async (carDetails) => {
    try {
      const response = await axios.post(`http://localhost:8765/USER/car/addCar`, carDetails);
      if (!response.data) {
        throw new Error('Invalid response from server: Car data is missing');
      }
      return response;
    } catch (error) {
      console.error("Error adding car: ", error);
      throw error;
    }
};

export const getYears = async () => {
    try {
      const response = await axios.get('http://localhost:8765/VEHICLE-FUEL/vehicles/years');
      return response.data;
    } catch (error) {
      console.error("Error fetching years: ", error);
      throw error;
    }
};

export const getMakes = async (year) => {
    try {
      const response = await axios.get(`http://localhost:8765/VEHICLE-FUEL/vehicles/makes?year=${year}`);
      return response.data;
    } catch (error) {
      console.error("Error fetching makes: ", error);
      throw error;
    }
};

export const getModels = async (year, make) => {
    try {
      const response = await axios.get(`http://localhost:8765/VEHICLE-FUEL/vehicles/models?year=${year}&make=${make}`);
      return response.data;
    } catch (error) {
      console.error("Error fetching models: ", error);
      throw error;
    }
};



export const setTokenCookie = (token) => {
  const expires = new Date();
  expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000));
  document.cookie = `token=${token};expires=${expires.toUTCString()};path=/;SameSite=Lax`;
};