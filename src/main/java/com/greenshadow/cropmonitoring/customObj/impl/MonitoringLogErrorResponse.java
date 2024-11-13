package com.greenshadow.cropmonitoring.customObj.impl;

import com.greenshadow.cropmonitoring.customObj.MonitoringLogResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonitoringLogErrorResponse implements MonitoringLogResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
