export const fetchRoute = async (startCoords, endCoords) => {
    const response = await fetch(`/api/ROUTE-SETTING/route/generate?type=driving&start=${startCoords.join(',')}&end=${endCoords.join(',')}`);
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

export const addRoute = async (routeData) => {
    const response = await fetch(`/api/ROUTE-FILTERING/filter/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(routeData)
    });
    if (!response.ok) {
        throw new Error('Failed to add route');
    }
    return response.json();
};

export const fetchFuelCost = async (year, make, model) => {
    const response = await fetch(`/api/VEHICLE-FUEL/vehicles/mpg?year=${year}&make=${make}&model=${model}`);
    if (!response.ok) {
        throw new Error('Failed to fetch fuel cost');
    }
    return response.json();
};

export const translatePlace = async (placeName) => {
    let names = placeName.split(',');
    names = names.map(name => name.trim());
    const namePlace = names[0] + '.json';
    const response = await fetch(`/api/ROUTE-SETTING/route/translate?place=${namePlace}`);
    if (!response.ok) {
        throw new Error('Failed to translate place');
    }
    const data = await response.json();
    return data.features[0].center;
};
