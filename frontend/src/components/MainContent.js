import React from 'react';
import { useEffect } from 'react';
import SoftwareService from '../services/SoftwareService';
import llt2example from '../assets/llt2example.png'; 
import DeviceService from '../services/DeviceService';// Importing the llt2example image
import { useState } from 'react';

const MainContent = () => {
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
    <main className="main-content-section mt-5">
      <div className="container">
        <div className="row">
          <div className="col-md-6">
            <div className="card border-light animate-scroll">
              <div className="card-body">
                <h5 className="card-title">Device Count</h5>
                <div className="progress">
                  <div
                    className="progress-bar bg-success"
                    role="progressbar"
                    style={{ width: `${devicePercentage}%` }}
                    aria-valuenow={deviceCount}
                    aria-valuemin="0"
                    aria-valuemax={deviceCount + softwareCount}
                  >
                    {deviceCount}
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="col-md-6">
            <div className="card border-light animate-scroll">
              <div className="card-body">
                <h5 className="card-title">Software Count</h5>
                <div className="progress">
                  <div
                    className="progress-bar bg-primary"
                    role="progressbar"
                    style={{ width: `${softwarePercentage}%` }}
                    aria-valuenow={softwareCount}
                    aria-valuemin="0"
                    aria-valuemax={deviceCount + softwareCount}
                  >
                    {softwareCount}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
    </main>
  );
};

export default MainContent;
