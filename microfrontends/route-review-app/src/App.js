import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import MapComponent from './components/MapComponent';
import ReviewComponent from './components/ReviewComponent';
import NavbarComponent from './components/NavbarComponent';
import withAuth from './components/withAuth';
import './App.css';

const App = () => {
    const [showReviewForm, setShowReviewForm] = useState(false);

    const toggleReviewForm = () => {
        setShowReviewForm(!showReviewForm);
    };

    return (
        <div className="App">
            <NavbarComponent />
            <div className="main-content">
                <MapComponent />
                <Button className="review-button" onClick={toggleReviewForm}>
                    {showReviewForm ? 'Hide Review Form' : 'Show Review Form'}
                </Button>
                {showReviewForm && <ReviewComponent toggleForm={toggleReviewForm} />}
            </div>
        </div>
    );
};

export default App;