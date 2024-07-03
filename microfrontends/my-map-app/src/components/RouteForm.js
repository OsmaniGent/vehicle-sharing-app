import React from 'react';
import { Form, Button, InputGroup, FormControl } from 'react-bootstrap';

const RouteForm = ({ formData, onFieldChange, onSubmit, onCalculateFuelCost, loading, seatsAvailable }) => {
    const togglePassengerDropdown = () => {
        onFieldChange('passengersAllowed', !formData.passengersAllowed);
    };

    const renderPassengerOptions = () => {
        const numberOfOptions = seatsAvailable > 0 ? seatsAvailable : 1;
        return Array.from({ length: numberOfOptions }, (_, i) => i + 1).map(number => (
            <option key={number} value={number}>{number}</option>
        ));
    };

    return (
        <Form>
            <InputGroup className="mb-3">
                <InputGroup.Text>Start Latitude</InputGroup.Text>
                <FormControl
                    value={formData.startLat}
                    onChange={e => onFieldChange('startLat', e.target.value)}
                    placeholder="Latitude"
                />
            </InputGroup>
            <InputGroup className="mb-3">
                <InputGroup.Text>Start Longitude</InputGroup.Text>
                <FormControl
                    value={formData.startLng}
                    onChange={e => onFieldChange('startLng', e.target.value)}
                    placeholder="Longitude"
                />
            </InputGroup>
            <InputGroup className="mb-3">
                <InputGroup.Text>End Latitude</InputGroup.Text>
                <FormControl
                    value={formData.endLat}
                    onChange={e => onFieldChange('endLat', e.target.value)}
                    placeholder="Latitude"
                />
            </InputGroup>
            <InputGroup className="mb-3">
                <InputGroup.Text>End Longitude</InputGroup.Text>
                <FormControl
                    value={formData.endLng}
                    onChange={e => onFieldChange('endLng', e.target.value)}
                    placeholder="Longitude"
                />
            </InputGroup>
            <Form.Group controlId="passengersAllowed">
                <Form.Check
                    type="checkbox"
                    label="Passengers Allowed"
                    checked={formData.passengersAllowed}
                    onChange={togglePassengerDropdown}
                />
            </Form.Group>
            {formData.passengersAllowed && (
                <InputGroup className="mb-3">
                    <InputGroup.Text>Number of Passengers:</InputGroup.Text>
                    <FormControl
                        as="select"
                        value={formData.passengerNumber}
                        onChange={e => onFieldChange('passengerNumber', e.target.value)}
                    >
                        {renderPassengerOptions()}
                    </FormControl>
                </InputGroup>
            )}
            <InputGroup className="mb-3">
                <InputGroup.Text>Cost</InputGroup.Text>
                <FormControl
                    value={formData.cost}
                    onChange={e => onFieldChange('cost', e.target.value)}
                    placeholder="Cost"
                />
            </InputGroup>
            <InputGroup className="mb-3">
                <InputGroup.Text>Time until action (minutes)</InputGroup.Text>
                <FormControl
                    type="number"
                    value={formData.timer}
                    onChange={e => onFieldChange('timer', e.target.value)}
                    placeholder="Minutes"
                />
            </InputGroup>
            <InputGroup className="mb-3">
                <InputGroup.Text>Date of trip</InputGroup.Text>
                <FormControl
                    type="datetime-local"
                    value={formData.startDate}
                    onChange={e => onFieldChange('startDate', e.target.value)}
                />
            </InputGroup>
            <Button onClick={onSubmit} disabled={loading}>Get Route</Button>
            <Button onClick={onCalculateFuelCost} disabled={loading}>Get Fuel Cost</Button>
        </Form>
    );
};

export default RouteForm;
