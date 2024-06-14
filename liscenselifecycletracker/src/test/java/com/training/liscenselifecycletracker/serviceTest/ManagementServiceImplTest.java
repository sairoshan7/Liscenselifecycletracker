package com.training.liscenselifecycletracker.serviceTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.training.liscenselifecycletracker.entities.LifecycleEvent;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;
import com.training.liscenselifecycletracker.repositories.LifecycleEventRepository;
import com.training.liscenselifecycletracker.service.ManagementServiceImpl;

public class ManagementServiceImplTest {

    @Mock
    private LifecycleEventRepository lifecycleEventRepository;

    @InjectMocks
    private ManagementServiceImpl managementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOverseeLifecycle_LifecycleEventFound() throws LifecycleEventNotFoundException {
        Long assetId = 1L;
        LifecycleEvent lifecycleEvent = new LifecycleEvent();
        when(lifecycleEventRepository.findByAssetId(assetId)).thenReturn(lifecycleEvent);

        LifecycleEvent result = managementService.overseeLifecycle(assetId);

        assertEquals(lifecycleEvent, result);
    }

    @Test
    public void testOverseeLifecycle_LifecycleEventNotFound() {
        Long assetId = 1L;
        when(lifecycleEventRepository.findByAssetId(assetId)).thenReturn(null);

        assertThrows(LifecycleEventNotFoundException.class, () -> {
            managementService.overseeLifecycle(assetId);
        });
    }

    @Test
    public void testGenerateLifecycleReports_LifecycleEventsFound() throws LifecycleEventNotFoundException {
        List<Long> assetIds = new ArrayList<>();
        assetIds.add(1L);
        assetIds.add(2L);

        List<LifecycleEvent> events = new ArrayList<>();
        events.add(new LifecycleEvent());
        events.add(new LifecycleEvent());

        when(lifecycleEventRepository.findAllByAssetId(1L)).thenReturn(events);
        when(lifecycleEventRepository.findAllByAssetId(2L)).thenReturn(events);

        List<LifecycleEvent> result = managementService.generateLifecycleReports(assetIds);

        assertEquals(4, result.size());
    }

    @Test
    public void testGenerateLifecycleReports_LifecycleEventsNotFound() {
        List<Long> assetIds = new ArrayList<>();
        assetIds.add(1L);
        assetIds.add(2L);

        when(lifecycleEventRepository.findAllByAssetId(1L)).thenReturn(new ArrayList<>());
        when(lifecycleEventRepository.findAllByAssetId(2L)).thenReturn(new ArrayList<>());

        assertThrows(LifecycleEventNotFoundException.class, () -> {
            managementService.generateLifecycleReports(assetIds);
        });
    }

    @Test
    public void testGetAllLifecycleEvents() {
        List<LifecycleEvent> events = new ArrayList<>();
        events.add(new LifecycleEvent());
        events.add(new LifecycleEvent());

        when(lifecycleEventRepository.findAll()).thenReturn(events);

        List<LifecycleEvent> result = managementService.getAllLifecycleEvents();

        assertEquals(2, result.size());
    }
}

