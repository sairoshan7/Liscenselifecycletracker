package com.training.liscenselifecycletracker.controllerTest;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.training.liscenselifecycletracker.DTO.LogFaultRequestDTO;
import com.training.liscenselifecycletracker.DTO.UpdateFaultLogRequestDTO;
import com.training.liscenselifecycletracker.controller.TechnicalSupportController;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;
import com.training.liscenselifecycletracker.service.TechnicalSupportService;
 
@SpringBootTest
public class TechnicalSupportControllerTest {
 
    @Mock
    private TechnicalSupportService technicalSupportService;
 
    @InjectMocks
    private TechnicalSupportController controller;
 
    @Test
    public void testLogFault_Success() throws IllegalArgumentException, LifecycleEventNotFoundException {
        // Prepare request DTO
        LogFaultRequestDTO requestDTO = new LogFaultRequestDTO();
        requestDTO.setDeviceId(1L);
        requestDTO.setDescription("Description");
        requestDTO.setDate("2024-06-15");
        requestDTO.setCategory("Category");
 
        // Mock service method to indicate successful fault logging
        doNothing().when(technicalSupportService).logFault(1L, "Description", "2024-06-15", "Category");
 
        // Call controller method
        ResponseEntity<String> response = controller.logFault(requestDTO);
 
        // Assert that the response is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fault logged successfully.", response.getBody());
    }
 
    @Test
    public void testLogFault_Error() throws IllegalArgumentException, LifecycleEventNotFoundException {
        // Prepare request DTO
        LogFaultRequestDTO requestDTO = new LogFaultRequestDTO();
        requestDTO.setDeviceId(1L);
        requestDTO.setDescription("Description");
        requestDTO.setDate("2024-06-15");
        requestDTO.setCategory("Category");
 
        // Mock service method to indicate an error occurred
        doThrow(new RuntimeException("Error")).when(technicalSupportService).logFault(1L, "Description", "2024-06-15", "Category");
 
        // Call controller method
        ResponseEntity<String> response = controller.logFault(requestDTO);
 
        // Assert that the response indicates an internal server error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error logging fault"));
    }
 
    @Test
    public void testUpdateFaultLog_Success() {
        // Prepare request DTO
        UpdateFaultLogRequestDTO requestDTO = new UpdateFaultLogRequestDTO();
        requestDTO.setDeviceId(1L);
        requestDTO.setRepairDetails("Repair details");
        requestDTO.setCategory("Category");
        requestDTO.setEventType("EventType");
 
        // Mock service method to indicate successful fault log update
        doNothing().when(technicalSupportService).updateFaultLog(1L, "Repair details", "Category", "EventType");
 
        // Call controller method
        ResponseEntity<String> response = controller.updateFaultLog(requestDTO);
 
        // Assert that the response is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fault log updated successfully.", response.getBody());
    }
 
    @Test
    public void testUpdateFaultLog_Error() {
        // Prepare request DTO
        UpdateFaultLogRequestDTO requestDTO = new UpdateFaultLogRequestDTO();
        requestDTO.setDeviceId(1L);
        requestDTO.setRepairDetails("Repair details");
        requestDTO.setCategory("Category");
        requestDTO.setEventType("EventType");
 
        // Mock service method to indicate an error occurred
        doThrow(new RuntimeException("Error")).when(technicalSupportService).updateFaultLog(1L, "Repair details", "Category", "EventType");
 
        // Call controller method
        ResponseEntity<String> response = controller.updateFaultLog(requestDTO);
 
        // Assert that the response indicates an internal server error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error updating fault log"));
    }
 
    @Test
    public void testViewEndOfSupportDates_Success() {
        // Prepare mock support dates
        List<String> supportDates = new ArrayList<>();
        supportDates.add("2024-06-15");
        supportDates.add("2024-06-16");
 
        // Mock service method to return support dates
        when(technicalSupportService.viewEndOfSupportDates()).thenReturn(supportDates);
 
        // Call controller method
        ResponseEntity<List<String>> response = controller.viewEndOfSupportDates();
 
        // Assert that the response is OK and contains support dates
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(supportDates, response.getBody());
    }
 
    @Test
    public void testViewEndOfSupportDates_Error() {
        // Mock service method to indicate an error occurred
        doThrow(new RuntimeException("Error")).when(technicalSupportService).viewEndOfSupportDates();
 
        // Call controller method
        ResponseEntity<List<String>> response = controller.viewEndOfSupportDates();
 
        // Assert that the response indicates an internal server error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}