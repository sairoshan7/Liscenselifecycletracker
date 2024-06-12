// ManagementService.js

import axios from "axios";
import authHeader from "./auth-header";

const BASE_URL = "http://localhost:8080/api/management";

const overseeLifecycle = async (assetId) => {
  try {
    const response = await axios.get(`${BASE_URL}/lifecycle?assetId=${assetId}`, {
      headers: {
        ...authHeader(),
        'Access-Control-Allow-Origin': '*'
      }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

const generateLifecycleReports = async (assetIds) => {
  try {
    // Check if assetIds is an array to determine if it's a single ID or multiple IDs
    const url = Array.isArray(assetIds)
      ? `${BASE_URL}/reports?assetIds=${assetIds.join(',')}`
      : `${BASE_URL}/reports/${assetIds}`;

    const response = await axios.get(url, {
      headers: {
        ...authHeader(),
        'Access-Control-Allow-Origin': '*'
      }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

const viewAllLifecycleEvents = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/lifecycle/events`, {
      headers: {
        ...authHeader(),
        'Access-Control-Allow-Origin': '*'
      }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

const ManagementService = {
  overseeLifecycle,
  generateLifecycleReports,
  viewAllLifecycleEvents,
};

export default ManagementService;
