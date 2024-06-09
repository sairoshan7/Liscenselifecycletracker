import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import LifecycleEventService from '../services/LifecycleEventService';
 
function UpdateLifecycleEvent() {
  const navigate = useNavigate();
  const { eventId } = useParams();
 
  const [lifecycleEventData, setLifecycleEventData] = useState({
    assetId: '',
    eventType: '',
    eventDate: '',
    description: '',
    category: ''
  });
 
  useEffect(() => {
    fetchLifecycleEventDataById(eventId);
  }, [eventId]);
 
  const fetchLifecycleEventDataById = async (eventId) => {
    try {
      const response = await LifecycleEventService.getLifecycleEventById(eventId);
      console.log(response);
      if (response) {
        setLifecycleEventData(response);
      } else {
        console.error('Error: Lifecycle event data is undefined.');
      }
    } catch (error) {
      console.error('Error fetching lifecycle event data:', error);
    }
  };
 
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setLifecycleEventData((prevLifecycleEventData) => ({
      ...prevLifecycleEventData,
      [name]: value
    }));
  };
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await LifecycleEventService.updateLifecycleEvent(lifecycleEventData);
      console.log(res);
      navigate("/admin/lifecycleEvent-management");
    } catch (error) {
      console.error('Error updating lifecycle event:', error);
      alert(error.message || 'An error occurred while updating lifecycle event.');
    }
  };
 
  return (
    <div className="auth-container">
      <h2>Update Lifecycle Event</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Related ID:</label>
          <input type="text" name="assetId" value={lifecycleEventData.assetId || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Event Type:</label>
          <input type="text" name="eventType" value={lifecycleEventData.eventType || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Event Date:</label>
          <input type="date" name="eventDate" value={lifecycleEventData.eventDate || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Description:</label>
          <input type="text" name="description" value={lifecycleEventData.description || ''} onChange={handleInputChange} />
        </div>
        <div className="form-group">
          <label>Category:</label>
          <input type="text" name="category" value={lifecycleEventData.category || ''} onChange={handleInputChange} />
        </div>
        <button type="submit">Update</button>
      </form>
    </div>
  );
}
 
export default UpdateLifecycleEvent;