import React from 'react';
import NavbarComponent from './components/NavbarComponent';
import UserDetails from './components/UserDetails';
import './App.css';

const App = () => {
  return (
    <div className="App">
      <NavbarComponent />
      <div className="main-container">
        <UserDetails />
      </div>
    </div>
  );
};

export default App;
