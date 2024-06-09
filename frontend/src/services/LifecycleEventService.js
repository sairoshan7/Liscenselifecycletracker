import axios from "axios";
import authHeader from "./auth-header";
 
const BASE_URL = "http://localhost:8080/api";
 
const addLifecycleEvent = async (lifecycleEvent) => {
  try {
    const response = await axios.post(`${BASE_URL}/admin/addlifecycleevent`, lifecycleEvent, {
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
 
const updateLifecycleEvent = async (lifecycleEvent, token) => {
  try {
    const response = await axios.put(`${BASE_URL}/admin/updatelifecycleevent`, lifecycleEvent, {
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
 
const deleteLifecycleEvent = async (eventId, token) => {
  try {
    //const response = await axios.post(`${BASE_URL}/admin/deletesoftware/${softwareId}`, null, {
    const response = await axios.post(`${BASE_URL}/admin/deletelifecyclevent`, eventId, {
        // params: { eventId },
        headers: {
          'Content-Type': 'application/json',
            ...authHeader() ,
           'Access-Control-Allow-Origin': '*'
           }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};
 
const getAllLifecycleEvents = async (token) => {
  try {
    const response = await axios.get(`${BASE_URL}/admin/getalllifecycleevents`, {
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
 
const getLifecycleEventById = async (eventId) => {
  try {
    const response = await axios.get(`${BASE_URL}/admin/getlifecycleevent?eventId=${eventId}`, {
      headers: {
        ...authHeader(),
        'Access-Control-Allow-Origin': '*'
      }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};
 
const LifecycleEventService = {
  addLifecycleEvent,
  updateLifecycleEvent,
  deleteLifecycleEvent,
  getAllLifecycleEvents,
  getLifecycleEventById,
};
 
export default LifecycleEventService;