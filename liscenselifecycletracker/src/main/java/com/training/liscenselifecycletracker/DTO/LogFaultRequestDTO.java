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
public class LogFaultRequestDTO {
    private Long deviceId;
    private String description;
    private String date;
    private String category;

}
