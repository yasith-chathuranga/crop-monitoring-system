package com.greenshadow.cropmonitoring.service.impl;

import com.greenshadow.cropmonitoring.customObj.EquipmentResponse;
import com.greenshadow.cropmonitoring.customObj.impl.EquipmentErrorResponse;
import com.greenshadow.cropmonitoring.dao.EquipmentDao;
import com.greenshadow.cropmonitoring.dao.FieldDao;
import com.greenshadow.cropmonitoring.dao.StaffDao;
import com.greenshadow.cropmonitoring.dto.impl.EquipmentDTO;
import com.greenshadow.cropmonitoring.entity.impl.EquipmentEntity;
import com.greenshadow.cropmonitoring.entity.impl.FieldEntity;
import com.greenshadow.cropmonitoring.entity.impl.StaffEntity;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.EquipmentNotFoundException;
import com.greenshadow.cropmonitoring.exception.FieldNotFoundException;
import com.greenshadow.cropmonitoring.exception.StaffNotFoundException;
import com.greenshadow.cropmonitoring.service.EquipmentService;
import com.greenshadow.cropmonitoring.util.AppUtil;
import com.greenshadow.cropmonitoring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        equipmentDTO.setEquipmentId(AppUtil.createEquipmentId());
        var equipmentEntity = mapping.convertToEntity(equipmentDTO);
        var savedEquipment = equipmentDao.save(equipmentEntity);
        if (savedEquipment == null) {
            throw new DataPersistFailedException("Cannot save equipment");
        }
    }
    @Override
    public void updateEquipment(String equipmentId, EquipmentDTO incomeEquipmentDTO, String staffId , String fieldCode) {
        EquipmentEntity equipment = equipmentDao.findById(equipmentId).orElse(null);
        if (equipment != null){
            equipment = mapping.convertToEntity(incomeEquipmentDTO);
            equipment.setEquipmentId(equipmentId);
            if (staffId.equals("N/A")) {
                equipment.setStaff(null);
            } else {
                Optional<StaffEntity> optional = staffDao.findById(staffId);
                if (optional.isPresent()){
                    StaffEntity staff = optional.get();
                    equipment.setStaff(staff);
                }else {
                    throw new StaffNotFoundException("Staff not found");
                }
            }
            if (fieldCode.equals("N/A")) {
                equipment.setField(null);
            } else {
                Optional<FieldEntity> optional = fieldDao.findById(fieldCode);
                if (optional.isPresent()){
                    FieldEntity field = optional.get();
                    equipment.setField(field);
                }else {
                    throw new FieldNotFoundException("Field not found");
                }
            }
        }
        if (equipment != null){
            EquipmentEntity save = equipmentDao.save(equipment);
            if (save == null){
                throw new DataPersistFailedException("Equipment update failed");
            }
        }else {
            throw new EquipmentNotFoundException("Equipment not found");
        }
    }
    @Override
    public void deleteEquipment(String equipmentId) {
        Optional<EquipmentEntity> findId = equipmentDao.findById(equipmentId);
        if(!findId.isPresent()){
            throw new EquipmentNotFoundException("Equipment not found");
        }else {
            equipmentDao.deleteById(equipmentId);
        }
    }
    @Override
    public EquipmentResponse getSelectedEquipment(String equipmentId) {
        if(equipmentDao.existsById(equipmentId)){
            return mapping.convertToDTO(equipmentDao.getReferenceById(equipmentId));
        }else {
            return new EquipmentErrorResponse(0,"Equipment not found");
        }
    }
    @Override
    public List<EquipmentResponse> getAllEquipments() {
        return mapping.convertEquipmentListToEquipmentDTOList(equipmentDao.findAll());
    }
}
