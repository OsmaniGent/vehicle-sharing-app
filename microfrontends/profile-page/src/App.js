import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavbarComponent from './components/NavbarComponent';
import UserDetails from './components/UserDetails';
import withAuth from './components/withAuth';
import './App.css';

const App = () => {
  const AuthenticatedUserDetails = withAuth(UserDetails);

  console.log('Rendering App component');

  return (
    <Router>
      <div className="App">
        <NavbarComponent />
        <div className="main-container">
          <Routes>
            <Route path="/" element={<AuthenticatedUserDetails />} />
            {/* Add other routes here */}
          </Routes>
        </div>
      </div>
    </Router>
  );
};

export default App;
