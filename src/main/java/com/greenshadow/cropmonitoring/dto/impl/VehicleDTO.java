package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.VehicleResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleDTO implements SuperDTO, VehicleResponse {
    private String vehicleCode;
    @NotBlank
    private String plateNumber;
    @NotBlank
    private String vehicleCategory;
    @NotBlank
    private String fuelType;
    @NotBlank
    private String status;
    private String remarks;
    private String staffId;
}
