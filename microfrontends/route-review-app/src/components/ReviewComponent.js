import React, { useState, useEffect } from 'react';
import { Form, Button, Container, Alert } from 'react-bootstrap';
import { getAllUsers, addReview } from '../api';

const ReviewComponent = ({ toggleForm }) => {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState('');
    const [rating, setRating] = useState(0);
    const [review, setReview] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                // const userList = await getAllUsers('6659c10ff8b9fe6335ed52ed');
                const userList = [{ id: 'edon-id', username: 'Edoni' }];
                setUsers(userList);
            } catch (err) {
                console.error('Error fetching users:', err);
                setError('Failed to fetch users. Please try again later.');
            }
        };

        fetchUsers();
    }, []);

    const handleReviewSubmit = async (e) => {
        e.preventDefault();

        if (!selectedUser || rating === 0 || review.trim() === '') {
            setError('All fields are required.');
            return;
        }

        const newReview = {
            review,
            rating,
            reviewedUserId: "6659a7c3676cee46ffb033cc",
            reviewerId: '6659a740676cee46ffb033cb',
        };

        try {
            console.log('Submitting review:', newReview);
            await addReview(newReview);
            alert('Review submitted successfully');
        } catch (err) {
            console.error('Error submitting review:', err);
            setError('Failed to submit review. Please try again later.');
        }
    };

    return (
        <Container className="review-form-container border p-4 rounded bg-light shadow animated-container">
            <Button className="mb-3" variant="secondary" onClick={toggleForm}>
                Hide Review Form
            </Button>
            <h2 className="text-center mb-4">Submit a Review</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleReviewSubmit}>
                <Form.Group controlId="selectUser">
                    <Form.Label>Select User</Form.Label>
                    <Form.Control
                        as="select"
                        value={selectedUser}
                        onChange={(e) => setSelectedUser(e.target.value)}
                    >
                        <option value="">Select a user...</option>
                        {users.map((user) => (
                            <option key={user.id} value={user.id}>
                                {user.username}
                            </option>
                        ))}
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId="rating" className="mt-3">
                    <Form.Label>Rating</Form.Label>
                    <Form.Control
                        as="select"
                        value={rating}
                        onChange={(e) => setRating(parseInt(e.target.value, 10))}
                    >
                        <option value={0}>Select a rating...</option>
                        {[1, 2, 3, 4, 5].map((star) => (
                            <option key={star} value={star}>
                                {star} Star{star > 1 && 's'}
                            </option>
                        ))}
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId="reviewText" className="mt-3">
                    <Form.Label>Review</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        value={review}
                        onChange={(e) => setReview(e.target.value)}
                        placeholder="Write your review here..."
                    />
                </Form.Group>
                <Button className="mt-4 w-100 animated-button" variant="primary" type="submit" size="lg">
                    Submit Review
                </Button>
            </Form>
        </Container>
    );
};

export default ReviewComponent;
