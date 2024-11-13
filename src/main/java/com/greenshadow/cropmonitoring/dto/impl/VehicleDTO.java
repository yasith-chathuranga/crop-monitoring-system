package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.VehicleResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleDTO implements SuperDTO, VehicleResponse {
    private String vehicleCode;
    private String plateNumber;
    private String vehicleCategory;
    private String fuelType;
    private String status;
    private String remarks;
    private List<StaffDTO> allocateStaff = new ArrayList<>();
}
