import React, { useState, useEffect, useRef } from 'react';
import DeviceService from '../services/DeviceService';
import { Link, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/DeviceManagementPage.css'; // Import CSS file

const DeviceManagementPage = () => {
  const [newDevice, setNewDevice] = useState({
    deviceName: '',
    deviceType: '',
    purchaseDate: '',
    expirationDate: '',
    supportEndDate: '',
    status: ''
  });
  const [devices, setDevices] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const [selectedDevice, setSelectedDevice] = useState(null);

  const form = useRef();
  const navigate = useNavigate();

  useEffect(() => {
    fetchDevices();
  }, []);

  const fetchDevices = () => {
    const token = localStorage.getItem('token');
    DeviceService.viewDevices(token)
      .then(response => {
        setDevices(response);
      })
      .catch(error => {
        console.error('Error fetching devices:', error);
      });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewDevice({ ...newDevice, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');

    const token = localStorage.getItem('token');
    DeviceService.addDevice(newDevice, token)
      .then(() => {
        alert('Device added successfully');
        setNewDevice({
          deviceId: 0,
          deviceName: '',
          deviceType: '',
          purchaseDate: '',
          expirationDate: '',
          supportEndDate: '',
          status: ''
        });
        fetchDevices();
      })
      .catch(error => {
        console.error('Error adding device:', error);
        alert('An error occurred while adding device');
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const fetchDeviceById = async (deviceId) => {
    try {
      const device = await DeviceService.getDeviceById(deviceId);
      setSelectedDevice(device); // Set the selected device
    } catch (error) {
      console.error('Error fetching device by ID:', error);
    }
  };

  const generateReport = () => {
    const header = ['ID', 'Name', 'Type', 'Purchase Date', 'Expiration Date', 'End of Support Date', 'Status'];
    const rows = devices.map(device => [device.deviceId, device.deviceName, device.deviceType, device.purchaseDate, device.expirationDate, device.endOfSupportDate, device.status]);
    const reportData = [header, ...rows];
    const csvContent = reportData.map(row => row.join(',')).join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'device_report.csv');
    document.body.appendChild(link);
    link.click();
  };

  const deleteDevice = (deviceId) => {
    const confirmDelete = window.confirm('Are you sure you want to delete this device?');
    if (confirmDelete) {
      DeviceService.deleteDevice(deviceId)
        .then(() => {
          fetchDevices();
        })
        .catch(error => {
          console.error('Error deleting device:', error);
        });
    }
  };

  return (
    <div className="device-management-container">
      <div className="device-form-container">
        <h2>Device Management Page</h2>
        <div className="card card-container">
          <form onSubmit={handleSubmit} ref={form}>
            <div className="form-group">
              <label htmlFor="deviceName" className="form-label">Device Name:</label>
              <input type="text" className="form-control" id="deviceName" name="deviceName" value={newDevice.deviceName} onChange={handleInputChange} required />
            </div>
            <div className="form-group">
              <label htmlFor="deviceType" className="form-label">Device Type:</label>
              <input type="text" className="form-control" id="deviceType" name="deviceType" value={newDevice.deviceType} onChange={handleInputChange} required />
            </div>
            <div className="form-group">
              <label htmlFor="purchaseDate" className="form-label">Purchase Date:</label>
              <input type="date" className="form-control" id="purchaseDate" name="purchaseDate" value={newDevice.purchaseDate} onChange={handleInputChange} required />
            </div>
            <div className="form-group">
              <label htmlFor="expirationDate" className="form-label">Expiration Date:</label>
              <input type="date" className="form-control" id="expirationDate" name="expirationDate" value={newDevice.expirationDate} onChange={handleInputChange} required />
            </div>
            <div className="form-group">
              <label htmlFor="supportEndDate" className="form-label">End of Support Date:</label>
              <input type="date" className="form-control" id="supportEndDate" name="supportEndDate" value={newDevice.supportEndDate} onChange={handleInputChange} required />
            </div>
            <div className="form-group">
              <label htmlFor="status" className="form-label">Status:</label>
              <select className="form-select" id="status" name="status" value={newDevice.status} onChange={handleInputChange} required>
                <option value="">Select Status</option>
                <option value="Active">Active</option>
                <option value="Inactive">Inactive</option>
              </select>
            </div>
            <button type="submit" className="btn btn-primary" disabled={loading}>Add Device</button>
          </form>
        </div>
      </div>
      <div className="device-table-container">
        <h2>Device List</h2>
        <table className="table mt-4">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Type</th>
              <th>Purchase Date</th>
              <th>Expiration Date</th>
              <th>End of Support Date</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {devices.map(device => (
              <tr key={device.deviceId}>
                <td>{device.deviceId}</td>
                <td>{device.deviceName}</td>
                <td>{device.deviceType}</td>
                <td>{device.purchaseDate}</td>
                <td>{device.expirationDate}</td>
                <td>{device.supportEndDate}</td>
                <td>{device.status}</td>
                <td>
                  <div className="action-buttons">
                    <button className="btn btn-primary btn-sm" onClick={() => fetchDeviceById(device.deviceId)}>View</button>
                    <button className="btn btn-danger btn-sm" onClick={() => deleteDevice(device.deviceId)}>Delete</button>
                    <button className="btn btn-secondary btn-sm update-button"><Link to={`/update-device/${device.deviceId}`}> Update</Link></button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {selectedDevice && (
        <div className="selected-device-details">
          <h3>Selected Device Details:</h3>
          <p>Device Name: {selectedDevice.deviceName}</p>
          <p>Device Type: {selectedDevice.deviceType}</p>
          <p>Purchase Date: {selectedDevice.purchaseDate}</p>
          <p>Expiration Date: {selectedDevice.expirationDate}</p>
          <p>End of Support Date: {selectedDevice.endOfSupportDate}</p>
          <p>Status: {selectedDevice.status}</p>
        </div>
      )}
      <button className="btn btn-success" onClick={generateReport}>Generate Report</button>
    </div>
  );
};

export default DeviceManagementPage;
