package com.training.liscenselifecycletracker.service;

import java.util.List;

import com.training.liscenselifecycletracker.entities.LifecycleEvent;
import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;

public interface ManagementService {
	
	LifecycleEvent overseeLifecycle(Long assetId)throws LifecycleEventNotFoundException;


    List<LifecycleEvent> getAllLifecycleEvents();

	List<LifecycleEvent> generateLifecycleReports(List<Long> assetIds) throws LifecycleEventNotFoundException;

}
