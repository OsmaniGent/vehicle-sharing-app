import axios from 'axios';
import { setToken, getToken, getUserIdFromToken, getUsernameFromToken } from './utils/auth';


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

export const getAllRoutes = async () => {
    const response = await axiosInstance.get('http://localhost:8765/ROUTE-FILTERING/filter/allRoutes');
    return response.data.filter(route => route.isOpen);
};

export const filterRoutes = async (coordinates) => {
    const url = `http://localhost:8765/ROUTE-FILTERING/filter/near?longitude=${coordinates[0]}&latitude=${coordinates[1]}`;
    const response = await axiosInstance.get(url);
    return response.data.slice(0, 2);
};

export const sendStop = async (currentSelectedRouteId) => {
    const pickupCoordsInput = document.getElementById('pickupCoordinates');
    const dropoffCoordsInput = document.getElementById('dropoffCoordinates');
    const passengerId = await getUserId();

    const stops = [];
    if (pickupCoordsInput && pickupCoordsInput.value) {
        stops.push({ coords: pickupCoordsInput.value, passengerId: passengerId, type: 'Pick Up' });
    }
    if (dropoffCoordsInput && dropoffCoordsInput.value) {
        stops.push({ coords: dropoffCoordsInput.value, passengerId: passengerId, type: 'Drop Off' });
    }

    console.log("Stops to be added:", stops);

    if (stops.length === 0) {
        alert("Please enter at least one stop.");
        return;
    }

    const formattedStops = stops.map(s => `${s.coords},${s.passengerId},${s.type}`).join(';');

    try {
        const initialRouteResponse = await axiosInstance.get(`http://localhost:8765/ROUTE-FILTERING/filter/details/${currentSelectedRouteId}`);
        await submitStops(initialRouteResponse.data, formattedStops);
    } catch (error) {
        console.error("Error generating route or sending for filtering:", error);
    }
};



export const submitStops = async (initialRoute, stopsParam) => {
    var coordinates = initialRoute.geometry.coordinates;
    var firstCoordinate = coordinates[0];
    var lastCoordinate = coordinates[coordinates.length - 1];

    var stopsDetails = stopsParam.split(';').map(stop => stop.split(','));

    var stopsCoordinates = stopsDetails.map(parts => parts[0] + ',' + parts[1]);

    var typeOfStop = stopsDetails.map(parts => parts[3]);

    console.log("Coordinates:", firstCoordinate, lastCoordinate);
    console.log("Stops Coordinates:", stopsCoordinates);
    console.log("Types of Stops:", typeOfStop);

    let url;
    if (typeOfStop.length === 1 && typeOfStop[0] === 'Pick Up') {
        url = `http://localhost:8081/route/generate?type=driving&start=${stopsCoordinates[0]}&end=${lastCoordinate.join(',')}`;
    } else if (typeOfStop.length === 1 && typeOfStop[0] === 'Drop Off') {
        url = `http://localhost:8081/route/generate?type=driving&start=${firstCoordinate.join(',')}&end=${stopsCoordinates[0]}`;
    } else if (typeOfStop.length === 2) {
        var start = stopsCoordinates[0];
        var end = stopsCoordinates[1];
        url = `http://localhost:8081/route/generate?type=driving&start=${start}&end=${end}`;
    }

    console.log("URL generated for routing:", url);

    if (!url) {
        console.error("Unable to generate URL for routing. Stops provided:", stopsParam);
        return;
    }

    try {
        const response = await axios.get(url);

        const subRouteData = {
            initialRouteId: initialRoute.id,
            driverId: initialRoute.driverId,
            geometry: {
                type: "LineString",
                coordinates: response.data.routes[0].geometry.coordinates
            },
            duration: response.data.routes[0].duration,
            distance: response.data.routes[0].distance,
            seatsAvailable: initialRoute.seatsAvailable - 1,
            maxStopTime: initialRoute.maxStopTime,
            cost: response.data.routes[0].cost,
            expiry: initialRoute.expiry,
            isOpen: initialRoute.isOpen,
            routeDate: initialRoute.routeDate,
            stops: stopsDetails.map(parts => ({
                coordinates: [parts[0], parts[1]],
                passengerId: parts[2],
                stopType: parts[3]
            }))
        };

        const updatedRouteData = {
            initialRouteId: initialRoute.id,
            driverId: initialRoute.driverId,
            geometry: {
                type: "LineString",
                coordinates: initialRoute.geometry.coordinates
            },
            duration: initialRoute.duration,
            distance: initialRoute.distance,
            seatsAvailable: initialRoute.seatsAvailable - 1,
            maxStopTime: initialRoute.maxStopTime,
            cost: initialRoute.cost,
            expiry: initialRoute.expiry,
            isOpen: initialRoute.isOpen,
            routeDate: initialRoute.routeDate,
            stops: stopsDetails.map(parts => ({
                coordinates: [parts[0], parts[1]],
                passengerId: parts[2],
                stopType: parts[3]
            })),
            identifiedCoordinates: initialRoute.identifiedCoordinates
        };

        await sendForFiltering(initialRoute, updatedRouteData, subRouteData);
    } catch (error) {
        console.error("Error generating route or sending for filtering:", error);
    }
};





export const sendForFiltering = async (initialRoute, newRoute, subRouteData) => {
    const url = `http://localhost:8765/ROUTE-FILTERING/filter/duration`;
    const body = {
        initialRoute: initialRoute,
        updatedRoute: newRoute,
        subRoute: subRouteData
    };

    await axiosInstance.post(url, body, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

export const toggleStopInputs = () => {
    const stopTypeSelect = document.getElementById('stopType');
    const pickupInput = document.getElementById('pickupCoordinatesInput');
    const dropoffInput = document.getElementById('dropoffCoordinatesInput');

    if (stopTypeSelect.value === 'pickUp') {
        pickupInput.style.display = 'block';
        dropoffInput.style.display = 'none';
    } else if (stopTypeSelect.value === 'dropOff') {
        pickupInput.style.display = 'none';
        dropoffInput.style.display = 'block';
    } else if (stopTypeSelect.value === 'both') {
        pickupInput.style.display = 'block';
        dropoffInput.style.display = 'block';
    }
};

export const getUserDetails = async (userId) => {
  try {
    const response = await axiosInstance.get(`http://localhost:8765/USER/user/user-details/${userId}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching user details: ", error);
    throw error;
  }
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