package com.training.liscenselifecycletracker.service;

import java.util.List;

import com.training.liscenselifecycletracker.exceptions.LifecycleEventNotFoundException;

public interface TechnicalSupportService {
	public void logFault(Long deviceId, String description, String date, String category)throws IllegalArgumentException, LifecycleEventNotFoundException;

	public void updateFaultLog(Long deviceId, String repairDetails, String category, String eventType);

	List<String> viewEndOfSupportDates();


}
