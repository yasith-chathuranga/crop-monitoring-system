package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.customObj.VehicleResponse;
import com.greenshadow.cropmonitoring.dto.impl.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    void updateVehicle(VehicleDTO vehicleDTO);
    void deleteVehicle(String vehicleCode);
    VehicleResponse getSelectedVehicle(String vehicleCode);
    List<VehicleDTO> getAllVehicles();
}
