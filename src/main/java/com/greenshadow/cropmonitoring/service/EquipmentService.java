package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.customObj.EquipmentResponse;
import com.greenshadow.cropmonitoring.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);
    void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO, String staffId , String fieldCode);
    void deleteEquipment(String equipmentId);
    EquipmentResponse getSelectedEquipment(String equipmentId);
    List<EquipmentResponse> getAllEquipments();
}
