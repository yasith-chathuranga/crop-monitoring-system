package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.MonitoringLogResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonitoringLogDTO implements SuperDTO, MonitoringLogResponse {
    private String logCode;
    private Date logDate;
    private String logDetails;
    private String observedImage;
    private List<String> cropCodes;
    private List<String> fieldCodes;
    private List<String> staffIds;
}
