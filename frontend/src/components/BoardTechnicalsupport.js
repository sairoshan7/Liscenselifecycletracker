import React, { useState } from "react";
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
    <div className="container mt-5"> {/* Apply Bootstrap's container class */}
      <header className="jumbotron">
        <h2>Technical Support Dashboard</h2>
        <div className="form-group mt-3"> {/* Apply Bootstrap's form-group class */}
          <label htmlFor="dashboard">Select Dashboard:</label>
          <select
            id="dashboard"
            value={selectedOption}
            onChange={handleDropdownChange}
            className="form-control mt-2" // Apply Bootstrap's form-control and mt-2 classes
          >
            <option value="">Select an option</option>
            <option value="logfault">Log Fault</option>
            <option value="updatelogfault">Update Log Fault</option>
            <option value="viewendofsupportdates">View End of Support Dates</option>
          </select>
        </div>
      </header>
    </div>
  );
};

export default BoardTechnicalsupport;
