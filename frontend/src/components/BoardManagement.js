import React, { useState, useEffect } from 'react';
import ManagementService from '../services/ManagementService';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS

const BoardManagement = ({ setGeneratedReportProp }) => {
  const [lifecycleEvents, setLifecycleEvents] = useState([]);
  const [selectedAssets, setSelectedAssets] = useState([]);
  const [errorState, setErrorState] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [generatedReport, setGeneratedReport] = useState(null);

  useEffect(() => {
    fetchLifecycleEvents();
  }, []);

  const fetchLifecycleEvents = () => {
    ManagementService.viewAllLifecycleEvents()
      .then(response => {
        setLifecycleEvents(response);
        setErrorState(false); // Reset error state on successful fetch
      })
      .catch(error => {
        console.error('Error fetching lifecycle events:', error);
        setErrorState(true);
        setErrorMessage(error.response.data); // Use error response message from backend
      });
  };

  const generateReport = async () => {
    try {
      // Perform report generation for the selected assets
      console.log('Generating report for selected assets:', selectedAssets);
      // Implement report generation logic here, e.g., making an API call
      const reportResponse = await ManagementService.generateLifecycleReports(selectedAssets);
      console.log('Report generated:', reportResponse);
      // Set the generated report
      setGeneratedReport(reportResponse);
      // Export as CSV
      const headers = ['event_id', 'asset_id', 'category', 'description', 'event_date', 'event_type'];
      exportAsCSV(reportResponse, headers);
    } catch (error) {
      console.error('Error generating report:', error);
      // Handle error or display an error message to the user
    }
  };

  const handleCheckboxChange = (assetId) => {
    // Toggle selection of the asset
    if (selectedAssets.includes(assetId)) {
      setSelectedAssets(selectedAssets.filter(id => id !== assetId));
    } else {
      setSelectedAssets([...selectedAssets, assetId]);
    }
  };

  const exportAsCSV = (reportData, headers) => {
    // Combine headers and report data
    const dataWithHeaders = [headers.join(",")].concat(reportData.map(row => Object.values(row).join(",")));
    
    // Convert data into CSV format
    const csvContent = dataWithHeaders.join("\n");
    
    // Create a blob with the CSV content
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });

    // Create a URL for the blob and create a link to trigger the download
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "generated_report.csv");
    document.body.appendChild(link);

    // Click the link to trigger the download
    link.click();
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Board Management</h2>
      {errorState && (
        <div className="alert alert-danger" role="alert">
          {errorMessage}
        </div>
      )}
      <button
        className="btn btn-primary mb-3"
        onClick={generateReport}
        disabled={selectedAssets.length === 0}
      >
        Generate Report for Selected Assets
      </button>
      <table className="table mt-4">
        <thead>
          <tr>
            <th></th>
            <th>Event ID</th>
            <th>Asset ID</th>
            <th>Event Type</th>
            <th>Event Date</th>
            <th>Description</th>
            <th>Category</th>
          </tr>
        </thead>
        <tbody>
          {lifecycleEvents.map(event => (
            <tr key={event.eventId}>
              <td>
                <input
                  type="checkbox"
                  checked={selectedAssets.includes(event.assetId)}
                  onChange={() => handleCheckboxChange(event.assetId)}
                />
              </td>
              <td>{event.eventId}</td>
              <td>{event.assetId}</td>
              <td>{event.eventType}</td>
              <td>{event.eventDate}</td>
              <td>{event.description}</td>
              <td>{event.category}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Display the generated report */}
      {generatedReport && (
        <div className="mt-5">
          <h3>Generated Report</h3>
          {generatedReport.map(report => (
            <div key={report.eventId} className="card mb-3">
              <div className="card-body">
                <h5 className="card-title">Event ID: {report.eventId}</h5>
                <p className="card-text">Asset ID: {report.assetId}</p>
                <p className="card-text">Event Type: {report.eventType}</p>
                <p className="card-text">Event Date: {report.eventDate}</p>
                <p className="card-text">Description: {report.description}</p>
                <p className="card-text">Category: {report.category}</p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default BoardManagement;
