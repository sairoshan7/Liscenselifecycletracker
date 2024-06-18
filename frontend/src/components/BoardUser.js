import React, { useState } from "react";
import "../styles/BoardAdmin.css"; // Import external CSS for dropdown style
import "bootstrap/dist/css/bootstrap.min.css"; // Import Bootstrap CSS

const BoardUser = () => {
  const [selectedOption, setSelectedOption] = useState(""); // State for selected option

  // Function to handle dropdown change
  const handleDropdownChange = (event) => {
    const selectedPage = event.target.value; // Get selected page from dropdown
    setSelectedOption(selectedPage); // Set selected option state

    // Navigate to the selected page
    if (selectedPage === "device") {
      window.location.href = "/user/view-devices";
    } else if (selectedPage === "software") {
      window.location.href = "/user/view-software";
    }
  };

  return (
    <div className="admin-dashboard-container"> {/* Apply the same class name */}
      <div className="admin-dashboard-content"> {/* Apply the same class name */}
        <header className="admin-dashboard-header"> {/* Apply the same class name */}
          <h2>User Dashboard</h2>
          <div className="admin-dropdown-form"> {/* Apply the same class name */}
            <div className="form-group">
              <label htmlFor="viewSelect">Select View:</label>
              <select
                id="viewSelect"
                value={selectedOption}
                onChange={handleDropdownChange}
                className="form-control" // Apply Bootstrap's form-control class
              >
                <option value="">Select an option</option>
                <option value="device">View Devices</option>
                <option value="software">View Software</option>
              </select>
            </div>
          </div>
        </header>
      </div>
    </div>
  );
};

export default BoardUser;
