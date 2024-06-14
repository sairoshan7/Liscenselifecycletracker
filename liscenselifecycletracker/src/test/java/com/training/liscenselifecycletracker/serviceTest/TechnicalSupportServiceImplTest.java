package com.training.liscenselifecycletracker.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.training.liscenselifecycletracker.entities.Device;
import com.training.liscenselifecycletracker.entities.LifecycleEvent;
import com.training.liscenselifecycletracker.entities.Software;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;
import com.training.liscenselifecycletracker.repositories.DeviceRepository;
import com.training.liscenselifecycletracker.repositories.LifecycleEventRepository;
import com.training.liscenselifecycletracker.repositories.SoftwareRepository;
import com.training.liscenselifecycletracker.service.TechnicalSupportServiceImpl;

public class TechnicalSupportServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private SoftwareRepository softwareRepository;

    @Mock
    private LifecycleEventRepository lifecycleEventRepository;

    @InjectMocks
    private TechnicalSupportServiceImpl technicalSupportService;
    
    @Captor
    private ArgumentCaptor<LifecycleEvent> lifecycleEventCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogFault() {
        Long deviceId = 1L;
        String description = "Device fault description";
        String date = "2024-06-12";
        String category = "Hardware";

        Device device = new Device();
        device.setDeviceId(deviceId);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        // Mock the behavior of lifecycleEventRepository.findByAssetId to return a valid LifecycleEvent
        LifecycleEvent faultEvent = new LifecycleEvent(); // Create a dummy LifecycleEvent
        when(lifecycleEventRepository.findByAssetId(deviceId)).thenReturn(faultEvent);

        // Call the method under test
        try {
            technicalSupportService.logFault(deviceId, description, date, category);
        } catch (IllegalArgumentException | LifecycleEventNotFoundException e) {
            e.printStackTrace();
        }

        // Verify that the save method was called with the expected argument
        verify(lifecycleEventRepository, times(1)).save(any(LifecycleEvent.class));
    }


    @Test
    public void testLogFault_DeviceNotFound() {
        Long deviceId = 1L;
        String description = "Device fault description";
        String date = "2024-06-12";
        String category = "Hardware";

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            technicalSupportService.logFault(deviceId, description, date, category);
        });
    }

    @Test
    public void testUpdateFaultLog() {
        Long deviceId = 1L;
        String repairDetails = "Repair details";
        String category = "Hardware";
        String eventType = "EventType";

        Device device = new Device();
        device.setDeviceId(deviceId);

        LifecycleEvent existingEvent = new LifecycleEvent();
        existingEvent.setAssetId(deviceId);
        existingEvent.setEventType(eventType);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(lifecycleEventRepository.findByAssetIdAndEventType(deviceId, eventType)).thenReturn(Optional.of(existingEvent));

        technicalSupportService.updateFaultLog(deviceId, repairDetails, category, eventType);

        verify(lifecycleEventRepository, times(1)).save(any(LifecycleEvent.class));
    }

    @Test
    public void testUpdateFaultLog_DeviceNotFound() {
        Long deviceId = 1L;
        String repairDetails = "Repair details";
        String category = "Hardware";
        String eventType = "EventType";

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            technicalSupportService.updateFaultLog(deviceId, repairDetails, category, eventType);
        });
    }

    @Test
    public void testUpdateFaultLog_NoExistingFaultLog() {
        Long deviceId = 1L;
        String repairDetails = "Repair details";
        String category = "Hardware";
        String eventType = "EventType";

        Device device = new Device();
        device.setDeviceId(deviceId);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(lifecycleEventRepository.findByAssetIdAndEventType(deviceId, eventType)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            technicalSupportService.updateFaultLog(deviceId, repairDetails, category, eventType);
        });
    }

    @Test
    public void testViewEndOfSupportDates() {
        List<Device> deviceList = new ArrayList<>();
        List<Software> softwareList = new ArrayList<>();

        Device device1 = new Device();
        device1.setDeviceId(1L);
        device1.setDeviceName("Device1");
        device1.setSupportEndDate(LocalDate.now());

        Device device2 = new Device();
        device2.setDeviceId(2L);
        device2.setDeviceName("Device2");
        device2.setSupportEndDate(LocalDate.now().plusDays(1));

        deviceList.add(device1);
        deviceList.add(device2);

        Software software1 = new Software();
        software1.setSoftwareId(1L);
        software1.setSoftwareName("Software1");
        software1.setSupportEndDate(LocalDate.now());

        Software software2 = new Software();
        software2.setSoftwareId(2L);
        software2.setSoftwareName("Software2");
        software2.setSupportEndDate(LocalDate.now().plusDays(1));

        softwareList.add(software1);
        softwareList.add(software2);

        when(deviceRepository.findAll()).thenReturn(deviceList);
        when(softwareRepository.findAll()).thenReturn(softwareList);

        List<String> supportDates = technicalSupportService.viewEndOfSupportDates();

        assertEquals(4, supportDates.size());
    }

    @Test
    public void testViewEndOfSupportDates_NoDevicesOrSoftwareFound() {
        when(deviceRepository.findAll()).thenReturn(Collections.emptyList());
        when(softwareRepository.findAll()).thenReturn(Collections.emptyList());

        List<String> supportDates = technicalSupportService.viewEndOfSupportDates();

        assertEquals(0, supportDates.size());
    }
    
    @Test
    public void testLogFault_LifecycleEventNotFound() {
        Long deviceId = 1L;
        String description = "Device fault description";
        String date = "2024-06-12";
        String category = "Hardware";

        Device device = new Device();
        device.setDeviceId(deviceId);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(lifecycleEventRepository.findByAssetId(deviceId)).thenReturn(null);

        assertThrows(LifecycleEventNotFoundException.class, () -> {
            technicalSupportService.logFault(deviceId, description, date, category);
        });
    }
}
