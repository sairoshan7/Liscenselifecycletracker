package com.training.liscenselifecycletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.liscenselifecycletracker.entities.LifecycleEvent;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;
import com.training.liscenselifecycletracker.service.ManagementService;

@RestController
@RequestMapping("/api/management")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ManagementController {

    @Autowired 
    ManagementService managementService;

    @GetMapping("/lifecycle")
    public ResponseEntity<?> overseeLifecycle(@RequestParam Long assetId) {
        try {
            LifecycleEvent event = managementService.overseeLifecycle(assetId);
            return ResponseEntity.ok(event);
        } catch (LifecycleEventNotFoundException e) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lifecycle event not found for asset with ID: " + assetId);
        }
    }
    
    @GetMapping("/reports")
    public ResponseEntity<?> generateLifecycleReports(@RequestParam List<Long> assetIds) {
        try {
            List<LifecycleEvent> reports = managementService.generateLifecycleReports(assetIds);
            return ResponseEntity.ok(reports);
        } catch (LifecycleEventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lifecycle events not found for provided asset IDs: " + assetIds);
        }
    }
    
    @GetMapping("/lifecycle/events")
    public ResponseEntity<List<LifecycleEvent>> viewAllLifecycleEvents() {
        List<LifecycleEvent> allEvents = managementService.getAllLifecycleEvents();
        return ResponseEntity.ok(allEvents);
    }
}
