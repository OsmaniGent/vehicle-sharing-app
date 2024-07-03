import React, { useState, useEffect } from 'react';
import { addCarForUser, getYears, getMakes, getModels, updateUserRole, changeRole } from '../api';
import './AddCarForm.css';

const AddCarForm = ({ userId, onCarAdded }) => {
  const [carDetails, setCarDetails] = useState({
    year: '',
    make: '',
    model: '',
    seatsAvailable: ''
  });

  const [years, setYears] = useState([]);
  const [makes, setMakes] = useState([]);
  const [models, setModels] = useState([]);

  useEffect(() => {
    const fetchYears = async () => {
      try {
        const yearsData = await getYears();
        setYears(yearsData);
      } catch (error) {
        console.error("Error fetching years: ", error);
      }
    };
    fetchYears();
  }, []);

  const handleYearChange = async (e) => {
    const year = e.target.value;
    setCarDetails({ ...carDetails, year, make: '', model: '' });
    setMakes([]);
    setModels([]);
    try {
      const makesData = await getMakes(year);
      setMakes(makesData);
    } catch (error) {
      console.error("Error fetching makes: ", error);
    }
  };

  const handleMakeChange = async (e) => {
    const make = e.target.value;
    const { year } = carDetails;
    setCarDetails({ ...carDetails, make, model: '' });
    setModels([]);
    try {
      const modelsData = await getModels(year, make);
      setModels(modelsData);
    } catch (error) {
      console.error("Error fetching models: ", error);
    }
  };

  const handleModelChange = (e) => {
    const model = e.target.value;
    setCarDetails({ ...carDetails, model });
  };

  const handleSeatsChange = (e) => {
    const seatsAvailable = e.target.value;
    setCarDetails({ ...carDetails, seatsAvailable });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const carData = {
      driverId: userId,
      carYear: carDetails.year,
      carMake: carDetails.make,
      carModel: carDetails.model,
      seatsAvailable: carDetails.seatsAvailable
    };
    try {
      await addCarForUser(carData);
      alert('Car added successfully!');
      alert('Role updated to driver!');
      onCarAdded();
    } catch (error) {
      console.error("Error adding car: ", error);
    }
  };

  return (
    <div className="add-car-form">
      <h3>Add Your Car</h3>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Year:</label>
          <select value={carDetails.year} onChange={handleYearChange} required>
            <option value="">Select Year</option>
            {years.map(year => (
              <option key={year.value} value={year.value}>{year.value}</option>
            ))}
          </select>
        </div>
        {carDetails.year && (
          <div>
            <label>Make:</label>
            <select value={carDetails.make} onChange={handleMakeChange} required>
              <option value="">Select Make</option>
              {makes.map(make => (
                <option key={make.value} value={make.value}>{make.value}</option>
              ))}
            </select>
          </div>
        )}
        {carDetails.make && (
          <div>
            <label>Model:</label>
            <select value={carDetails.model} onChange={handleModelChange} required>
              <option value="">Select Model</option>
              {models.map(model => (
                <option key={model.value} value={model.value}>{model.value}</option>
              ))}
            </select>
          </div>
        )}
        {carDetails.model && (
          <div>
            <label>Seats Available:</label>
            <input
              type="number"
              min="1"
              max="7"
              value={carDetails.seatsAvailable}
              onChange={handleSeatsChange}
              required
            />
          </div>
        )}
        {carDetails.model && carDetails.seatsAvailable && (
          <button type="submit" className="submit-btn">Submit</button>
        )}
      </form>
    </div>
  );
};

export default AddCarForm;
