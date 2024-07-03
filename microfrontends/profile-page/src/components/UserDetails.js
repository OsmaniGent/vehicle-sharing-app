import React, { useEffect, useState } from 'react';
import { changeRole, getUserDetails } from '../api';
import AddCarForm from './AddCarForm';
import { getUserIdFromToken } from '../utils/auth';
import './UserDetails.css';

const UserDetails = () => {
  const [user, setUser] = useState(null);
  const [showAddCarForm, setShowAddCarForm] = useState(false);
  const userId = getUserIdFromToken();

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        console.log('Fetching user details for userId:', userId);
        if (userId) {
          const userDetails = await getUserDetails(userId);
          console.log('User details fetched:', userDetails);
          setUser(userDetails);
        }
      } catch (error) {
        console.error("Error fetching user details: ", error);
      }
    };

    fetchUserDetails();
  }, [userId]);

  const handleBecomeDriver = () => {
    setShowAddCarForm(true);
  };

  const handleCarAdded = async () => {
    try {
      if (userId) {
        const updatedUserDetails = await getUserDetails(userId);
        setUser(updatedUserDetails);
        setShowAddCarForm(false);
      }
    } catch (error) {
      console.error("Error fetching updated user details: ", error);
    }
  };

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="user-details">
      <h2>User Profile</h2>
      <div className="user-info">
        <p><strong>Email:</strong> {user.email}</p>
        <p><strong>Username:</strong> {user.username}</p>
        <p><strong>Role:</strong> {user.role}</p>
        <p><strong>Ethereum Address:</strong> {user.ethereumAddress}</p>
        <p><strong>Average Rating:</strong> {user.avgRating}</p>
        {/* Add more user details as needed */}
      </div>
      {user.role === 'PASSENGER' && (
        <div>
          <button onClick={handleBecomeDriver} className="become-driver-btn">Become a Driver</button>
          {showAddCarForm && <AddCarForm userId={user.id} onCarAdded={handleCarAdded} />}
        </div>
      )}
    </div>
  );
};

export default UserDetails;
