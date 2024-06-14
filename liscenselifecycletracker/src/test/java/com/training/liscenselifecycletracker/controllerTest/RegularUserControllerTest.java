package com.training.liscenselifecycletracker.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.training.liscenselifecycletracker.controller.RegularUserController;
import com.training.liscenselifecycletracker.entities.Device;
import com.training.liscenselifecycletracker.entities.Software;
import com.training.liscenselifecycletracker.exceptions.DeviceNotFoundException;
import com.training.liscenselifecycletracker.exceptions.SoftwareNotFoundException;
import com.training.liscenselifecycletracker.service.RegularUser;

@ExtendWith(MockitoExtension.class)
public class RegularUserControllerTest {

    @Mock
    private RegularUser regularUser;

    @InjectMocks
    private RegularUserController regularUserController;

    @Test
    public void testViewDevices_Success() throws DeviceNotFoundException {
        // Mock data
        List<Device> devices = Arrays.asList(new Device(), new Device());

        // Mock service method behavior
        when(regularUser.viewDevices()).thenReturn(new ResponseEntity<>(devices, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.viewDevices();

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(devices, response.getBody());
    }

    @Test
    public void testViewDevices_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock service method behavior
        when(regularUser.viewDevices()).thenThrow(new DeviceNotFoundException("Devices not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.viewDevices();

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Devices not found", response.getBody());
    }
    
    
    @Test
    public void testViewSoftwares_Success() throws SoftwareNotFoundException {
        // Mock data
        List<Software> softwares = Arrays.asList(new Software(), new Software());

        // Mock service method behavior
        when(regularUser.viewSoftwares()).thenReturn(new ResponseEntity<>(softwares, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.viewSoftwares();

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwares, response.getBody());
    }

    @Test
    public void testViewSoftwares_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock service method behavior
        when(regularUser.viewSoftwares()).thenThrow(new SoftwareNotFoundException("Softwares not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.viewSoftwares();

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Softwares not found", response.getBody());
    }
    
    @Test
    public void testReceiveNotifications_Success() {
        // Mock data
        List<String> notifications = Arrays.asList("Notification1", "Notification2");

        // Mock service method behavior
        when(regularUser.receiveNotifications()).thenReturn(new ResponseEntity<>(notifications, HttpStatus.OK));

        // Perform the test
        ResponseEntity<List<String>> response = regularUserController.receiveNotifications();

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notifications, response.getBody());
    }

    @Test
    public void testSearchDevicesById_Success() throws DeviceNotFoundException {
        // Mock data
        long deviceId = 1L;
        Device device = new Device();

        // Mock service method behavior
        when(regularUser.searchDevicesById(deviceId)).thenReturn(new ResponseEntity<>(device, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesById(deviceId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(device, response.getBody());
    }

    @Test
    public void testSearchDevicesById_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        long deviceId = 1L;

        // Mock service method behavior
        when(regularUser.searchDevicesById(deviceId)).thenThrow(new DeviceNotFoundException("Device not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesById(deviceId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Device not found", response.getBody());
    }

    @Test
    public void testSearchDevicesByName_Success() throws DeviceNotFoundException {
        // Mock data
        String deviceName = "Device1";
        List<Device> devices = Arrays.asList(new Device(), new Device());

        // Mock service method behavior
        when(regularUser.searchDevicesByName(deviceName)).thenReturn(new ResponseEntity<>(devices, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByName(deviceName);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(devices, response.getBody());
    }

    @Test
    public void testSearchDevicesByName_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        String deviceName = "NonexistentDevice";

        // Mock service method behavior
        when(regularUser.searchDevicesByName(deviceName)).thenThrow(new DeviceNotFoundException("Device not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByName(deviceName);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Device not found", response.getBody());
    }

    @Test
    public void testSearchDevicesByType_Success() throws DeviceNotFoundException {
        // Mock data
        String deviceType = "Type1";
        List<Device> devices = Arrays.asList(new Device(), new Device());

        // Mock service method behavior
        when(regularUser.searchDevicesByType(deviceType)).thenReturn(new ResponseEntity<>(devices, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByType(deviceType);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(devices, response.getBody());
    }

    @Test
    public void testSearchDevicesByType_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        String deviceType = "NonexistentType";

        // Mock service method behavior
        when(regularUser.searchDevicesByType(deviceType)).thenThrow(new DeviceNotFoundException("Devices not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByType(deviceType);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Devices not found", response.getBody());
    }
    
    @Test
    public void testSearchDevicesByPurchaseDate_Success() throws DeviceNotFoundException {
        // Mock data
        LocalDate purchaseDate = LocalDate.of(2022, 6, 1);
        List<Device> devices = Arrays.asList(new Device(), new Device());

        // Mock service method behavior
        when(regularUser.searchDevicesByPurchaseDate(purchaseDate)).thenReturn(new ResponseEntity<>(devices, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByPurchaseDate(purchaseDate);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(devices, response.getBody());
    }

    @Test
    public void testSearchDevicesByPurchaseDate_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        LocalDate purchaseDate = LocalDate.of(2022, 6, 1);

        // Mock service method behavior
        when(regularUser.searchDevicesByPurchaseDate(purchaseDate)).thenThrow(new DeviceNotFoundException("Devices not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByPurchaseDate(purchaseDate);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Devices not found", response.getBody());
    }

    @Test
    public void testSearchDevicesByExpirationDate_Success() throws DeviceNotFoundException {
        // Mock data
        LocalDate expirationDate = LocalDate.of(2022, 12, 31);
        List<Device> devices = Arrays.asList(new Device(), new Device());

        // Mock service method behavior
        when(regularUser.searchDevicesByExpirationDate(expirationDate)).thenReturn(new ResponseEntity<>(devices, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByExpirationDate(expirationDate);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(devices, response.getBody());
    }

    @Test
    public void testSearchDevicesByExpirationDate_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        LocalDate expirationDate = LocalDate.of(2022, 12, 31);

        // Mock service method behavior
        when(regularUser.searchDevicesByExpirationDate(expirationDate)).thenThrow(new DeviceNotFoundException("Devices not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByExpirationDate(expirationDate);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Devices not found", response.getBody());
    }
    
    @Test
    public void testSearchDevicesByStatus_Success() throws DeviceNotFoundException {
        // Mock data
        String status = "Active";
        List<Device> devices = Arrays.asList(new Device(), new Device());

        // Mock service method behavior
        when(regularUser.searchDevicesByStatus(status)).thenReturn(new ResponseEntity<>(devices, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByStatus(status);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(devices, response.getBody());
    }

    @Test
    public void testSearchDevicesByStatus_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        String status = "NonexistentStatus";

        // Mock service method behavior
        when(regularUser.searchDevicesByStatus(status)).thenThrow(new DeviceNotFoundException("Devices not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesByStatus(status);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Devices not found", response.getBody());
    }

    @Test
    public void testSearchDevicesBySupportEndDate_Success() throws DeviceNotFoundException {
        // Mock data
        LocalDate supportEndDate = LocalDate.of(2022, 12, 31);
        List<Device> devices = Arrays.asList(new Device(), new Device());

        // Mock service method behavior
        when(regularUser.searchDevicesBySupportEndDate(supportEndDate)).thenReturn(new ResponseEntity<>(devices, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesBySupportEndDate(supportEndDate);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(devices, response.getBody());
    }

    @Test
    public void testSearchDevicesBySupportEndDate_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        LocalDate supportEndDate = LocalDate.of(2022, 12, 31);

        // Mock service method behavior
        when(regularUser.searchDevicesBySupportEndDate(supportEndDate)).thenThrow(new DeviceNotFoundException("Devices not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchDevicesBySupportEndDate(supportEndDate);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Devices not found", response.getBody());
    }

    @Test
    public void testSearchSoftwareById_Success() throws SoftwareNotFoundException {
        // Mock data
        Long softwareId = 1L;
        List<Software> softwareList = Arrays.asList(new Software(), new Software());

        // Mock service method behavior
        when(regularUser.searchSoftwareById(softwareId)).thenReturn(new ResponseEntity<>(softwareList, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareById(softwareId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    @Test
    public void testSearchSoftwareById_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        Long softwareId = 1L;

        // Mock service method behavior
        when(regularUser.searchSoftwareById(softwareId)).thenThrow(new SoftwareNotFoundException("Software not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareById(softwareId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }
    
    @Test
    public void testSearchSoftwareByName_Success() throws SoftwareNotFoundException {
        // Mock data
        String softwareName = "SampleSoftware";
        List<Software> softwareList = Arrays.asList(new Software(), new Software());

        // Mock service method behavior
        when(regularUser.searchSoftwareByName(softwareName)).thenReturn(new ResponseEntity<>(softwareList, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByName(softwareName);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    @Test
    public void testSearchSoftwareByName_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        String softwareName = "NonexistentSoftware";

        // Mock service method behavior
        when(regularUser.searchSoftwareByName(softwareName)).thenThrow(new SoftwareNotFoundException("Software not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByName(softwareName);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }

    @Test
    public void testSearchSoftwareByPurchaseDate_Success() throws SoftwareNotFoundException {
        // Mock data
        LocalDate purchaseDate = LocalDate.of(2022, 6, 1);
        List<Software> softwareList = Arrays.asList(new Software(), new Software());

        // Mock service method behavior
        when(regularUser.searchSoftwareByPurchaseDate(purchaseDate)).thenReturn(new ResponseEntity<>(softwareList, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByPurchaseDate(purchaseDate);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    @Test
    public void testSearchSoftwareByPurchaseDate_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        LocalDate purchaseDate = LocalDate.of(2022, 6, 1);

        // Mock service method behavior
        when(regularUser.searchSoftwareByPurchaseDate(purchaseDate)).thenThrow(new SoftwareNotFoundException("Software not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByPurchaseDate(purchaseDate);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }

    @Test
    public void testSearchSoftwareByExpiryDate_Success() throws SoftwareNotFoundException {
        // Mock data
        LocalDate expiryDate = LocalDate.of(2022, 12, 31);
        List<Software> softwareList = Arrays.asList(new Software(), new Software());

        // Mock service method behavior
        when(regularUser.searchSoftwareByExpiryDate(expiryDate)).thenReturn(new ResponseEntity<>(softwareList, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByExpiryDate(expiryDate);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    @Test
    public void testSearchSoftwareByExpiryDate_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        LocalDate expiryDate = LocalDate.of(2022, 12, 31);

        // Mock service method behavior
        when(regularUser.searchSoftwareByExpiryDate(expiryDate)).thenThrow(new SoftwareNotFoundException("Software not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByExpiryDate(expiryDate);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }

    @Test
    public void testSearchSoftwareBySupportEndDate_Success() throws SoftwareNotFoundException {
        // Mock data
        LocalDate supportEndDate = LocalDate.of(2022, 12, 31);
        List<Software> softwareList = Arrays.asList(new Software(), new Software());

        // Mock service method behavior
        when(regularUser.searchSoftwareBySupportEndDate(supportEndDate)).thenReturn(new ResponseEntity<>(softwareList, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareBySupportEndDate(supportEndDate);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    @Test
    public void testSearchSoftwareBySupportEndDate_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        LocalDate supportEndDate = LocalDate.of(2022, 12, 31);

        // Mock service method behavior
        when(regularUser.searchSoftwareBySupportEndDate(supportEndDate)).thenThrow(new SoftwareNotFoundException("Software not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareBySupportEndDate(supportEndDate);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }
    
    @Test
    public void testSearchSoftwareByStatus_Success() throws SoftwareNotFoundException {
        // Mock data
        String status = "Active";
        List<Software> softwareList = Arrays.asList(new Software(), new Software());

        // Mock service method behavior
        when(regularUser.searchSoftwareByStatus(status)).thenReturn(new ResponseEntity<>(softwareList, HttpStatus.OK));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByStatus(status);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    @Test
    public void testSearchSoftwareByStatus_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        String status = "NonExistentStatus";

        // Mock service method behavior
        when(regularUser.searchSoftwareByStatus(status)).thenThrow(new SoftwareNotFoundException("Software not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByStatus(status);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }

    @Test
    public void testSearchSoftwareByLicenseKey_Success() throws SoftwareNotFoundException {
        // Mock data
        String licenseKey = "ABCD1234";
        List<Software> softwareList = Arrays.asList(new Software(), new Software());
        ResponseEntity<List<Software>> responseEntity = new ResponseEntity<>(softwareList, HttpStatus.OK);

        // Mock service method behavior
        when(regularUser.searchSoftwareByLicenseKey(licenseKey)).thenReturn(responseEntity);

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByLicenseKey(licenseKey);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    @Test
    public void testSearchSoftwareByLicenseKey_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        String licenseKey = "NonExistentLicenseKey";

        // Mock service method behavior
        when(regularUser.searchSoftwareByLicenseKey(licenseKey)).thenThrow(new SoftwareNotFoundException("Software not found"));

        // Perform the test
        ResponseEntity<?> response = regularUserController.searchSoftwareByLicenseKey(licenseKey);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }

    // Add more test methods for other controller methods
}
