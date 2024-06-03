import React, { useEffect, useState } from 'react';
import { getUserDetails } from '../api';
import AddCarForm from './AddCarForm';
import './UserDetails.css';

const UserDetails = () => {
  const [user, setUser] = useState(null);
  const [showAddCarForm, setShowAddCarForm] = useState(false);
  const userId = '665a2d9594694c21bb7093de';

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const userDetails = await getUserDetails(userId);
        setUser(userDetails);
      } catch (error) {
        console.error("Error fetching user details: ", error);
      }
    };

    fetchUserDetails();
  }, []);

  const handleBecomeDriver = () => {
    setShowAddCarForm(true);
  };

  const handleCarAdded = async () => {
    try {
      const updatedUserDetails = await getUserDetails(userId);
      setUser(updatedUserDetails);
      setShowAddCarForm(false);
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
        <p><strong>Email:</strong> gent@gmail.com</p>
        <p><strong>Username:</strong> Gent</p>
        <p><strong>Role:</strong> passenger</p>  
        <p><strong>Ethereum Address:</strong> {user.ethereumAddress}</p>
        <p><strong>Average Rating:</strong> {user.avgRating}</p>
        {/* Add more user details as needed */}
      </div>
      {user.role === 'passenger' && (
        <div>
          <button onClick={handleBecomeDriver} className="become-driver-btn">Become a Driver</button>
          {showAddCarForm && <AddCarForm userId={userId} onCarAdded={handleCarAdded} />}
        </div>
      )}
    </div>
  );
};

export default UserDetails;
