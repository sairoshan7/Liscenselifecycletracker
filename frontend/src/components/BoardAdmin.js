import React, { useState } from "react";
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
    }
  };

  return (
    <div className="container mt-5"> {/* Apply Bootstrap's container class */}
      <header className="jumbotron">
        <h2>Admin Dashboard</h2>
        <div className="form-group mt-3"> {/* Apply Bootstrap's form-group class */}
          <label htmlFor="dashboard">Select Dashboard:</label>
          <select
            id="dashboard"
            value={selectedOption}
            onChange={handleDropdownChange}
            className="form-control mt-2" // Apply Bootstrap's form-control and mt-2 classes
          >
            <option value="">Select an option</option>
            <option value="device">Device Management</option>
            <option value="software">Software Management</option>
            <option value="lifecycle">Lifecycle Management</option>
          </select>
        </div>
      </header>
    </div>
  );
};

export default BoardAdmin;
