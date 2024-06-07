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
    }
  };

  return (
    <div className="container mt-5"> {/* Apply Bootstrap's container class */}
      <header className="jumbotron">
        <label htmlFor="crud" className="dropdown-label">CRUD operations:</label>
        <select value={selectedOption} onChange={handleDropdownChange} className="form-select mt-2"> {/* Apply Bootstrap's form-select and mt-2 classes */}
          <option value="">Select an option</option>
          <option value="device">Device Management</option>
          <option value="software">Software Management</option>
        </select>
      </header>
    </div>
  );
};

export default BoardAdmin;
