import React, { useState, useEffect } from 'react';
import RegularUserService from '../services/RegularUserService';
import '../styles/UserViewDevices.css'; // Import custom CSS for styling
import searchIcon from '../assets/search.png'; // Import your search icon from Icons8

const UserViewDevices = () => {
  const [devices, setDevices] = useState([]);
  const [searchField, setSearchField] = useState('id');
  const [searchKeyword, setSearchKeyword] = useState('');
  const [errorState, setErrorState] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchDevices();
  }, []);

  const fetchDevices = () => {
    RegularUserService.viewDevices()
      .then(response => {
        setDevices(response);
        setErrorState(false); // Reset error state on successful fetch
      })
      .catch(error => {
        console.error('Error fetching devices:', error);
        setErrorState(true);
        setErrorMessage(error.response.data); // Use error response message from backend
      });
  };

  const handleSearch = async () => {
    try {
      let response;
      if (!searchKeyword.trim()) {
        // If search keyword is empty, fetch all devices again
        response = await RegularUserService.viewDevices();
      } else {
        // Otherwise, perform search based on the selected field
        switch (searchField) {
          case 'id':
            response = await RegularUserService.searchDevicesById(searchKeyword);
            break;
          case 'name':
            response = await RegularUserService.searchDevicesByName(searchKeyword);
            break;
          case 'type':
            response = await RegularUserService.searchDevicesByType(searchKeyword);
            break;
          case 'purchaseDate':
            response = await RegularUserService.searchDevicesByPurchaseDate(searchKeyword);
            break;
          case 'expirationDate':
            response = await RegularUserService.searchDevicesByExpirationDate(searchKeyword);
            break;
          case 'supportEndDate':
            response = await RegularUserService.searchDevicesBySupportEndDate(searchKeyword);
            break;
          case 'status':
            response = await RegularUserService.searchDevicesByStatus(searchKeyword);
            break;
          default:
            response = [];
            break;
        }
      }
      setDevices(Array.isArray(response) ? response : [response]); // Ensure response is an array
      setErrorState(false); // Reset error state on successful search
    } catch (error) {
      console.error('Error searching devices:', error);
      // Extract error message from the response if available
      const errorMessage = error.response ? error.response.data : 'An error occurred while searching devices. Please try again later.';
      // Handle error in frontend
      setErrorState(true);
      setErrorMessage(errorMessage);
    }
  };

  const handleInputChange = (e) => {
    setSearchKeyword(e.target.value);
  };

  const handleSelectChange = (e) => {
    setSearchField(e.target.value); // Update selected search field
    setSearchKeyword(''); // Clear search keyword when changing the field
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4 custom-heading">View Devices</h2>
      <div className="custom-search-container">
        <div className="custom-search-input-container">
          <input
            type="text"
            className="form-control custom-input"
            placeholder="Search..."
            aria-label="Search"
            aria-describedby="search-button"
            value={searchKeyword}
            onChange={handleInputChange}
            onKeyPress={handleKeyPress} // Handle Enter key press
          />
          <button
            className="btn btn-primary custom-button"
            type="button"
            id="search-button"
            onClick={handleSearch}
          >
            <img src={searchIcon} alt="Search Icon" className="custom-search-icon" />
          </button>
        </div>
        <select
          className="form-select custom-select"
          aria-label="Search field"
          value={searchField}
          onChange={handleSelectChange}
        >
          <option value="id">ID</option>
          <option value="name">Name</option>
          <option value="type">Type</option>
          <option value="purchaseDate">Purchase Date</option>
          <option value="expirationDate">Expiration Date</option>
          <option value="supportEndDate">End of Support Date</option>
          <option value="status">Status</option>
        </select>
      </div>
      {errorState && (
        <div className="alert alert-danger custom-alert" role="alert">
          {errorMessage}
        </div>
      )}
      <div className="table-responsive">
        <table className="table table-bordered table-hover mt-4 custom-table">
          <thead className="table-dark">
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Type</th>
              <th>Purchase Date</th>
              <th>Expiration Date</th>
              <th>End of Support Date</th>
              <th>Status</th>
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
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default UserViewDevices;
