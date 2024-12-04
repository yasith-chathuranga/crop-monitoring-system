package com.greenshadow.cropmonitoring.service.impl;

import com.greenshadow.cropmonitoring.customObj.FieldResponse;
import com.greenshadow.cropmonitoring.customObj.impl.FieldErrorResponse;
import com.greenshadow.cropmonitoring.dao.FieldDao;
import com.greenshadow.cropmonitoring.dao.StaffDao;
import com.greenshadow.cropmonitoring.dto.impl.FieldDTO;
import com.greenshadow.cropmonitoring.entity.impl.FieldEntity;
import com.greenshadow.cropmonitoring.entity.impl.StaffEntity;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.FieldNotFoundException;
import com.greenshadow.cropmonitoring.service.FieldService;
import com.greenshadow.cropmonitoring.util.AppUtil;
import com.greenshadow.cropmonitoring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setFieldCode(AppUtil.createFieldCode());
        var fieldEntity = mapping.convertToEntity(fieldDTO);
        var savedField = fieldDao.save(fieldEntity);
        if (savedField == null) {
            throw new DataPersistFailedException("Cannot save field");
        }
    }
    @Override
    public void updateField(FieldDTO incomeFieldDTO, List<String> staffIds) {
        Optional<FieldEntity> field = fieldDao.findById(incomeFieldDTO.getFieldCode());
        if (field.isPresent()) {
            FieldEntity TempFieldEntity = mapping.convertToEntity(incomeFieldDTO);
            List<StaffEntity> staff = new ArrayList<>();
            for (String staffId : staffIds) {
                Optional<StaffEntity> optional = staffDao.findById(staffId);
                optional.ifPresent(staff::add);
            }
            TempFieldEntity.setStaff(staff);
            FieldEntity save = fieldDao.save(TempFieldEntity);
            if (save == null) {
                throw new DataPersistFailedException("Field update failed");
            }
        }else {
            throw new FieldNotFoundException("Field not found");
        }
    }
    @Override
    public void deleteField(String fieldCode) {
        Optional<FieldEntity> findId = fieldDao.findById(fieldCode);
        if(!findId.isPresent()){
            throw new FieldNotFoundException("Field not found");
        }else {
            fieldDao.deleteById(fieldCode);
        }
    }
    @Override
    public FieldResponse getSelectedField(String fieldCode) {
        if(fieldDao.existsById(fieldCode)){
            return mapping.convertToDTO(fieldDao.getReferenceById(fieldCode));
        }else {
            return new FieldErrorResponse(0,"Field not found");
        }
    }
    @Override
    public List<FieldDTO> getAllFields() {
        return mapping.convertToDTOFieldList(fieldDao.findAll());
    }
}
