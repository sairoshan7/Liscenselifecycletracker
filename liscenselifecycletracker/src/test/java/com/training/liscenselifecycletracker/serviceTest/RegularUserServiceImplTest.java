package com.training.liscenselifecycletracker.serviceTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.training.liscenselifecycletracker.entities.Device;
import com.training.liscenselifecycletracker.entities.Software;
import com.training.liscenselifecycletracker.exceptions.DeviceNotFoundException;
import com.training.liscenselifecycletracker.exceptions.SoftwareNotFoundException;
import com.training.liscenselifecycletracker.repositories.DeviceRepository;
import com.training.liscenselifecycletracker.repositories.SoftwareRepository;
import com.training.liscenselifecycletracker.service.RegularUserServiceImpl;

public class RegularUserServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private SoftwareRepository softwareRepository;

    @InjectMocks
    private RegularUserServiceImpl regularUserService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for viewDevices method when devices exist
    @Test
    public void testViewDevices_DevicesExist() throws DeviceNotFoundException {
        List<Device> deviceList = new ArrayList<>();
        Device device1 = new Device();
        device1.setDeviceId(1L);
        Device device2 = new Device();
        device2.setDeviceId(2L);
        deviceList.add(device1);
        deviceList.add(device2);

        when(deviceRepository.findAll()).thenReturn(deviceList);

        ResponseEntity<List<Device>> response = regularUserService.viewDevices();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }

    // Test for viewDevices method when no devices exist
    @Test
    public void testViewDevices_NoDevicesFound() {
        when(deviceRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> {
            regularUserService.viewDevices();
        });
    }

    // Test for viewSoftwares method when softwares exist
    @Test
    public void testViewSoftwares_SoftwaresExist() throws SoftwareNotFoundException {
        List<Software> softwareList = new ArrayList<>();
        Software software1 = new Software();
        software1.setSoftwareId(1L);
        Software software2 = new Software();
        software2.setSoftwareId(2L);
        softwareList.add(software1);
        softwareList.add(software2);

        when(softwareRepository.findAll()).thenReturn(softwareList);

        ResponseEntity<List<Software>> response = regularUserService.viewSoftwares();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    // Test for viewSoftwares method when no softwares exist
    @Test
    public void testViewSoftwares_NoSoftwaresFound() {
        when(softwareRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            regularUserService.viewSoftwares();
        });
    }

    // Test for receiveNotifications method when there are expiring software licenses
    @Test
    public void testReceiveNotifications_ExpiringLicensesExist() {
        LocalDate now = LocalDate.now();
        LocalDate expiryDateWithin30Days = now.plusDays(15);
        LocalDate expiryDateBeyond30Days = now.plusDays(35);

        Software expiringSoftware1 = new Software();
        expiringSoftware1.setSoftwareId(1L);
        expiringSoftware1.setExpiryDate(expiryDateWithin30Days);

        Software expiringSoftware2 = new Software();
        expiringSoftware2.setSoftwareId(2L);
        expiringSoftware2.setExpiryDate(expiryDateBeyond30Days);

        List<Software> expiringSoftwareList = new ArrayList<>();
        expiringSoftwareList.add(expiringSoftware1);
        expiringSoftwareList.add(expiringSoftware2);

        when(softwareRepository.findByExpiryDate(now.plusDays(30))).thenReturn(expiringSoftwareList);

        ResponseEntity<List<String>> response = regularUserService.receiveNotifications();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    // Test for receiveNotifications method when there are no expiring software licenses
    @Test
    public void testReceiveNotifications_NoExpiringLicenses() {
        LocalDate now = LocalDate.now();
        when(softwareRepository.findByExpiryDate(now.plusDays(30))).thenReturn(Collections.emptyList());

        ResponseEntity<List<String>> response = regularUserService.receiveNotifications();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    // Add similar test methods for other search methods

    // Test for searchDevicesById method when device exists
    @Test
    public void testSearchDevicesById_DeviceExists() throws DeviceNotFoundException {
        Long deviceId = 1L;
        Device device = new Device();
        device.setDeviceId(deviceId);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        ResponseEntity<Device> response = regularUserService.searchDevicesById(deviceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(device, response.getBody());
    }

    // Test for searchDevicesById method when device does not exist
    @Test
    public void testSearchDevicesById_DeviceNotFound() {
        Long deviceId = 1L;

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            regularUserService.searchDevicesById(deviceId);
        });
    }

    // Add similar test methods for other search methods
    // Test for searchDevicesByName method when devices exist
    @Test
    public void testSearchDevicesByName_DevicesExist() throws DeviceNotFoundException {
        String deviceName = "TestDevice";
        List<Device> deviceList = new ArrayList<>();
        Device device1 = new Device();
        device1.setDeviceId(1L);
        Device device2 = new Device();
        device2.setDeviceId(2L);
        deviceList.add(device1);
        deviceList.add(device2);

        when(deviceRepository.findByDeviceName(deviceName)).thenReturn(deviceList);

        ResponseEntity<List<Device>> response = regularUserService.searchDevicesByName(deviceName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }

    // Test for searchDevicesByName method when no devices exist
    @Test
    public void testSearchDevicesByName_NoDevicesFound() {
        String deviceName = "NonExistentDevice";

        when(deviceRepository.findByDeviceName(deviceName)).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> {
            regularUserService.searchDevicesByName(deviceName);
        });
    }

    // Test for searchDevicesByType method when devices exist
    @Test
    public void testSearchDevicesByType_DevicesExist() throws DeviceNotFoundException {
        String deviceType = "Laptop";
        List<Device> deviceList = new ArrayList<>();
        Device device1 = new Device();
        device1.setDeviceId(1L);
        Device device2 = new Device();
        device2.setDeviceId(2L);
        deviceList.add(device1);
        deviceList.add(device2);

        when(deviceRepository.findByDeviceType(deviceType)).thenReturn(deviceList);

        ResponseEntity<List<Device>> response = regularUserService.searchDevicesByType(deviceType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }

    // Test for searchDevicesByType method when no devices exist
    @Test
    public void testSearchDevicesByType_NoDevicesFound() {
        String deviceType = "NonExistentType";

        when(deviceRepository.findByDeviceType(deviceType)).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> {
            regularUserService.searchDevicesByType(deviceType);
        });
    }

    // Add similar test methods for other search methods

    // Test for searchSoftwareByName method when software exists
    @Test
    public void testSearchSoftwareByName_SoftwareExists() throws SoftwareNotFoundException {
        String softwareName = "TestSoftware";
        List<Software> softwareList = new ArrayList<>();
        Software software1 = new Software();
        software1.setSoftwareId(1L);
        Software software2 = new Software();
        software2.setSoftwareId(2L);
        softwareList.add(software1);
        softwareList.add(software2);

        when(softwareRepository.findBySoftwareName(softwareName)).thenReturn(softwareList);

        ResponseEntity<List<Software>> response = regularUserService.searchSoftwareByName(softwareName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    // Test for searchSoftwareByName method when no software exists
    @Test
    public void testSearchSoftwareByName_NoSoftwareFound() {
        String softwareName = "NonExistentSoftware";

        when(softwareRepository.findBySoftwareName(softwareName)).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            regularUserService.searchSoftwareByName(softwareName);
        });
    }
    
 // Continued from the previous code...

    // Test for searchSoftwareById method when software exists
    @Test
    public void testSearchSoftwareById_SoftwareExists() throws SoftwareNotFoundException {
        Long softwareId = 1L;
        Software software = new Software();
        software.setSoftwareId(softwareId);

        when(softwareRepository.findBySoftwareId(softwareId)).thenReturn(Collections.singletonList(software));

        ResponseEntity<List<Software>> response = regularUserService.searchSoftwareById(softwareId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(software), response.getBody());
    }

    // Test for searchSoftwareById method when software does not exist
    @Test
    public void testSearchSoftwareById_SoftwareNotFound() {
        Long softwareId = 1L;

        when(softwareRepository.findBySoftwareId(softwareId)).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            regularUserService.searchSoftwareById(softwareId);
        });
    }

    // Test for searchSoftwareByPurchaseDate method when software exists
    @Test
    public void testSearchSoftwareByPurchaseDate_SoftwareExists() throws SoftwareNotFoundException {
        LocalDate purchaseDate = LocalDate.now();
        List<Software> softwareList = new ArrayList<>();
        Software software1 = new Software();
        software1.setSoftwareId(1L);
        Software software2 = new Software();
        software2.setSoftwareId(2L);
        softwareList.add(software1);
        softwareList.add(software2);

        when(softwareRepository.findByPurchaseDate(purchaseDate)).thenReturn(softwareList);

        ResponseEntity<List<Software>> response = regularUserService.searchSoftwareByPurchaseDate(purchaseDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    // Test for searchSoftwareByPurchaseDate method when no software exists
    @Test
    public void testSearchSoftwareByPurchaseDate_NoSoftwareFound() {
        LocalDate purchaseDate = LocalDate.now();

        when(softwareRepository.findByPurchaseDate(purchaseDate)).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            regularUserService.searchSoftwareByPurchaseDate(purchaseDate);
        });
    }

    // Add similar test methods for other search methods
 // Continued from the previous code...

    // Test for searchSoftwareByExpiryDate method when software exists
    @Test
    public void testSearchSoftwareByExpiryDate_SoftwareExists() throws SoftwareNotFoundException {
        LocalDate expiryDate = LocalDate.now();
        List<Software> softwareList = new ArrayList<>();
        Software software1 = new Software();
        software1.setSoftwareId(1L);
        Software software2 = new Software();
        software2.setSoftwareId(2L);
        softwareList.add(software1);
        softwareList.add(software2);

        when(softwareRepository.findByExpiryDate(expiryDate)).thenReturn(softwareList);

        ResponseEntity<List<Software>> response = regularUserService.searchSoftwareByExpiryDate(expiryDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    // Test for searchSoftwareByExpiryDate method when no software exists
    @Test
    public void testSearchSoftwareByExpiryDate_NoSoftwareFound() {
        LocalDate expiryDate = LocalDate.now();

        when(softwareRepository.findByExpiryDate(expiryDate)).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            regularUserService.searchSoftwareByExpiryDate(expiryDate);
        });
    }

    // Test for searchSoftwareBySupportEndDate method when software exists
    @Test
    public void testSearchSoftwareBySupportEndDate_SoftwareExists() throws SoftwareNotFoundException {
        LocalDate supportEndDate = LocalDate.now();
        List<Software> softwareList = new ArrayList<>();
        Software software1 = new Software();
        software1.setSoftwareId(1L);
        Software software2 = new Software();
        software2.setSoftwareId(2L);
        softwareList.add(software1);
        softwareList.add(software2);

        when(softwareRepository.findBySupportEndDate(supportEndDate)).thenReturn(softwareList);

        ResponseEntity<List<Software>> response = regularUserService.searchSoftwareBySupportEndDate(supportEndDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    // Test for searchSoftwareBySupportEndDate method when no software exists
    @Test
    public void testSearchSoftwareBySupportEndDate_NoSoftwareFound() {
        LocalDate supportEndDate = LocalDate.now();

        when(softwareRepository.findBySupportEndDate(supportEndDate)).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            regularUserService.searchSoftwareBySupportEndDate(supportEndDate);
        });
    }

    // Test for searchSoftwareByStatus method when software exists
    @Test
    public void testSearchSoftwareByStatus_SoftwareExists() throws SoftwareNotFoundException {
        String status = "Active";
        List<Software> softwareList = new ArrayList<>();
        Software software1 = new Software();
        software1.setSoftwareId(1L);
        Software software2 = new Software();
        software2.setSoftwareId(2L);
        softwareList.add(software1);
        softwareList.add(software2);

        when(softwareRepository.findByStatus(status)).thenReturn(softwareList);

        ResponseEntity<List<Software>> response = regularUserService.searchSoftwareByStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    // Test for searchSoftwareByStatus method when no software exists
    @Test
    public void testSearchSoftwareByStatus_NoSoftwareFound() {
        String status = "Inactive";

        when(softwareRepository.findByStatus(status)).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            regularUserService.searchSoftwareByStatus(status);
        });
    }

    // Test for searchSoftwareByLicenseKey method when software exists
    @Test
    public void testSearchSoftwareByLicenseKey_SoftwareExists() throws SoftwareNotFoundException {
        String licenseKey = "ABC123";
        List<Software> softwareList = new ArrayList<>();
        Software software1 = new Software();
        software1.setSoftwareId(1L);
        Software software2 = new Software();
        software2.setSoftwareId(2L);
        softwareList.add(software1);
        softwareList.add(software2);

        when(softwareRepository.findByLicenseKey(licenseKey)).thenReturn(softwareList);

        ResponseEntity<List<Software>> response = regularUserService.searchSoftwareByLicenseKey(licenseKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    // Test for searchSoftwareByLicenseKey method when no software exists
    @Test
    public void testSearchSoftwareByLicenseKey_NoSoftwareFound() {
        String licenseKey = "NonExistentKey";

        when(softwareRepository.findByLicenseKey(licenseKey)).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            regularUserService.searchSoftwareByLicenseKey(licenseKey);
        });
    }
 // Continued from the previous code...

    // Test for searchDevicesByPurchaseDate method when devices exist
    @Test
    public void testSearchDevicesByPurchaseDate_DevicesExist() throws DeviceNotFoundException {
        LocalDate purchaseDate = LocalDate.now();
        List<Device> deviceList = new ArrayList<>();
        Device device1 = new Device();
        device1.setDeviceId(1L);
        Device device2 = new Device();
        device2.setDeviceId(2L);
        deviceList.add(device1);
        deviceList.add(device2);

        when(deviceRepository.findByPurchaseDate(purchaseDate)).thenReturn(deviceList);

        ResponseEntity<List<Device>> response = regularUserService.searchDevicesByPurchaseDate(purchaseDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }

    // Test for searchDevicesByPurchaseDate method when no devices exist
    @Test
    public void testSearchDevicesByPurchaseDate_NoDevicesFound() {
        LocalDate purchaseDate = LocalDate.now();

        when(deviceRepository.findByPurchaseDate(purchaseDate)).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> {
            regularUserService.searchDevicesByPurchaseDate(purchaseDate);
        });
    }

    // Test for searchDevicesByExpirationDate method when devices exist
    @Test
    public void testSearchDevicesByExpirationDate_DevicesExist() throws DeviceNotFoundException {
        LocalDate expirationDate = LocalDate.now();
        List<Device> deviceList = new ArrayList<>();
        Device device1 = new Device();
        device1.setDeviceId(1L);
        Device device2 = new Device();
        device2.setDeviceId(2L);
        deviceList.add(device1);
        deviceList.add(device2);

        when(deviceRepository.findByExpirationDate(expirationDate)).thenReturn(deviceList);

        ResponseEntity<List<Device>> response = regularUserService.searchDevicesByExpirationDate(expirationDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }

    // Test for searchDevicesByExpirationDate method when no devices exist
    @Test
    public void testSearchDevicesByExpirationDate_NoDevicesFound() {
        LocalDate expirationDate = LocalDate.now();

        when(deviceRepository.findByExpirationDate(expirationDate)).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> {
            regularUserService.searchDevicesByExpirationDate(expirationDate);
        });
    }

    // Test for searchDevicesByStatus method when devices exist
    @Test
    public void testSearchDevicesByStatus_DevicesExist() throws DeviceNotFoundException {
        String status = "Active";
        List<Device> deviceList = new ArrayList<>();
        Device device1 = new Device();
        device1.setDeviceId(1L);
        Device device2 = new Device();
        device2.setDeviceId(2L);
        deviceList.add(device1);
        deviceList.add(device2);

        when(deviceRepository.findByStatus(status)).thenReturn(deviceList);

        ResponseEntity<List<Device>> response = regularUserService.searchDevicesByStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }

    // Test for searchDevicesByStatus method when no devices exist
    @Test
    public void testSearchDevicesByStatus_NoDevicesFound() {
        String status = "Inactive";

        when(deviceRepository.findByStatus(status)).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> {
            regularUserService.searchDevicesByStatus(status);
        });
    }

    // Test for searchDevicesBySupportEndDate method when devices exist
    @Test
    public void testSearchDevicesBySupportEndDate_DevicesExist() throws DeviceNotFoundException {
        LocalDate supportEndDate = LocalDate.now();
        List<Device> deviceList = new ArrayList<>();
        Device device1 = new Device();
        device1.setDeviceId(1L);
        Device device2 = new Device();
        device2.setDeviceId(2L);
        deviceList.add(device1);
        deviceList.add(device2);

        when(deviceRepository.findBySupportEndDate(supportEndDate)).thenReturn(deviceList);

        ResponseEntity<List<Device>> response = regularUserService.searchDevicesBySupportEndDate(supportEndDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }

    // Test for searchDevicesBySupportEndDate method when no devices exist
    @Test
    public void testSearchDevicesBySupportEndDate_NoDevicesFound() {
        LocalDate supportEndDate = LocalDate.now();

        when(deviceRepository.findBySupportEndDate(supportEndDate)).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> {
            regularUserService.searchDevicesBySupportEndDate(supportEndDate);
        });
    }







}
