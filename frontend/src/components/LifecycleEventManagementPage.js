import React, { useState, useEffect, useRef } from 'react';
import LifecycleEventService from '../services/LifecycleEventService';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const LifecycleEventManagementPage = () => {
  const [newEvent, setNewEvent] = useState({
    assetId: '',
    eventType: '',
    eventDate: '',
    description: '',
    category: ''
  });
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const form = useRef();

  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = () => {
    const token = localStorage.getItem('token');
    LifecycleEventService.getAllLifecycleEvents(token)
      .then(response => {
        setEvents(response);
      })
      .catch(error => {
        console.error('Error fetching events:', error);
      });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewEvent({ ...newEvent, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);

    const token = localStorage.getItem('token');
    LifecycleEventService.addLifecycleEvent(newEvent, token)
      .then(() => {
        alert('Event added successfully');
        setNewEvent({
          assetId: '',
          eventType: '',
          eventDate: '',
          description: '',
          category: ''
        });
        fetchEvents();
      })
      .catch(error => {
        console.error('Error adding event:', error);
        alert('An error occurred while adding event');
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const deleteEvent = (eventId) => {
    const confirmDelete = window.confirm('Are you sure you want to delete this event?');
    if (confirmDelete) {
      const token = localStorage.getItem('token');
      LifecycleEventService.deleteLifecycleEvent(eventId, token)
        .then(() => {
          fetchEvents();
        })
        .catch(error => {
          console.error('Error deleting event:', error);
        });
    }
  };

  const fetchEventById = async (eventId) => {
    const token = localStorage.getItem('token');
    try {
      const event = await LifecycleEventService.getLifecycleEventById(eventId, token);
      setSelectedEvent(event);
    } catch (error) {
      console.error('Error fetching event by ID:', error);
    }
  };

  const handleClose = () => {
    setSelectedEvent(null);
  };

  const generateReport = () => {
    const header = ['Event ID', 'Asset ID', 'Event Type', 'Event Date', 'Description', 'Category'];
    const rows = events.map(event => [event.eventId, event.relatedId, event.eventType, event.eventDate, event.description, event.category]);

    // Joining header and rows into a single array
    const reportData = [header, ...rows];

    // Converting array data into CSV format
    const csvContent = reportData.map(row => row.join(',')).join('\n');

    // Creating a blob with the CSV content
    const blob = new Blob([csvContent], { type: 'text/csv' });

    // Creating a URL for the blob and creating a link to trigger the download
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'event_report.csv');
    document.body.appendChild(link);

    // Clicking the link to trigger the download
    link.click();
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Lifecycle Event Management Page</h2>
      <div className="card">
        <div className="card-body">
          <form onSubmit={handleSubmit} ref={form}>
            <div className="mb-3 row">
              <label htmlFor="assetId" className="col-sm-2 col-form-label">Related ID:</label>
              <div className="col-sm-10">
                <input type="text" className="form-control" id="assetId" name="assetId" value={newEvent.assetId} onChange={handleInputChange} required />
              </div>
            </div>
            <div className="mb-3 row">
              <label htmlFor="eventType" className="col-sm-2 col-form-label">Event Type:</label>
              <div className="col-sm-10">
                <input type="text" className="form-control" id="eventType" name="eventType" value={newEvent.eventType} onChange={handleInputChange} required />
              </div>
            </div>
            <div className="mb-3 row">
              <label htmlFor="eventDate" className="col-sm-2 col-form-label">Event Date:</label>
              <div className="col-sm-10">
                <input type="date" className="form-control" id="eventDate" name="eventDate" value={newEvent.eventDate} onChange={handleInputChange} required />
              </div>
            </div>
            <div className="mb-3 row">
              <label htmlFor="description" className="col-sm-2 col-form-label">Description:</label>
              <div className="col-sm-10">
                <input type="text" className="form-control" id="description" name="description" value={newEvent.description} onChange={handleInputChange} required />
              </div>
            </div>
            <div className="mb-3 row">
              <label htmlFor="category" className="col-sm-2 col-form-label">Category:</label>
              <div className="col-sm-10">
                <input type="text" className="form-control" id="category" name="category" value={newEvent.category} onChange={handleInputChange} required />
              </div>
            </div>
            <div className="mb-3">
              <button type="submit" className="btn btn-primary" disabled={loading}>Add Event</button>
            </div>
          </form>
        </div>
      </div>

      <div className="table-responsive mt-4">
        <table className="table table-striped">
          <thead className="table-dark">
            <tr>
              <th>Event ID</th>
              <th>Asset ID</th>
              <th>Event Type</th>
              <th>Event Date</th>
              <th>Description</th>
              <th>Category</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {events.map(event => (
              <tr key={event.eventId}>
                <td>{event.eventId}</td>
                <td>{event.assetId}</td>
                <td>{event.eventType}</td>
                <td>{event.eventDate}</td>
                <td>{event.description}</td>
                <td>{event.category}</td>
                <td>
                  <button className="btn btn-primary btn-sm me-2" onClick={() => fetchEventById(event.eventId)}>View</button>
                  <button className="btn btn-danger btn-sm me-2" onClick={() => deleteEvent(event.eventId)}>Delete</button>
                  <button className="btn btn-secondary btn-sm">
                    <Link to={`/update-lifecycleEvent/${event.eventId}`} className="text-white text-decoration-none">Update</Link>
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {selectedEvent && (
        <div className="mt-4">
          <button className="btn btn-danger btn-sm mt-2" onClick={handleClose}>Close</button>
          <h3>Selected Event Details:</h3>
          <p>Event ID: {selectedEvent.eventId}</p>
          <p>Related ID: {selectedEvent.assetId}</p>
          <p>Event Type: {selectedEvent.eventType}</p>
          <p>Event Date: {selectedEvent.eventDate}</p>
          <p>Description: {selectedEvent.description}</p>
          <p>Category: {selectedEvent.category}</p>
          
        </div>
      )}

      <div className="mt-4">
        <button className="btn btn-success" onClick={generateReport}>Generate Report</button>
      </div>
    </div>
  );
};

export default LifecycleEventManagementPage;
