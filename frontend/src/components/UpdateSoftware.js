import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import SoftwareService from '../services/SoftwareService';
 
function UpdateSoftware() {
  const navigate = useNavigate();
  const { softwareId } = useParams();
 
  const [softwareData, setSoftwareData] = useState({
    softwareName: '',
    licenseKey: '',
    purchaseDate: '',
    expiryDate: '',
    supportEndDate: '',
    status: ''
  });
 
  useEffect(() => {
    fetchSoftwareDataById(softwareId);
  }, [softwareId]);
 
  const fetchSoftwareDataById = async (softwareId) => {
    try {
      //const token = localStorage.getItem('token'); // Retrieve the token from localStorage
      const response = await SoftwareService.getSoftwareById(softwareId);
      console.log(response);
      if (response) {
        setSoftwareData(response);
      } else {
        console.error('Error: Software data is undefined.');
      }
    } catch (error) {
      console.error('Error fetching software data:', error);
    }
  };
 
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSoftwareData((prevSoftwareData) => ({
      ...prevSoftwareData,
      [name]: value
    }));
  };
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await SoftwareService.updateSoftware(softwareData, localStorage.getItem('token'));
      console.log(res);
      navigate("/admin/software-management");
    } catch (error) {
      console.error('Error updating software:', error);
      alert(error.message || 'An error occurred while updating software.');
    }
  };
 
  return (
    <div className="auth-container">
      <h2>Update Software</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Software Name:</label>
          <input type="text" name="softwareName" value={softwareData.softwareName || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>License Key:</label>
          <input type="text" name="licenseKey" value={softwareData.licenseKey || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Purchase Date:</label>
          <input type="date" name="purchaseDate" value={softwareData.purchaseDate || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Expiration Date:</label>
          <input type="date" name="expiryDate" value={softwareData.expiryDate || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Support End Date:</label>
          <input type="date" name="supportEndDate" value={softwareData.supportEndDate || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Status:</label>
          <input type="text" name="status" value={softwareData.status || ''} onChange={handleInputChange} />
        </div>
        <button type="submit">Update</button>
      </form>
    </div>
  );
}
 
export default UpdateSoftware;