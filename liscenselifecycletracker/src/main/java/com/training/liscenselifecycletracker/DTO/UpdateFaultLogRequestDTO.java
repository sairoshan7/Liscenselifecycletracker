package com.training.liscenselifecycletracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateFaultLogRequestDTO {
	private Long deviceId;
    private String repairDetails;
    private String category;
    private String eventType;

}
