import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import DeviceService from '../services/DeviceService';

function UpdateDevice() {
  const navigate = useNavigate();
  const { deviceId } = useParams();

  const [deviceData, setDeviceData] = useState({
    deviceName: '',
    deviceType: '',
    purchaseDate: '',
    expirationDate: '',
    supportEndDate: '',
    status: ''
  });

  useEffect(() => {
    fetchDeviceDataById(deviceId);
  }, [deviceId]);

  const fetchDeviceDataById = async (deviceId) => {
    try {
        
      const response = await DeviceService.getDeviceById(deviceId);
      if (response) {
        setDeviceData(response);
      } else {
        console.error('Error: Device data is undefined.');
      }
    } catch (error) {
      console.error('Error fetching device data:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setDeviceData((prevDeviceData) => ({
      ...prevDeviceData,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await DeviceService.updateDevice( deviceData);
      navigate("/admin");
    } catch (error) {
      console.error('Error updating device:', error);
      alert(error.message || 'An error occurred while updating device.');
    }
  };

  return (
    <div className="auth-container">
      <h2>Update Device</h2>
      <form onSubmit={handleSubmit}>
        {/* Input fields for device data */}
        <div className="form-group">
          <label>Device Name:</label>
          <input type="text" name="deviceName" value={deviceData.deviceName || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Device Type:</label>
          <input type="text" name="deviceType" value={deviceData.deviceType || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Purchase Date:</label>
          <input type="date" name="purchaseDate" value={deviceData.purchaseDate || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Expiration Date:</label>
          <input type="date" name="expirationDate" value={deviceData.expirationDate || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>End of Support Date:</label>
          <input type="date" name="supportEndDate" value={deviceData.supportEndDate || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Status:</label>
          <input type="text" name="status" value={deviceData.status || ''} onChange={handleInputChange} />
        </div>
        <button type="submit">Update</button>
      </form>
    </div>
  );
}

export default UpdateDevice;
