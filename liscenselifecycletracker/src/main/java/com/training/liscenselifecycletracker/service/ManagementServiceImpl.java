package com.training.liscenselifecycletracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.liscenselifecycletracker.entities.LifecycleEvent;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;
import com.training.liscenselifecycletracker.repositories.LifecycleEventRepository;

@Service
public class ManagementServiceImpl implements ManagementService {

    @Autowired
    LifecycleEventRepository lifecycleEventRepository;

    @Override
    public LifecycleEvent overseeLifecycle(Long assetId) throws LifecycleEventNotFoundException {
        // Fetch lifecycle event for the given assetId
        LifecycleEvent eventOptional = lifecycleEventRepository.findByAssetId(assetId);
        if (eventOptional != null) {
            return eventOptional;
        } else {
            throw new LifecycleEventNotFoundException("Lifecycle event not found for asset with ID: " + assetId);
        }
    }

    @Override
    public List<LifecycleEvent> generateLifecycleReports(List<Long> assetIds) throws LifecycleEventNotFoundException {
        // Fetch all lifecycle events for the given list of asset IDs
        List<LifecycleEvent> events = new ArrayList<>();
        for (Long assetId : assetIds) {
            List<LifecycleEvent> eventsForAsset = lifecycleEventRepository.findAllByAssetId(assetId);
            events.addAll(eventsForAsset);
        }
        
        if (!events.isEmpty()) {
            return events;
        } else {
            throw new LifecycleEventNotFoundException("Lifecycle events not found for provided asset IDs: " + assetIds);
        }
    }

    @Override
    public List<LifecycleEvent> getAllLifecycleEvents() {
        // Fetch all lifecycle events
        return (List<LifecycleEvent>) lifecycleEventRepository.findAll();
    }



}
