package com.training.liscenselifecycletracker.controllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.training.liscenselifecycletracker.controller.ManagementController;
import com.training.liscenselifecycletracker.entities.LifecycleEvent;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;
import com.training.liscenselifecycletracker.service.ManagementService;

@SpringBootTest
public class ManagementControllerTest {

    @Mock
    private ManagementService managementService;

    @InjectMocks
    private ManagementController controller;

    @Test
    public void testOverseeLifecycle_Success() throws LifecycleEventNotFoundException {
        // Mock the service method to return a single lifecycle event
        LifecycleEvent lifecycleEvent = new LifecycleEvent(/* Add necessary parameters */);
        when(managementService.overseeLifecycle(anyLong())).thenReturn(lifecycleEvent);

        // Call the controller method with an assetId
        Long assetId = 1L;
        ResponseEntity<?> response = controller.overseeLifecycle(assetId);

        // Assert that the response is OK and contains the lifecycle event
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lifecycleEvent, response.getBody());
    }

    @Test
    public void testOverseeLifecycle_NotFound() throws LifecycleEventNotFoundException {
        // Mock the service method to throw LifecycleEventNotFoundException
        when(managementService.overseeLifecycle(anyLong())).thenThrow(new LifecycleEventNotFoundException("Lifecycle event not found"));

        // Call the controller method with an assetId
        Long assetId = 1L;
        ResponseEntity<?> response = controller.overseeLifecycle(assetId);

        // Assert that the response is NOT_FOUND and contains the error message
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Lifecycle event not found for asset with ID: " + assetId, response.getBody());
    }

    @Test
    public void testGenerateLifecycleReports_Success() throws LifecycleEventNotFoundException {
        // Mock the service method to return a list of lifecycle events
        List<LifecycleEvent> lifecycleEvents = new ArrayList<>();
        when(managementService.generateLifecycleReports(anyList())).thenReturn(lifecycleEvents);

        // Call the controller method with some assetIds
        List<Long> assetIds = List.of(1L, 2L, 3L);
        ResponseEntity<?> response = controller.generateLifecycleReports(assetIds);

        // Assert that the response is OK and contains the list of lifecycle events
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lifecycleEvents, response.getBody());
    }

    @Test
    public void testGenerateLifecycleReports_NotFound() throws LifecycleEventNotFoundException {
        // Mock the service method to throw LifecycleEventNotFoundException
        when(managementService.generateLifecycleReports(anyList())).thenThrow(new LifecycleEventNotFoundException("Lifecycle events not found"));

        // Call the controller method with some assetIds
        List<Long> assetIds = List.of(1L, 2L, 3L);
        ResponseEntity<?> response = controller.generateLifecycleReports(assetIds);

        // Assert that the response is NOT_FOUND and contains the error message
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Lifecycle events not found for provided asset IDs: " + assetIds, response.getBody());
    }

    @Test
    public void testViewAllLifecycleEvents_Success() {
        // Mock the service method to return a list of lifecycle events
        List<LifecycleEvent> lifecycleEvents = new ArrayList<>();
        when(managementService.getAllLifecycleEvents()).thenReturn(lifecycleEvents);

        // Call the controller method
        ResponseEntity<List<LifecycleEvent>> response = controller.viewAllLifecycleEvents();

        // Assert that the response is OK and contains the list of lifecycle events
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lifecycleEvents, response.getBody());
    }
}

