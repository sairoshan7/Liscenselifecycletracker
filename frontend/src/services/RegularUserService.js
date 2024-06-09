import axios from "axios";
import authHeader from "./auth-header";

const BASE_URL = "http://localhost:8080/api/user";

const viewDevices = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/devices`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const receiveNotifications = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/notifications`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchAssets = async (keyword) => {
  try {
    const response = await axios.get(`${BASE_URL}/assets/search?keyword=${keyword}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchDevicesById = async (deviceId) => {
  try {
    const response = await axios.get(`${BASE_URL}/devices/searchById?deviceId=${deviceId}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchDevicesByName = async (deviceName) => {
  try {
    const response = await axios.get(`${BASE_URL}/devices/searchByName?deviceName=${deviceName}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchDevicesByType = async (deviceType) => {
  try {
    const response = await axios.get(`${BASE_URL}/devices/searchByType?deviceType=${deviceType}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchDevicesByPurchaseDate = async (purchaseDate) => {
  try {
    const response = await axios.get(`${BASE_URL}/devices/searchByPurchaseDate?purchaseDate=${purchaseDate}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchDevicesByExpirationDate = async (expirationDate) => {
  try {
    const response = await axios.get(`${BASE_URL}/devices/searchByExpirationDate?expirationDate=${expirationDate}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchDevicesByStatus = async (status) => {
  try {
    const response = await axios.get(`${BASE_URL}/devices/searchByStatus?status=${status}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchDevicesBySupportEndDate = async (supportEndDate) => {
  try {
    const response = await axios.get(`${BASE_URL}/devices/searchBySupportEndDate?supportEndDate=${supportEndDate}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const viewSoftwares = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/softwares`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchSoftwareById = async (softwareId) => {
  try {
    const response = await axios.get(`${BASE_URL}/software/searchById?softwareId=${softwareId}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchSoftwareByName = async (softwareName) => {
  try {
    const response = await axios.get(`${BASE_URL}/software/searchByName?softwareName=${softwareName}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchSoftwareByPurchaseDate = async (purchaseDate) => {
  try {
    const response = await axios.get(`${BASE_URL}/software/searchByPurchaseDate?purchaseDate=${purchaseDate}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchSoftwareByExpiryDate = async (expiryDate) => {
  try {
    const response = await axios.get(`${BASE_URL}/software/searchByExpiryDate?expiryDate=${expiryDate}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchSoftwareBySupportEndDate = async (supportEndDate) => {
  try {
    const response = await axios.get(`${BASE_URL}/software/searchBySupportEndDate?supportEndDate=${supportEndDate}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const searchSoftwareByStatus = async (status) => {
  try {
    const response = await axios.get(`${BASE_URL}/software/searchByStatus?status=${status}`, {
      headers: { ...authHeader() }
    });
    return response.data;
  } catch (err) {
    throw err;
  }
};

const RegularUserService = {
  viewDevices,
  viewSoftwares,
  receiveNotifications,
  searchAssets,
  searchDevicesById,
  searchDevicesByName,
  searchDevicesByType,
  searchDevicesByPurchaseDate,
  searchDevicesByExpirationDate,
  searchDevicesByStatus,
  searchDevicesBySupportEndDate,
  searchSoftwareById,
  searchSoftwareByName,
  searchSoftwareByPurchaseDate,
  searchSoftwareByExpiryDate,
  searchSoftwareBySupportEndDate,
  searchSoftwareByStatus
};

export default RegularUserService;
