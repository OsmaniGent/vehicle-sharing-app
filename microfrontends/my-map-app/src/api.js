import axios from 'axios';
import { getToken, getUsernameFromToken } from './utils/auth';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8765', // Use consistent base URL
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
  (error) => Promise.reject(error)
);

export const fetchRoute = async (startCoords, endCoords) => {
  const response = await fetch(`http://localhost:8081/route/generate?type=driving&start=${startCoords.join(',')}&end=${endCoords.join(',')}`);
  if (!response.ok) {
    throw new Error('Failed to fetch route');
  }
  const data = await response.json();
  return {
    type: 'FeatureCollection',
    features: [
      {
        type: 'Feature',
        geometry: {
          type: 'LineString',
          coordinates: data.routes[0].geometry.coordinates
        },
        properties: {
          duration: data.routes[0].duration,
          distance: data.routes[0].distance
        }
      }
    ]
  };
};

export const translatePlace = async (placeName) => {
  const names = placeName.split(',').map(name => name.trim());
  console.log('Place names:', names);
  
  let url;
  if (names.length >= 3) {
    const firstPart = names[0].includes(' ') ? names[0].replace(/\s/g, '') : names[0];
    const lastPart = names[names.length - 1];
    const namePlace = `${firstPart},${lastPart}.json`;
    url = `http://localhost:8081/route/translate?place=${namePlace}`;
  } else {
    const firstPart = names[0].includes(' ') ? names[0].replace(/\s/g, '') : names[0];
    const namePlace = `${firstPart}.json`;
    url = `http://localhost:8081/route/translate?place=${namePlace}`;
  }

  console.log('Translating place from:', url);
  const response = await fetch(url);
  if (!response.ok) {
    const errorText = await response.text();
    console.error('Failed to translate place:', errorText);
    throw new Error('Failed to translate place');
  }
  const data = await response.json();
  console.log('Translated place data received:', data);
  return data.features[0].center;
};





export const addRoute = async (routeData) => {
  const url = `/ROUTE-FILTERING/filter/add`;
  const response = await axiosInstance.post(url, routeData, {
    headers: {
      'Content-Type': 'application/json'
    }
  });
  return response.data;
};

export const fetchFuelCost = async (year, make, model) => {
  const url = `/VEHICLE-FUEL/vehicles/mpg?year=${year}&make=${make}&model=${model}`;
  const response = await axiosInstance.get(url);
  return response.data;
};

export const getUserId = async () => {
  try {
      const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details-username/${getUsernameFromToken()}`);
      return response.data.id;
    } catch (error) {
      console.error("Error fetching user details: ", error);
      throw error;
    }
};

export const getUserRole = async () => {
  try {
      const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details-username/${getUsernameFromToken()}`);
      return response.data.role;
    } catch (error) {
      console.error("Error fetching user details: ", error);
      throw error;
    }
};

export const getUserCar = async (userId) => {
  try {
    const response = await axiosInstance.get(`http://localhost:8765/USER/car/getCarByDriverId/${userId}`);
    return response.data;
  }
  catch (error) {
    console.error("Error fetching user car: ", error);
    throw error;
  }
};