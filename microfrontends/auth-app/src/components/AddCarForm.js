import React, { useState, useEffect } from 'react';
import { Form, Alert, Container } from 'react-bootstrap';
import { getYears, getMakes, getModels } from '../api';

const AddCarForm = ({ onCarDetailsChange }) => {
    const [years, setYears] = useState([]);
    const [makes, setMakes] = useState([]);
    const [models, setModels] = useState([]);
    const [selectedYear, setSelectedYear] = useState('');
    const [selectedMake, setSelectedMake] = useState('');
    const [selectedModel, setSelectedModel] = useState('');
    const [seatsAvailable, setSeatsAvailable] = useState(1);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchYears = async () => {
            try {
                const response = await getYears();
                setYears(response.map(year => ({ text: year.text, value: year.value })));
            } catch (error) {
                setError('Error fetching years');
            }
        };
        fetchYears();
    }, []);

    const handleYearChange = async (e) => {
        const year = e.target.value;
        setSelectedYear(year);
        setSelectedMake('');
        setSelectedModel('');
        try {
            const response = await getMakes(year);
            setMakes(response.map(make => ({ text: make.text, value: make.value })));
            setModels([]);
        } catch (error) {
            setError('Error fetching makes');
        }
    };

    const handleMakeChange = async (e) => {
        const make = e.target.value;
        setSelectedMake(make);
        setSelectedModel('');
        try {
            const response = await getModels(selectedYear, make);
            setModels(response.map(model => ({ text: model.text, value: model.value })));
        } catch (error) {
            setError('Error fetching models');
        }
    };

    const handleModelChange = (e) => {
        setSelectedModel(e.target.value);
    };

    const handleSeatsChange = (e) => {
        setSeatsAvailable(e.target.value);
    };

    useEffect(() => {
        if (selectedYear && selectedMake && selectedModel) {
            onCarDetailsChange({
                carYear: selectedYear,
                carMake: selectedMake,
                carModel: selectedModel,
                seatsAvailable: seatsAvailable,
            });
        }
    }, [selectedYear, selectedMake, selectedModel, seatsAvailable, onCarDetailsChange]);

    return (
        <Container className="mt-4">
            <h2 className="text-center mb-4">Add Your Car</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form.Group controlId="year">
                <Form.Label>Year</Form.Label>
                <Form.Control as="select" value={selectedYear} onChange={handleYearChange}>
                    <option value="">Select Year</option>
                    {years.map(year => (
                        <option key={year.value} value={year.value}>{year.text}</option>
                    ))}
                </Form.Control>
            </Form.Group>
            <Form.Group controlId="make" className="mt-3">
                <Form.Label>Make</Form.Label>
                <Form.Control as="select" value={selectedMake} onChange={handleMakeChange} disabled={!selectedYear}>
                    <option value="">Select Make</option>
                    {makes.map(make => (
                        <option key={make.value} value={make.value}>{make.text}</option>
                    ))}
                </Form.Control>
            </Form.Group>
            <Form.Group controlId="model" className="mt-3">
                <Form.Label>Model</Form.Label>
                <Form.Control as="select" value={selectedModel} onChange={handleModelChange} disabled={!selectedMake}>
                    <option value="">Select Model</option>
                    {models.map(model => (
                        <option key={model.value} value={model.value}>{model.text}</option>
                    ))}
                </Form.Control>
            </Form.Group>
            <Form.Group controlId="seatsAvailable" className="mt-3">
                <Form.Label>Seats Available</Form.Label>
                <Form.Control
                    type="number"
                    placeholder="Enter number of seats"
                    value={seatsAvailable}
                    onChange={handleSeatsChange}
                    required
                    min="1"
                    max="10"
                />
            </Form.Group>
        </Container>
    );
};

export default AddCarForm;
