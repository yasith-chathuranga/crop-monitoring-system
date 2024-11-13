package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.customObj.MonitoringLogResponse;
import com.greenshadow.cropmonitoring.dto.impl.MonitoringLogDTO;

import java.util.List;

public interface MonitoringLogService {
    void saveMonitoringLog(MonitoringLogDTO monitoringLogDTO);
    void updateMonitoringLog(MonitoringLogDTO monitoringLogDTO);
    void deleteMonitoringLog(String logCode);
    MonitoringLogResponse getSelectedMonitoringLog(String logCode);
    List<MonitoringLogDTO> getAllMonitoringLogs();
}
