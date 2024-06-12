// ViewEndOfSupportDates.js

import React, { useState, useEffect } from 'react';
import TechnicalSupportService from '../services/TechnicalSupportService';

const ViewEndOfSupportDates = () => {
  const [supportDates, setSupportDates] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchEndOfSupportDates();
  }, []);

  const fetchEndOfSupportDates = async () => {
    try {
      const dates = await TechnicalSupportService.viewEndOfSupportDates();
      setSupportDates(dates);
      setLoading(false);
    } catch (error) {
      setError(error.message);
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>End of Support Dates</h2>
      {loading && <p>Loading...</p>}
      {error && <p>Error: {error}</p>}
      {!loading && !error && (
        <ul>
          {supportDates.map((date, index) => (
            <li key={index}>{date}</li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ViewEndOfSupportDates;
