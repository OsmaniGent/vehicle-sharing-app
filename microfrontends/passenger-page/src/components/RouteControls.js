import React, { useEffect } from 'react';

const RouteControls = ({ currentSelectedRouteId, sendStop, onStopAdded }) => {
    const handleAddStop = () => {
        if (currentSelectedRouteId) {
            sendStop(currentSelectedRouteId)
                .then(() => {
                    onStopAdded('Stop added successfully.');
                })
                .catch(() => {
                    alert('Failed to add stop. Please try again.');
                });
        } else {
            alert('Please select a stop first.');
        }
    };

    const toggleStopInputs = () => {
        const stopType = document.getElementById('stopType').value;
        const pickupInput = document.getElementById('pickupCoordinatesInput');
        const dropoffInput = document.getElementById('dropoffCoordinatesInput');

        if (stopType === 'pickUp') {
            pickupInput.style.display = 'block';
            dropoffInput.style.display = 'none';
        } else if (stopType === 'dropOff') {
            pickupInput.style.display = 'none';
            dropoffInput.style.display = 'block';
        } else {
            pickupInput.style.display = 'block';
            dropoffInput.style.display = 'block';
        }
    };

    useEffect(() => {
        toggleStopInputs();
    }, []);

    return (
        <div id="selectedRouteControls" className="animated-container" style={{ display: 'none' }}>
            <label htmlFor="stopType">Stop Type:</label>
            <select id="stopType" onChange={toggleStopInputs}>
                <option value="pickUp">Pick Up</option>
                <option value="dropOff">Drop Off</option>
                <option value="both">Both</option>
            </select>

            <div id="pickupCoordinatesInput" style={{ display: 'none' }}>
                <input type="text" id="pickupCoordinates" placeholder="Pickup location (lat,long)" />
            </div>

            <div id="dropoffCoordinatesInput" style={{ display: 'none' }}>
                <input type="text" id="dropoffCoordinates" placeholder="Dropoff location (lat,long)" />
            </div>

            <button onClick={handleAddStop} className="animated-button">Add Stops</button>
        </div>
    );
};

export default RouteControls;
