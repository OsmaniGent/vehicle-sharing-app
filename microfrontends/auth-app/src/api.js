import axios from 'axios';

const API_URL = 'http://localhost:8765/USER/user';

// axios.interceptors.request.use(
//   config => {
//       const token = localStorage.getItem('token');
//       if (token) {
//           config.headers.Authorization = `Bearer ${token}`;
//       }
//       return config;
//   },
//   error => {
//       return Promise.reject(error);
//   }
// );

export const loginUser = async (username, password) => {
    const response = await axios.post(`${API_URL}/login`, {
        username,
        hashedPassword: password
    });
    return response;
};

export const addUser = async (user) => {
    try{
        const response = await axios.post(`${API_URL}/addUser`, user);
        if (!response.data || !response.data.id) {
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