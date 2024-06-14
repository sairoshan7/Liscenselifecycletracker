package com.training.liscenselifecycletracker.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.training.liscenselifecycletracker.entities.LifecycleEvent;
import com.training.liscenselifecycletracker.entities.Software;
import com.training.liscenselifecycletracker.entities.User;
import com.training.liscenselifecycletracker.exceptions.DeviceNotFoundException;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;
import com.training.liscenselifecycletracker.exceptions.SoftwareNotFoundException;
import com.training.liscenselifecycletracker.exceptions.UserNotFoundException;
import com.training.liscenselifecycletracker.repositories.DeviceRepository;
import com.training.liscenselifecycletracker.repositories.LifecycleEventRepository;
import com.training.liscenselifecycletracker.repositories.SoftwareRepository;
import com.training.liscenselifecycletracker.repositories.UserRepository;
import com.training.liscenselifecycletracker.service.AdminServiceImpl;

public class AdminServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private SoftwareRepository softwareRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LifecycleEventRepository lifecycleEventRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for addDevice method
    @Test
    public void testAddDevice() {
        Device device = new Device();
        adminService.addDevice(device);
        verify(deviceRepository, times(1)).save(device);
    }

    // Test for updateDevice method when device exists
    @Test
    public void testUpdateDevice_DeviceExists() throws DeviceNotFoundException {
        Device updatedDevice = new Device();
        updatedDevice.setDeviceId(1L);

        when(deviceRepository.findById(updatedDevice.getDeviceId())).thenReturn(Optional.of(updatedDevice));

        adminService.updateDevice(updatedDevice);

        verify(deviceRepository, times(1)).save(updatedDevice);
    }

    // Test for updateDevice method when device does not exist
    @Test
    public void testUpdateDevice_DeviceNotFound() {
        Device updatedDevice = new Device();
        updatedDevice.setDeviceId(1L);

        when(deviceRepository.findById(updatedDevice.getDeviceId())).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            adminService.updateDevice(updatedDevice);
        });
    }

    // Test for deleteDevice method when device exists
    @Test
    public void testDeleteDevice_DeviceExists() throws DeviceNotFoundException {
        Long deviceId = 1L;
        Device device = new Device();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        adminService.deleteDevice(deviceId);

        verify(deviceRepository, times(1)).deleteById(deviceId);
    }

    // Test for deleteDevice method when device does not exist
    @Test
    public void testDeleteDevice_DeviceNotFound() {
        Long deviceId = 1L;

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            adminService.deleteDevice(deviceId);
        });
    }

    // Add similar test methods for addSoftware, updateSoftware, deleteSoftware,
    // addUser, updateUser, deleteUser, addLifecycleEvent, updateLifecycleEvent,
    // deleteLifecycleEvent, viewDevices, searchDevicesById, viewSoftware,
    // searchSoftwareById, viewAllLifecycleEvents, and getLifecycleEventById
 // Test for addSoftware method
    @Test
    public void testAddSoftware() {
        Software software = new Software();
        adminService.addSoftware(software);
        verify(softwareRepository, times(1)).save(software);
    }

    // Test for updateSoftware method when software exists
    @Test
    public void testUpdateSoftware_SoftwareExists() throws SoftwareNotFoundException {
        Software updatedSoftware = new Software();
        updatedSoftware.setSoftwareId(1L);

        when(softwareRepository.findById(updatedSoftware.getSoftwareId())).thenReturn(Optional.of(updatedSoftware));

        adminService.updateSoftware(updatedSoftware);

        verify(softwareRepository, times(1)).save(updatedSoftware);
    }

    // Test for updateSoftware method when software does not exist
    @Test
    public void testUpdateSoftware_SoftwareNotFound() {
        Software updatedSoftware = new Software();
        updatedSoftware.setSoftwareId(1L);

        when(softwareRepository.findById(updatedSoftware.getSoftwareId())).thenReturn(Optional.empty());

        assertThrows(SoftwareNotFoundException.class, () -> {
            adminService.updateSoftware(updatedSoftware);
        });
    }

    // Test for deleteSoftware method when software exists
    @Test
    public void testDeleteSoftware_SoftwareExists() throws SoftwareNotFoundException {
        Long softwareId = 1L;
        Software software = new Software();

        when(softwareRepository.findById(softwareId)).thenReturn(Optional.of(software));

        adminService.deleteSoftware(softwareId);

        verify(softwareRepository, times(1)).deleteById(softwareId);
    }

    // Test for deleteSoftware method when software does not exist
    @Test
    public void testDeleteSoftware_SoftwareNotFound() {
        Long softwareId = 1L;

        when(softwareRepository.findById(softwareId)).thenReturn(Optional.empty());

        assertThrows(SoftwareNotFoundException.class, () -> {
            adminService.deleteSoftware(softwareId);
        });
    }
    
 // Test for addUser method
    @Test
    public void testAddUser() {
        User user = new User();
        adminService.addUser(user);
        verify(userRepository, times(1)).save(user);
    }

    // Test for updateUser method when user exists
    @Test
    public void testUpdateUser_UserExists() throws UserNotFoundException {
        User updatedUser = new User();
        updatedUser.setUserId(1L);

        when(userRepository.findById(updatedUser.getUserId())).thenReturn(Optional.of(updatedUser));

        adminService.updateUser(updatedUser);

        verify(userRepository, times(1)).save(updatedUser);
    }

    // Test for updateUser method when user does not exist
    @Test
    public void testUpdateUser_UserNotFound() {
        User updatedUser = new User();
        updatedUser.setUserId(1L);

        when(userRepository.findById(updatedUser.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            adminService.updateUser(updatedUser);
        });
    }

    // Test for deleteUser method when user exists
    @Test
    public void testDeleteUser_UserExists() throws UserNotFoundException {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    // Test for deleteUser method when user does not exist
    @Test
    public void testDeleteUser_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            adminService.deleteUser(userId);
        });
    }

    // Test for addLifecycleEvent method
    @Test
    public void testAddLifecycleEvent() {
        LifecycleEvent lifecycleEvent = new LifecycleEvent();
        adminService.addLifecycleEvent(lifecycleEvent);
        verify(lifecycleEventRepository, times(1)).save(lifecycleEvent);
    }

    // Test for updateLifecycleEvent method when lifecycle event exists
    @Test
    public void testUpdateLifecycleEvent_EventExists() throws LifecycleEventNotFoundException {
        LifecycleEvent updatedLifecycleEvent = new LifecycleEvent();
        updatedLifecycleEvent.setEventId(1L);

        when(lifecycleEventRepository.findById(updatedLifecycleEvent.getEventId())).thenReturn(Optional.of(updatedLifecycleEvent));

        adminService.updateLifecycleEvent(updatedLifecycleEvent);

        verify(lifecycleEventRepository, times(1)).save(updatedLifecycleEvent);
    }

    // Test for updateLifecycleEvent method when lifecycle event does not exist
    @Test
    public void testUpdateLifecycleEvent_EventNotFound() {
        LifecycleEvent updatedLifecycleEvent = new LifecycleEvent();
        updatedLifecycleEvent.setEventId(1L);

        when(lifecycleEventRepository.findById(updatedLifecycleEvent.getEventId())).thenReturn(Optional.empty());

        assertThrows(LifecycleEventNotFoundException.class, () -> {
            adminService.updateLifecycleEvent(updatedLifecycleEvent);
        });
    }

    // Test for deleteLifecycleEvent method when lifecycle event exists
    @Test
    public void testDeleteLifecycleEvent_EventExists() throws LifecycleEventNotFoundException {
        Long eventId = 1L;
        LifecycleEvent lifecycleEvent = new LifecycleEvent();

        when(lifecycleEventRepository.findById(eventId)).thenReturn(Optional.of(lifecycleEvent));

        adminService.deleteLifecycleEvent(eventId);

        verify(lifecycleEventRepository, times(1)).delete(lifecycleEvent);
    }

    // Test for deleteLifecycleEvent method when lifecycle event does not exist
    @Test
    public void testDeleteLifecycleEvent_EventNotFound() {
        Long eventId = 1L;

        when(lifecycleEventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(LifecycleEventNotFoundException.class, () -> {
            adminService.deleteLifecycleEvent(eventId);
        });
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

        ResponseEntity<List<Device>> response = adminService.viewDevices();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList, response.getBody());
    }

    // Test for viewDevices method when no devices exist
    @Test
    public void testViewDevices_NoDevicesFound() {
        when(deviceRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> {
            adminService.viewDevices();
        });
    }

    // Test for searchDevicesById method when device exists
    @Test
    public void testSearchDevicesById_DeviceExists() throws DeviceNotFoundException {
        Long deviceId = 1L;
        Device device = new Device();
        device.setDeviceId(deviceId);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        ResponseEntity<Device> response = adminService.searchDevicesById(deviceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(device, response.getBody());
    }

    // Test for searchDevicesById method when device does not exist
    @Test
    public void testSearchDevicesById_DeviceNotFound() {
        Long deviceId = 1L;

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            adminService.searchDevicesById(deviceId);
        });
    }

    // Test for viewSoftware method when software exists
    @Test
    public void testViewSoftware_SoftwareExist() throws SoftwareNotFoundException {
        List<Software> softwareList = new ArrayList<>();
        Software software1 = new Software();
        software1.setSoftwareId(1L);
        Software software2 = new Software();
        software2.setSoftwareId(2L);
        softwareList.add(software1);
        softwareList.add(software2);

        when(softwareRepository.findAll()).thenReturn(softwareList);

        ResponseEntity<List<Software>> response = adminService.viewSoftware();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(softwareList, response.getBody());
    }

    // Test for viewSoftware method when no software exists
    @Test
    public void testViewSoftware_NoSoftwareFound() {
        when(softwareRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(SoftwareNotFoundException.class, () -> {
            adminService.viewSoftware();
        });
    }

    // Test for searchSoftwareById method when software exists
    @Test
    public void testSearchSoftwareById_SoftwareExists() throws SoftwareNotFoundException {
        Long softwareId = 1L;
        Software software = new Software();
        software.setSoftwareId(softwareId);

        when(softwareRepository.findById(softwareId)).thenReturn(Optional.of(software));

        ResponseEntity<Software> response = adminService.searchSoftwareById(softwareId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(software, response.getBody());
    }

    // Test for searchSoftwareById method when software does not exist
    @Test
    public void testSearchSoftwareById_SoftwareNotFound() {
        Long softwareId = 1L;

        when(softwareRepository.findById(softwareId)).thenReturn(Optional.empty());

        assertThrows(SoftwareNotFoundException.class, () -> {
            adminService.searchSoftwareById(softwareId);
        });
    }

    // Test for viewAllLifecycleEvents method when lifecycle events exist
    @Test
    public void testViewAllLifecycleEvents_EventsExist() throws LifecycleEventNotFoundException {
        List<LifecycleEvent> eventList = new ArrayList<>();
        LifecycleEvent event1 = new LifecycleEvent();
        event1.setEventId(1L);
        LifecycleEvent event2 = new LifecycleEvent();
        event2.setEventId(2L);
        eventList.add(event1);
        eventList.add(event2);

        when(lifecycleEventRepository.findAll()).thenReturn(eventList);

        ResponseEntity<?> response = adminService.viewAllLifecycleEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventList, response.getBody());
    }

    // Test for viewAllLifecycleEvents method when no lifecycle events exist
    @Test
    public void testViewAllLifecycleEvents_NoEventsFound() {
        when(lifecycleEventRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(LifecycleEventNotFoundException.class, () -> {
            adminService.viewAllLifecycleEvents();
        });
    }

    // Test for getLifecycleEventById method when lifecycle event exists
    @Test
    public void testGetLifecycleEventById_EventExists() throws LifecycleEventNotFoundException {
        Long eventId = 1L;
        LifecycleEvent event = new LifecycleEvent();
        event.setEventId(eventId);

        when(lifecycleEventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ResponseEntity<LifecycleEvent> response = adminService.getLifecycleEventById(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(event, response.getBody());
    }

    // Test for getLifecycleEventById method when lifecycle event does not exist
    @Test
    public void testGetLifecycleEventById_EventNotFound() {
        Long eventId = 1L;

        when(lifecycleEventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(LifecycleEventNotFoundException.class, () -> {
            adminService.getLifecycleEventById(eventId);
        });
    }


}
