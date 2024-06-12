// TechnicalSupportService.js

import axios from 'axios';
import authHeader from './auth-header';

const BASE_URL = 'http://localhost:8080/api/technicalsupport';

const logFault = async (deviceId, description, date, category) => {
    try {
      const response = await axios.post(
        `${BASE_URL}/support/faults`,
        {
          deviceId,
          description,
          date,
          category
        },
        {
          headers: {
            'Content-Type': 'application/json',
            ...authHeader(),
          }
        }
      );
      return response.data;
    } catch (error) {
      console.error('Error logging fault:', error);
      throw error;
    }
  };
  
  const updateFaultLog = async (deviceId, repairDetails, category, eventType) => {
    try {
      const response = await axios.put(
        `${BASE_URL}/support/faults/update`,
        { deviceId, repairDetails, category, eventType },
        { headers: { ...authHeader(),
            'Content-Type': 'application/json',
         } } // Spread the authHeader object
      );
      return response.data;
    } catch (error) {
      throw error;
    }
  };

const viewEndOfSupportDates = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/support/dates`, { headers: { ...authHeader() } }); // Spread the authHeader object
    return response.data;
  } catch (error) {
    throw error;
  }
};

const TechnicalSupportService = {
  logFault,
  updateFaultLog,
  viewEndOfSupportDates,
};

export default TechnicalSupportService;
