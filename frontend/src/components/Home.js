import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/Home.css'; // Assuming corrected path to Home.css
import Header from './Header';
import MainContent from './MainContent';
import AboutUs from './AboutUs';
import Footer from './Footer';
import DeviceService from '../services/DeviceService';
import SoftwareService from '../services/SoftwareService';

const Home = () => {
  const [deviceCount, setDeviceCount] = useState(0);
  const [softwareCount, setSoftwareCount] = useState(0);

  useEffect(() => {
    fetchDeviceCounts();
    fetchSoftwareCounts();

    // Add event listener for scrolling
    window.addEventListener('scroll', handleScroll);

    // Clean up the event listener
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  const fetchDeviceCounts = () => {
    const token = localStorage.getItem('token');
    
    DeviceService.viewDevices(token)
      .then(devices => {
        setDeviceCount(devices.length);
      })
      .catch(error => {
        console.error('Error fetching devices:', error);
      });
  };

  const fetchSoftwareCounts = () => {
    SoftwareService.getAllSoftware()
      .then(softwares => {
        setSoftwareCount(softwares.length);
      })
      .catch(error => {
        console.error('Error fetching software:', error);
      });
  };

  const devicePercentage = deviceCount === 0 ? 0 : (deviceCount / (deviceCount + softwareCount)) * 100;
  const softwarePercentage = softwareCount === 0 ? 0 : (softwareCount / (deviceCount + softwareCount)) * 100;

  // Function to handle scroll event
  const handleScroll = () => {
    const elements = document.querySelectorAll('.animate-scroll');

    elements.forEach(element => {
      const elementPosition = element.getBoundingClientRect().top;
      const screenPosition = window.innerHeight / 1.3;

      if (elementPosition < screenPosition) {
        element.classList.add('fade-in-active'); // Add active class to trigger fade-in
      } else {
        element.classList.remove('fade-in-active'); // Remove active class to hide
      }
    });
  };

  return (
    <div className="containerHome">
      <Header />
      <MainContent />
      <AboutUs />
      <Footer />
    </div>
  );
};

export default Home;
