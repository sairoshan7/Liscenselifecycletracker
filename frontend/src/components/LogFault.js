import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import TechnicalSupportService from '../services/TechnicalSupportService'; // Import the TechnicalSupportService

const LogFault = () => {
  const [deviceId, setDeviceId] = useState('');
  const [description, setDescription] = useState('');
  const [date, setDate] = useState('');
  const [category, setCategory] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Call the logFault function from the TechnicalSupportService with the form data
      await TechnicalSupportService.logFault(deviceId, description, date, category);
      // Reset form fields after successful submission
      setDeviceId('');
      setDescription('');
      setDate('');
      setCategory('');
      setErrorMessage('');
      alert('Fault logged successfully!');
    } catch (error) {
      setErrorMessage('Error logging fault: ' + error.message);
    }
  };

  return (
    <div className="container">
      <h2>Log Fault</h2>
      {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="deviceId" className="form-label">Device ID:</label>
          <input type="text" className="form-control" id="deviceId" value={deviceId} onChange={(e) => setDeviceId(e.target.value)} required />
        </div>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">Description:</label>
          <textarea className="form-control" id="description" rows="3" value={description} onChange={(e) => setDescription(e.target.value)} required></textarea>
        </div>
        <div className="mb-3">
          <label htmlFor="date" className="form-label">Date:</label>
          <input type="date" className="form-control" id="date" value={date} onChange={(e) => setDate(e.target.value)} required />
        </div>
        <div className="mb-3">
          <label htmlFor="category" className="form-label">Category:</label>
          <input type="text" className="form-control" id="category" value={category} onChange={(e) => setCategory(e.target.value)} required />
        </div>
        <button type="submit" className="btn btn-primary">Log Fault</button>
      </form>
    </div>
  );
};

export default LogFault;
