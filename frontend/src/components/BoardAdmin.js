// BoardAdmin.js

import React, { useState } from "react";
import "../styles/BoardAdmin.css"; // Import external CSS
import "bootstrap/dist/css/bootstrap.min.css"; // Import Bootstrap CSS

const BoardAdmin = () => {
  const [selectedOption, setSelectedOption] = useState("");

  const handleDropdownChange = (event) => {
    const selectedPage = event.target.value;
    setSelectedOption(selectedPage);

    if (selectedPage === "device") {
      window.location.href = "/admin/device-management";
    } else if (selectedPage === "software") {
      window.location.href = "/admin/software-management";
    } else if (selectedPage === "lifecycle") {
      window.location.href = "/admin/lifecycle-management";
    } else if (selectedPage === "UpdateRole") {
      window.location.href = "/admin/Update-Role";
    }
  };

  return (
    <div className="admin-dashboard-container">
      <div className="admin-dashboard-content">
        <header className="admin-dashboard-header">
          <h2>Admin Dashboard</h2>
          <div className="admin-dropdown-form">
            <div className="form-group">
              <label htmlFor="dashboard">Select Dashboard:</label>
              <select
                id="dashboard"
                value={selectedOption}
                onChange={handleDropdownChange}
                className="form-control"
              >
                <option value="">Select an option</option>
                <option value="device">Device Management</option>
                <option value="software">Software Management</option>
                <option value="lifecycle">Lifecycle Management</option>
                <option value="UpdateRole">Update UserRole</option>
              </select>
            </div>
          </div>
        </header>
      </div>
    </div>
  );
};

export default BoardAdmin;
