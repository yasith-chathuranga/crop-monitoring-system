package com.greenshadow.cropmonitoring.util;

import com.greenshadow.cropmonitoring.dto.impl.*;
import com.greenshadow.cropmonitoring.entity.impl.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;
    //matters of CropEntity and DTO
    public CropDTO convertToDTO(CropEntity crop) {
        return modelMapper.map(crop, CropDTO.class);
    }
    public CropDTO convertCropToCropDTO(CropEntity crop){
        return modelMapper.map(crop, CropDTO.class);
    }
    public CropEntity convertCropDTOToCrop(CropDTO cropDTO){
        return modelMapper.map(cropDTO, CropEntity.class);
    }
    public CropEntity convertToEntity(CropDTO dto) {
        return modelMapper.map(dto, CropEntity.class);
    }
    public List convertCropListToCropDTOList(List<CropEntity> all) {
        return modelMapper.map(all, List.class);
    }
    public List<CropDTO> convertToDTOList(List<CropEntity> crops) {return modelMapper.map(crops, new TypeToken<List<CropDTO>>() {}.getType());}
    //matters of EquipmentEntity and DTO
    public EquipmentDTO convertToDTO(EquipmentEntity equipment) {return modelMapper.map(equipment, EquipmentDTO.class);}
    public EquipmentEntity convertToEntity(EquipmentDTO dto) {
        return modelMapper.map(dto, EquipmentEntity.class);
    }
    public List convertEquipmentListToEquipmentDTOList(List<EquipmentEntity> all) {
        return modelMapper.map(all, List.class);
    }
    //matters of FieldEntity and DTO
    public FieldDTO convertToDTO(FieldEntity field) {
        return modelMapper.map(field, FieldDTO.class);
    }
    public FieldEntity convertToEntity(FieldDTO dto) {
        return modelMapper.map(dto, FieldEntity.class);
    }
    public List<FieldDTO> convertToDTOFieldList(List<FieldEntity> crops) {return modelMapper.map(crops, new TypeToken<List<FieldDTO>>() {}.getType());}
    //matters of MonitoringLogEntity and DTO
    public MonitoringLogDTO convertToDTO(MonitoringLogEntity monitoringLog) {return modelMapper.map(monitoringLog, MonitoringLogDTO.class);}
    public MonitoringLogEntity convertToEntity(MonitoringLogDTO dto) {return modelMapper.map(dto, MonitoringLogEntity.class);}
    public List<MonitoringLogDTO> convertToDTOMonitoringLogList(List<MonitoringLogEntity> monitoringLogs) {return modelMapper.map(monitoringLogs, new TypeToken<List<MonitoringLogDTO>>() {}.getType());}
    //matters of VehicleEntity and DTO
    public VehicleDTO convertToDTO(VehicleEntity vehicle) {return modelMapper.map(vehicle, VehicleDTO.class);}
    public VehicleEntity convertToEntity(VehicleDTO dto) {return modelMapper.map(dto, VehicleEntity.class);}
    public List<VehicleDTO> convertToDTOVehicleList(List<VehicleEntity> vehicles) {return modelMapper.map(vehicles, new TypeToken<List<VehicleDTO>>() {}.getType());}
    //matters of StaffEntity and DTO
    public StaffDTO convertToDTO(StaffEntity staff) {return modelMapper.map(staff, StaffDTO.class);}
    public StaffEntity convertToEntity(StaffDTO dto) {return modelMapper.map(dto, StaffEntity.class);}
    public List<StaffDTO> convertToDTOStaffList(List<StaffEntity> staffs) {return modelMapper.map(staffs, new TypeToken<List<StaffDTO>>() {}.getType());}
}
