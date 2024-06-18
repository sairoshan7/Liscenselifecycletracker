import React, { useState, useEffect, useRef } from 'react';
import DeviceService from '../services/DeviceService';
import { Link, useNavigate } from 'react-router-dom';
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

  const handleCloseDetails = () => {
    setSelectedDevice(null); // Close the selected device details
  };

  const generateReport = () => {
    const header = ['ID', 'Name', 'Type', 'Purchase Date', 'Expiration Date', 'End of Support Date', 'Status'];
    const rows = devices.map(device => [device.deviceId, device.deviceName, device.deviceType, device.purchaseDate, device.expirationDate, device.supportEndDate, device.status]);
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
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card">
            <div className="card-body">
              <h2 className="card-title text-center">Device Management Page</h2>
              <form onSubmit={handleSubmit} ref={form}>
                <div className="mb-3">
                  <label htmlFor="deviceName" className="form-label">Device Name:</label>
                  <input type="text" className="form-control" id="deviceName" name="deviceName" value={newDevice.deviceName} onChange={handleInputChange} required />
                </div>
                <div className="mb-3">
                  <label htmlFor="deviceType" className="form-label">Device Type:</label>
                  <input type="text" className="form-control" id="deviceType" name="deviceType" value={newDevice.deviceType} onChange={handleInputChange} required />
                </div>
                <div className="mb-3">
                  <label htmlFor="purchaseDate" className="form-label">Purchase Date:</label>
                  <input type="date" className="form-control" id="purchaseDate" name="purchaseDate" value={newDevice.purchaseDate} onChange={handleInputChange} required />
                </div>
                <div className="mb-3">
                  <label htmlFor="expirationDate" className="form-label">Expiration Date:</label>
                  <input type="date" className="form-control" id="expirationDate" name="expirationDate" value={newDevice.expirationDate} onChange={handleInputChange} required />
                </div>
                <div className="mb-3">
                  <label htmlFor="supportEndDate" className="form-label">End of Support Date:</label>
                  <input type="date" className="form-control" id="supportEndDate" name="supportEndDate" value={newDevice.supportEndDate} onChange={handleInputChange} required />
                </div>
                <div className="mb-3">
                  <label htmlFor="status" className="form-label">Status:</label>
                  <select className="form-select" id="status" name="status" value={newDevice.status} onChange={handleInputChange} required>
                    <option value="">Select Status</option>
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                  </select>
                </div>
                <button type="submit" className="btn btn-primary w-10" disabled={loading}>Add Device</button>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div className="row mt-4">
        <div className="col-md-12">
          <div className="card">
            <div className="card-body">
              <div className="table-responsive">
                <table className="table table-striped table-bordered">
                  <thead className="table-dark">
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
                          <div className="btn-group" role="group" aria-label="Device Actions">
                            <button className="btn btn-primary btn-sm" onClick={() => fetchDeviceById(device.deviceId)}>View</button>
                            <button className="btn btn-danger btn-sm" onClick={() => deleteDevice(device.deviceId)}>Delete</button>
                            <button className="btn btn-secondary btn-sm">
                              <Link to={`/update-device/${device.deviceId}`} className="text-white text-decoration-none">Update</Link>
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      {selectedDevice && (
        <div className="row mt-4">
          <div className="col-md-8 offset-md-2">
            <div className="card">
              <div className="card-body">
                <button className="btn btn-danger btn-sm float-right" onClick={handleCloseDetails}>X</button>
                <h3 className="card-title mt-2">Selected Device Details:</h3>
                <p className="card-text">Device Name: {selectedDevice.deviceName}</p>
                <p className="card-text">Device Type: {selectedDevice.deviceType}</p>
                <p className="card-text">Purchase Date: {selectedDevice.purchaseDate}</p>
                <p className="card-text">Expiration Date: {selectedDevice.expirationDate}</p>
                <p className="card-text">End of Support Date: {selectedDevice.supportEndDate}</p>
                <p className="card-text">Status: {selectedDevice.status}</p>
                <div className="row mt-4">
                  <div className="col-md-12 text-left">
                    <button className="btn btn-primary btn-sm" onClick={generateReport}>Generate Report</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default DeviceManagementPage;
