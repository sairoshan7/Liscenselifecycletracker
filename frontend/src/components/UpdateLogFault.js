import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import TechnicalSupportService from '../services/TechnicalSupportService'; // Import the TechnicalSupportService

const UpdateLogFault = () => {
  const [deviceId, setDeviceId] = useState('');
  const [repairDetails, setRepairDetails] = useState('');
  const [category, setCategory] = useState('');
  const [eventType, setEventType] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Call the updateFaultLog function from the TechnicalSupportService with the form data
      await TechnicalSupportService.updateFaultLog(deviceId, repairDetails, category, eventType);
      // Reset form fields after successful submission
      setDeviceId('');
      setRepairDetails('');
      setCategory('');
      setEventType('');
      setErrorMessage('');
      alert('Fault log updated successfully!');
    } catch (error) {
      setErrorMessage('Error updating fault log: ' + error.message);
    }
  };

  return (
    <div className="container">
      <h2>Update Fault Log</h2>
      {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="deviceId" className="form-label">Device ID:</label>
          <input type="text" className="form-control" id="deviceId" value={deviceId} onChange={(e) => setDeviceId(e.target.value)} required />
        </div>
        <div className="mb-3">
          <label htmlFor="repairDetails" className="form-label">Repair Details:</label>
          <textarea className="form-control" id="repairDetails" rows="3" value={repairDetails} onChange={(e) => setRepairDetails(e.target.value)} required></textarea>
        </div>
        <div className="mb-3">
          <label htmlFor="category" className="form-label">Category:</label>
          <input type="text" className="form-control" id="category" value={category} onChange={(e) => setCategory(e.target.value)} required />
        </div>
        <div className="mb-3">
          <label htmlFor="eventType" className="form-label">Event Type:</label>
          <input type="text" className="form-control" id="eventType" value={eventType} onChange={(e) => setEventType(e.target.value)} required />
        </div>
        <button type="submit" className="btn btn-primary">Update Fault Log</button>
      </form>
    </div>
  );
};

export default UpdateLogFault;
