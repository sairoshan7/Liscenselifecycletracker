import axios from "axios";
import authHeader from "./auth-header";
 
const BASE_URL = "http://localhost:8080/api";
 
const addSoftware = async (software) => {
  try {
    const response = await axios.post(`${BASE_URL}/admin/addsoftware`, software, {
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
 
const updateSoftware = async (software, token) => {
  try {
    const response = await axios.put(`${BASE_URL}/admin/updatesoftware`, software, {
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
 
const deleteSoftware = async (softwareId, token) => {
  try {
    const response = await axios.post(`${BASE_URL}/admin/deletesoftware/${softwareId}`, null, {
      params: { softwareId },
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};
 
const getAllSoftware = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/admin/getallsoftwares`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};
 
const getSoftwareById = async (softwareId, token) => {
  try {
    const response = await axios.get(`${BASE_URL}/admin/getsoftware?id=${softwareId}`, {
      params: { softwareId },
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};
 
const SoftwareService = {
  addSoftware,
  updateSoftware,
  deleteSoftware,
  getAllSoftware,
  getSoftwareById,
};
 
export default SoftwareService;