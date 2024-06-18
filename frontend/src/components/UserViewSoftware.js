import React, { useState, useEffect } from 'react';
import RegularUserService from '../services/RegularUserService';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS
import '../styles/UserViewSoftware.css'; // Import custom CSS for styling
import searchIcon from '../assets/search.png'; // Import your search icon from Icons8

const UserViewSoftware = () => {
  const [software, setSoftware] = useState([]);
  const [searchField, setSearchField] = useState('id');
  const [searchKeyword, setSearchKeyword] = useState('');
  const [errorState, setErrorState] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchSoftware();
  }, []);

  const fetchSoftware = () => {
    RegularUserService.viewSoftwares()
      .then(response => {
        setSoftware(response);
        setErrorState(false); // Reset error state on successful fetch
      })
      .catch(error => {
        console.error('Error fetching software:', error);
        setErrorState(true);
        setErrorMessage(getErrorMessage(error)); // Use error response message from backend
      });
  };

  const handleSearch = async () => {
    try {
      let response;
      if (!searchKeyword.trim()) {
        // If search keyword is empty, fetch all software again
        response = await RegularUserService.viewSoftwares();
      } else {
        // Otherwise, perform search based on the selected field
        switch (searchField) {
          case 'id':
            response = await RegularUserService.searchSoftwareById(searchKeyword);
            break;
          case 'name':
            response = await RegularUserService.searchSoftwareByName(searchKeyword);
            break;
          case 'purchaseDate':
            response = await RegularUserService.searchSoftwareByPurchaseDate(searchKeyword);
            break;
          case 'licenseKey':
            response = await RegularUserService.searchSoftwareByLicenseKey(searchKeyword);
            break;
          case 'expiryDate':
            response = await RegularUserService.searchSoftwareByExpiryDate(searchKeyword);
            break;
          case 'supportEndDate':
            response = await RegularUserService.searchSoftwareBySupportEndDate(searchKeyword);
            break;
          case 'status':
            response = await RegularUserService.searchSoftwareByStatus(searchKeyword);
            break;
          default:
            response = [];
            break;
        }
      }
      setSoftware(Array.isArray(response) ? response : [response]); // Ensure response is an array
      setErrorState(false); // Reset error state on successful search
    } catch (error) {
      console.error('Error searching software:', error);
      // Extract error message from the response if available
      setErrorMessage(getErrorMessage(error));
      setErrorState(true);
    }
  };

  const handleInputChange = (e) => {
    setSearchKeyword(e.target.value);
  };

  const handleSelectChange = (e) => {
    setSearchField(e.target.value);
    setSearchKeyword(''); // Clear search keyword when changing the field
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  const getErrorMessage = (error) => {
    return error.response ? error.response.data : 'An error occurred while searching software. Please try again later.';
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">View Software</h2>
      <div className="search-container">
        <div className="search-input-container">
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
            <img src={searchIcon} alt="Search Icon" className="search-icon" />
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
          <option value="purchaseDate">Purchase Date</option>
          <option value="licenseKey">License Key</option>
          <option value="expiryDate">Expiration Date</option>
          <option value="supportEndDate">End of Support Date</option>
          <option value="status">Status</option>
        </select>
      </div>
      {errorState && (
        <div className="alert alert-danger" role="alert">
          {errorMessage}
        </div>
      )}
      <div className="table-responsive">
        <table className="table table-bordered table-hover mt-4 custom-table">
          <thead className="table-dark">
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>License Key</th>
              <th>Purchase Date</th>
              <th>Expiration Date</th>
              <th>End of Support Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {software.map(softwareItem => (
              <tr key={softwareItem.softwareId}>
                <td>{softwareItem.softwareId}</td>
                <td>{softwareItem.softwareName}</td>
                <td>{softwareItem.licenseKey}</td>
                <td>{softwareItem.purchaseDate}</td>
                <td>{softwareItem.expiryDate}</td>
                <td>{softwareItem.supportEndDate}</td>
                <td>{softwareItem.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default UserViewSoftware;
