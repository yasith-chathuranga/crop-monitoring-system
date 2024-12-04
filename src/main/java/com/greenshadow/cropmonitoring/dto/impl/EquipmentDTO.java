package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.EquipmentResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String equipmentId;
    private String name;
    private String type;
    private String status;
    private String staffId;
    private String fieldCode;
}
