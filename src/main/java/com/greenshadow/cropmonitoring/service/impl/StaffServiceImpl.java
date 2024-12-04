package com.greenshadow.cropmonitoring.service.impl;

import com.greenshadow.cropmonitoring.customObj.StaffResponse;
import com.greenshadow.cropmonitoring.customObj.impl.StaffErrorResponse;
import com.greenshadow.cropmonitoring.dao.StaffDao;
import com.greenshadow.cropmonitoring.dto.impl.StaffDTO;
import com.greenshadow.cropmonitoring.entity.impl.StaffEntity;
import com.greenshadow.cropmonitoring.exception.*;
import com.greenshadow.cropmonitoring.service.StaffService;
import com.greenshadow.cropmonitoring.util.AppUtil;
import com.greenshadow.cropmonitoring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveStaff(StaffDTO staffDTO) {
        staffDTO.setId(AppUtil.createStaffId());
        var staffEntity = mapping.convertToEntity(staffDTO);
        var savedStaff = staffDao.save(staffEntity);
        if (savedStaff == null) {
            throw new DataPersistFailedException("Cannot save staff");
        }
    }
    @Override
    public void updateStaff(String id, StaffDTO incomeStaffDTO) {
        StaffEntity staff = staffDao.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff Not found"));
        staff.setFirstName(incomeStaffDTO.getFirstName());
        staff.setLastName(incomeStaffDTO.getLastName());
        staff.setDesignation(incomeStaffDTO.getDesignation());
        staff.setGender(incomeStaffDTO.getGender());
        staff.setJoinedDate(incomeStaffDTO.getJoinedDate());
        staff.setDob(incomeStaffDTO.getDob());
        staff.setAddressName(incomeStaffDTO.getAddressName());
        staff.setAddressLane(incomeStaffDTO.getAddressLane());
        staff.setAddressCity(incomeStaffDTO.getAddressCity());
        staff.setAddressState(incomeStaffDTO.getAddressState());
        staff.setAddressCode(incomeStaffDTO.getAddressCode());
        staff.setContactNo(incomeStaffDTO.getContactNo());
        staff.setEmail(incomeStaffDTO.getEmail());
        staff.setRole(incomeStaffDTO.getRole());
    }
    @Override
    public void deleteStaff(String id) {
        Optional<StaffEntity> findId = staffDao.findById(id);
        if(!findId.isPresent()){
            throw new StaffNotFoundException("Staff not found");
        }else {
            staffDao.deleteById(id);
        }
    }
    @Override
    public StaffResponse getSelectedStaff(String id) {
        if(staffDao.existsById(id)){
            return mapping.convertToDTO(staffDao.getReferenceById(id));
        }else {
            return new StaffErrorResponse(0,"Staff not found");
        }
    }
    @Override
    public List<StaffDTO> getAllStaff() {
        return mapping.convertToDTOStaffList(staffDao.findAll());
    }
}
