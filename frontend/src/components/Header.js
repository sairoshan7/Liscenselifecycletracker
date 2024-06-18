import React from 'react';
import headerImage from '../assets/headerimage.jpg'; // Importing the office image
import '../styles/Header.css'; // Import your custom CSS for additional styling if needed

const Header = () => {
  return (
    <header className="header-section">
      <div className="container">
        <div className="row align-items-center">
          <div className="col-md-6">
            <div className="content">
              <h1 className="header-title">Welcome to License Lifecycle Tracker</h1>
              <p className="header-text">
                This capstone project aims to develop a comprehensive application,
                "License Lifecycle Tracker," designed to manage and monitor the
                lifecycle of licenses for network devices and software. This system
                will provide an integrated platform for tracking critical information
                such as device expiration dates, end of support dates, and software
                license details. It will encompass various lifecycle stages including
                order (procurement), deployment, auditing (fault management), and
                expiry. 
              </p>
            </div>
          </div>
          <div className="col-md-6">
            <img src={headerImage} alt="Office" className="img-fluid" />
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
