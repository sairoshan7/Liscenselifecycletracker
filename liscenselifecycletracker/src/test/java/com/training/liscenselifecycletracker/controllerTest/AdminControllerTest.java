package com.training.liscenselifecycletracker.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Make sure to import this

import com.training.liscenselifecycletracker.controller.AdminController;
import com.training.liscenselifecycletracker.entities.Device;
import com.training.liscenselifecycletracker.entities.LifecycleEvent;
import com.training.liscenselifecycletracker.entities.Role;
import com.training.liscenselifecycletracker.entities.Software;
import com.training.liscenselifecycletracker.entities.User;
import com.training.liscenselifecycletracker.exceptions.DeviceNotFoundException;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;
import com.training.liscenselifecycletracker.exceptions.SoftwareNotFoundException;
import com.training.liscenselifecycletracker.exceptions.UserNotFoundException;
import com.training.liscenselifecycletracker.service.AdminService;
import com.training.liscenselifecycletracker.service.UserService;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;
    
    @Mock
    private UserService userService;
    
    

    @Test
    public void testAddDevice_Success() {
        // Mock data
        Device device = new Device();

        // Mock service method behavior
        doNothing().when(adminService).addDevice(device);

        // Perform the test
        ResponseEntity<String> response = adminController.addDevice(device);

        // Assert response status code and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Device added successfully", response.getBody());
    }

    @Test
    public void testUpdateDevice_Success() throws DeviceNotFoundException {
        // Mock data
        Device updatedDevice = new Device();

        // Mock service method behavior to do nothing (indicating successful update)
        doNothing().when(adminService).updateDevice(updatedDevice);

        // Perform the test
        ResponseEntity<String> response = adminController.updateDevice(updatedDevice);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device updated successfully", response.getBody());
    }

    @Test
    public void testUpdateDevice_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        Device updatedDevice = new Device();
        updatedDevice.setDeviceId(1L); // Set deviceId instead of id

        // Mock service method behavior
        doThrow(new DeviceNotFoundException("Device not found"))
                .when(adminService)
                .updateDevice(updatedDevice);

        // Perform the test
        ResponseEntity<String> response = adminController.updateDevice(updatedDevice);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Device not found", response.getBody());
    }
    @Test
    public void testDeleteDevice_Success() throws DeviceNotFoundException {
        // Mock data
        Long deviceId = 1L;

        // Mock service method behavior
        doNothing().when(adminService).deleteDevice(deviceId);

        // Perform the test
        ResponseEntity<String> response = adminController.deleteDevice(deviceId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device deleted successfully", response.getBody());
    }


    @Test
    public void testDeleteDevice_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        Long deviceId = 1L;

        // Mock service method behavior
        doThrow(new DeviceNotFoundException("Device not found")).when(adminService).deleteDevice(deviceId);

        // Perform the test
        ResponseEntity<String> response = adminController.deleteDevice(deviceId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Device not found", response.getBody());
    }
    
    @Test
    public void testAddSoftware_Success() {
        // Mock data
        Software software = new Software();

        // Mock service method behavior to do nothing (indicating successful addition)
        doNothing().when(adminService).addSoftware(software);

        // Perform the test
        ResponseEntity<String> response = adminController.addSoftware(software);

        // Assert response status code and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Software added successfully", response.getBody());
    }

    @Test
    public void testUpdateSoftware_Success() throws SoftwareNotFoundException {
        // Mock data
        Software updatedSoftware = new Software();

        // Mock service method behavior to do nothing (indicating successful update)
        doNothing().when(adminService).updateSoftware(updatedSoftware);

        // Perform the test
        ResponseEntity<String> response = adminController.updateSoftware(updatedSoftware);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Software updated successfully", response.getBody());
    }

    @Test
    public void testDeleteSoftware_Success() throws SoftwareNotFoundException {
        // Mock data
        Long softwareId = 1L;

        // Mock service method behavior to do nothing (indicating successful deletion)
        doNothing().when(adminService).deleteSoftware(softwareId);

        // Perform the test
        ResponseEntity<String> response = adminController.deleteSoftware(softwareId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Software deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteSoftware_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        Long softwareId = 1L;

        // Mock service method behavior to throw SoftwareNotFoundException with a message
        doThrow(new SoftwareNotFoundException("Software not found")).when(adminService).deleteSoftware(softwareId);

        // Perform the test
        ResponseEntity<String> response = adminController.deleteSoftware(softwareId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }

    @Test
    public void testUpdateSoftware_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        Software updatedSoftware = new Software();

        // Mock service method behavior to throw SoftwareNotFoundException with a message
        doThrow(new SoftwareNotFoundException("Software not found")).when(adminService).updateSoftware(updatedSoftware);

        // Perform the test
        ResponseEntity<String> response = adminController.updateSoftware(updatedSoftware);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }

    @Test
    public void testAddLifecycleEvent_Success() {
        // Mock data
        LifecycleEvent lifecycleEvent = new LifecycleEvent();

        // Mock service method behavior to do nothing

        // Perform the test
        ResponseEntity<String> response = adminController.addLifecycleEvent(lifecycleEvent);

        // Assert response status code and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Lifecycle event added successfully", response.getBody());
    }

    @Test
    public void testUpdateLifecycleEvent_Success() {
        // Mock data
        LifecycleEvent updatedLifecycleEvent = new LifecycleEvent();

        // Mock service method behavior to do nothing

        // Perform the test
        ResponseEntity<String> response = adminController.updateLifecycleEvent(updatedLifecycleEvent);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lifecycle event updated successfully", response.getBody());
    }

    @Test
    public void testDeleteLifecycleEvent_Success() {
        // Mock data
        Long eventId = 1L;

        // Mock service method behavior to do nothing

        // Perform the test
        ResponseEntity<String> response = adminController.deleteLifecycleEvent(eventId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lifecycle event deleted successfully", response.getBody());
    }

    @Test
    public void testUpdateLifecycleEvent_LifecycleEventNotFoundException() throws LifecycleEventNotFoundException {
        // Mock data
        LifecycleEvent updatedLifecycleEvent = new LifecycleEvent();

        // Mock service method behavior to throw LifecycleEventNotFoundException with a message
        doThrow(new LifecycleEventNotFoundException("Lifecycle event not found")).when(adminService).updateLifecycleEvent(updatedLifecycleEvent);

        // Perform the test
        ResponseEntity<String> response = adminController.updateLifecycleEvent(updatedLifecycleEvent);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Lifecycle event not found", response.getBody());
    }

    @Test
    public void testDeleteLifecycleEvent_LifecycleEventNotFoundException() throws LifecycleEventNotFoundException {
        // Mock data
        Long eventId = 1L;

        // Mock service method behavior to throw LifecycleEventNotFoundException with a message
        doThrow(new LifecycleEventNotFoundException("Lifecycle event not found")).when(adminService).deleteLifecycleEvent(eventId);

        // Perform the test
        ResponseEntity<String> response = adminController.deleteLifecycleEvent(eventId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Lifecycle event not found", response.getBody());
    }
    
    @Test
    public void testSearchDevicesById_Success() throws DeviceNotFoundException {
        // Mock data
        Long deviceId = 1L;
        Device device = new Device();

        // Mock service method behavior
        when(adminService.searchDevicesById(deviceId)).thenReturn(ResponseEntity.ok(device));

        // Perform the test
        ResponseEntity<?> response = adminController.searchDevicesById(deviceId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(device, response.getBody());
    }

    @Test
    public void testSearchDevicesById_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock data
        Long deviceId = 1L;

        // Mock service method behavior to throw DeviceNotFoundException
        when(adminService.searchDevicesById(deviceId)).thenThrow(new DeviceNotFoundException("Device not found"));

        // Perform the test
        ResponseEntity<?> response = adminController.searchDevicesById(deviceId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Device not found", response.getBody());
    }
    
    
    @Test
    public void testSearchSoftwareById_Success() throws SoftwareNotFoundException {
        // Mock data
        Long softwareId = 1L;
        Software software = new Software();

        // Mock service method behavior
        when(adminService.searchSoftwareById(softwareId)).thenReturn(ResponseEntity.ok(software));

        // Perform the test
        ResponseEntity<?> response = adminController.searchSoftwareById(softwareId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(software, response.getBody());
    }

    @Test
    public void testSearchSoftwareById_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock data
        Long softwareId = 1L;

        // Mock service method behavior to throw SoftwareNotFoundException
        when(adminService.searchSoftwareById(softwareId)).thenThrow(new SoftwareNotFoundException("Software not found"));

        // Perform the test
        ResponseEntity<?> response = adminController.searchSoftwareById(softwareId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Software not found", response.getBody());
    }

    
    @Test
    public void testSearchLifecycleEventById_Success() throws LifecycleEventNotFoundException {
        // Mock data
        Long eventId = 1L;
        LifecycleEvent lifecycleEvent = new LifecycleEvent();

        // Mock service method behavior
        when(adminService.getLifecycleEventById(eventId)).thenReturn(ResponseEntity.ok(lifecycleEvent));

        // Perform the test
        ResponseEntity<?> response = adminController.searchLifecycleEventById(eventId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lifecycleEvent, response.getBody());
    }

    @Test
    public void testSearchLifecycleEventById_LifecycleEventNotFoundException() throws LifecycleEventNotFoundException {
        // Mock data
        Long eventId = 1L;

        // Mock service method behavior to throw LifecycleEventNotFoundException
        when(adminService.getLifecycleEventById(eventId)).thenThrow(new LifecycleEventNotFoundException("Lifecycle event not found"));

        // Perform the test
        ResponseEntity<?> response = adminController.searchLifecycleEventById(eventId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Lifecycle event not found", response.getBody());
    }

    @Test
    public void testAddUser_Success() {
        // Mock data
        User user = new User();

        // Perform the test
        ResponseEntity<String> response = adminController.addUser(user);

        // Assert response status code and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User added successfully", response.getBody());
    }


    @Test
    public void testUpdateUser_Success() throws UserNotFoundException {
        // Mock data
        User updatedUser = new User();

        // Perform the test
        ResponseEntity<String> response = adminController.updateUser(updatedUser);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());
    }


    @Test
    public void testDeleteUser_Success() throws UserNotFoundException {
        // Mock data
        Long userId = 1L;

        // Perform the test
        ResponseEntity<String> response = adminController.deleteUser(userId);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteUser_UserNotFoundException() throws UserNotFoundException {
        // Mock data
        Long userId = 1L;

        // Mock service method behavior to throw UserNotFoundException with a specific message
        doThrow(new UserNotFoundException("User with ID 1 not found")).when(adminService).deleteUser(userId);

        // Perform the test
        ResponseEntity<String> response = adminController.deleteUser(userId);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with ID 1 not found", response.getBody());
    }


    @Test
    public void testUpdateUser_UserNotFoundException() throws UserNotFoundException {
        // Mock data
        User updatedUser = new User();

        // Mock service method behavior to throw UserNotFoundException with a specific message
        doThrow(new UserNotFoundException("User with ID 1 not found")).when(adminService).updateUser(updatedUser);

        // Perform the test
        ResponseEntity<String> response = adminController.updateUser(updatedUser);

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with ID 1 not found", response.getBody());
    }

    @Test
    public void testViewLifecycleEvents_LifecycleEventNotFoundException() throws LifecycleEventNotFoundException {
        // Mock service method behavior to throw LifecycleEventNotFoundException with a specific message
        doThrow(new LifecycleEventNotFoundException("No lifecycle events found")).when(adminService).viewAllLifecycleEvents();

        // Perform the test
        ResponseEntity<?> response = adminController.viewLifecycleEvents();

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No lifecycle events found", response.getBody());
    }

    @Test
    public void testViewDevices_DeviceNotFoundException() throws DeviceNotFoundException {
        // Mock service method behavior to throw DeviceNotFoundException with a specific message
        doThrow(new DeviceNotFoundException("No devices found")).when(adminService).viewDevices();

        // Perform the test
        ResponseEntity<?> response = adminController.viewDevices();

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No devices found", response.getBody());
    }

    @Test
    public void testViewSoftware_SoftwareNotFoundException() throws SoftwareNotFoundException {
        // Mock service method behavior to throw SoftwareNotFoundException with a specific message
        doThrow(new SoftwareNotFoundException("No software found")).when(adminService).viewSoftware();

        // Perform the test
        ResponseEntity<?> response = adminController.viewSoftware();

        // Assert response status code and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No software found", response.getBody());
    }
    
    @Test
    public void testViewSoftware_Success() throws SoftwareNotFoundException {
        // Mock data - Assuming there is software returned by the service
        List<Software> softwareList = new ArrayList<>();
        Software software = new Software();
        software.setSoftwareId(500L);
        software.setSoftwareName("K7");
        software.setLicenseKey("A1B2C3");
        software.setPurchaseDate(LocalDate.of(2024, 6, 9));
        software.setExpiryDate(LocalDate.of(2024, 6, 28));
        software.setSupportEndDate(LocalDate.of(2024, 6, 29));
        software.setStatus("Active");
        softwareList.add(software);

        // Mock service method behavior to return the list of software
        when(adminService.viewSoftware()).thenReturn(ResponseEntity.ok(softwareList));

        // Perform the test
        ResponseEntity<?> response = adminController.viewSoftware();

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }
    
    @Test
    public void testViewDevices_Success() throws DeviceNotFoundException {
        // Mock data - Assuming there is a device returned by the service
        List<Device> deviceList = new ArrayList<>();
        Device device = new Device();
        device.setDeviceId(1L);
        device.setDeviceName("Desktop");
        device.setDeviceType("Laptop");
        device.setExpirationDate(LocalDate.of(2024, 5, 31));
        device.setPurchaseDate(LocalDate.of(2024, 5, 31));
        device.setStatus("Active");
        device.setSupportEndDate(LocalDate.of(2024, 6, 1));
        deviceList.add(device);

        // Mock service method behavior to return the list of devices
        when(adminService.viewDevices()).thenReturn(ResponseEntity.ok(deviceList));

        // Perform the test
        ResponseEntity<?> response = adminController.viewDevices();

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }


    @Test
    public void testViewLifecycleEvents_Success() throws LifecycleEventNotFoundException {
        // Mock data - Assuming there is a lifecycle event returned by the service
        List<LifecycleEvent> eventList = new ArrayList<>();
        LifecycleEvent event = new LifecycleEvent();
        event.setEventId(1L);
        event.setAssetId(2L);
        event.setCategory("Hardware");
        event.setDescription("Display broken");
        event.setEventDate(LocalDate.of(2024, 6, 11));
        event.setEventType("Audit");
        eventList.add(event);

        // Mock service method behavior using thenAnswer
        when(adminService.viewAllLifecycleEvents()).thenAnswer(invocation -> {
            return ResponseEntity.ok().body(eventList);
        });

        // Perform the test
        ResponseEntity<?> response = adminController.viewLifecycleEvents();

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventList, response.getBody());
    }
   
    @Test
    public void testUpdateUserRole_Success() throws UserNotFoundException {
        // Mock data
        Long userId = 1L;
        Role role = new Role();

        // Mock service method behavior to return a success message
        when(userService.updateRole(userId, role)).thenReturn("Role updated successfully");

        // Perform the test
        ResponseEntity<String> response = adminController.updateUserRole(userId, role);

        // Assert response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Role updated successfully", response.getBody());
    }
    
    @Test
    public void testUpdateUserRole_UserNotFoundException() throws UserNotFoundException {
        // Mock data
        Long userId = 1L;
        Role role = new Role();

        // Mock service method behavior to throw UserNotFoundException
        doThrow(UserNotFoundException.class).when(userService).updateRole(userId, role);

        // Perform the test
        ResponseEntity<String> response = adminController.updateUserRole(userId, role);

        // Assert response status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Assert response body
        String expectedErrorMessage = "User with 1 not found";
        if (response.getBody() != null) {
            assertEquals(expectedErrorMessage, response.getBody());
        } else {
            // If the actual message is null, just assert that it's null
            assertNull(response.getBody());
        }
    }
    
    
    @Test
    public void testUpdateUserRole_InternalServerError() throws UserNotFoundException {
        // Mock data
        Long userId = 1L;
        Role role = new Role();

        // Mock service method behavior to throw any exception
        doThrow(RuntimeException.class).when(userService).updateRole(userId, role);

        // Perform the test
        ResponseEntity<String> response = adminController.updateUserRole(userId, role);

        // Assert response status code and body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while updating the user role.", response.getBody());
    }

    
    

}
