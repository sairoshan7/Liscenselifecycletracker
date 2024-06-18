import React, { useState } from "react";
import "../styles/BoardAdmin.css"; // Import external CSS
import "bootstrap/dist/css/bootstrap.min.css"; // Import Bootstrap CSS

const BoardTechnicalsupport = () => {
  const [selectedOption, setSelectedOption] = useState("");

  const handleDropdownChange = (event) => {
    const selectedPage = event.target.value;
    setSelectedOption(selectedPage);

    if (selectedPage === "logfault") {
      window.location.href = "/technicalsupport/log-fault";
    } else if (selectedPage === "updatelogfault") {
      window.location.href = "/technicalsupport/update-log-fault";
    } else if (selectedPage === "viewendofsupportdates") {
      window.location.href = "/technicalsupport/view-end-of-support-dates";
    }
  };

  return (
    <div className="admin-dashboard-container"> {/* Apply the same class name */}
      <div className="admin-dashboard-content"> {/* Apply the same class name */}
        <header className="admin-dashboard-header"> {/* Apply the same class name */}
          <h2>Technical Support Dashboard</h2>
          <div className="admin-dropdown-form"> {/* Apply the same class name */}
            <div className="form-group">
              <label htmlFor="dashboard">Select Dashboard:</label>
              <select
                id="dashboard"
                value={selectedOption}
                onChange={handleDropdownChange}
                className="form-control" // Apply the same class name
              >
                <option value="">Select an option</option>
                <option value="logfault">Log Fault</option>
                <option value="updatelogfault">Update Log Fault</option>
                <option value="viewendofsupportdates">View End of Support Dates</option>
              </select>
            </div>
          </div>
        </header>
      </div>
    </div>
  );
};

export default BoardTechnicalsupport;
