// DeviceService.js
import axios from "axios";
import authHeader from "./auth-header";

const BASE_URL = "http://localhost:8080/api";

const addDevice = async (device, token) => {
  try {
    const response = await axios.post(`${BASE_URL}/admin/adddevices`, device, {
      headers: {
        'Content-Type': 'application/json',
        ...authHeader(),
        'Access-Control-Allow-Origin': '*'
      }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const updateDevice = async (device) => {
  try {
    const response = await axios.put(`${BASE_URL}/admin/updatedevices`, device, {
      headers: { 
        ...authHeader() ,
        'Access-Control-Allow-Origin': '*'
      }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const deleteDevice = async (deviceId) => {
  try {
    const response = await axios.post(`${BASE_URL}/admin/devices/deletedevices`, deviceId, {
      headers: { 
        'Content-Type': 'application/json',
        ...authHeader(), 
        'Access-Control-Allow-Origin': '*'

       }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const viewDevices = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/admin/getalldevices`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const getDeviceById = async (deviceId, token) => {
  try {
    const response = await axios.get(`${BASE_URL}/admin/devices/searchById`, {
      params: { deviceId },
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const DeviceService = {
  addDevice,
  updateDevice,
  deleteDevice,
  viewDevices,
  getDeviceById,
};

export default DeviceService;
