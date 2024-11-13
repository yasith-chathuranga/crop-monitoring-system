package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.customObj.MonitoringLogResponse;
import com.greenshadow.cropmonitoring.customObj.StaffResponse;
import com.greenshadow.cropmonitoring.dto.impl.MonitoringLogDTO;
import com.greenshadow.cropmonitoring.dto.impl.StaffDTO;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO);
    void updateStaff(StaffDTO staffDTO);
    void deleteStaff(String id);
    StaffResponse getSelectedStaff(String id);
    List<StaffDTO> getAllStaff();
}
