package com.greenshadow.cropmonitoring.service.impl;

import com.greenshadow.cropmonitoring.customObj.VehicleResponse;
import com.greenshadow.cropmonitoring.customObj.impl.VehicleErrorResponse;
import com.greenshadow.cropmonitoring.dao.StaffDao;
import com.greenshadow.cropmonitoring.dao.VehicleDao;
import com.greenshadow.cropmonitoring.dto.impl.VehicleDTO;
import com.greenshadow.cropmonitoring.entity.impl.StaffEntity;
import com.greenshadow.cropmonitoring.entity.impl.VehicleEntity;
import com.greenshadow.cropmonitoring.exception.AlreadyExistsException;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.StaffNotFoundException;
import com.greenshadow.cropmonitoring.exception.VehicleNotFoundException;
import com.greenshadow.cropmonitoring.service.VehicleService;
import com.greenshadow.cropmonitoring.util.AppUtil;
import com.greenshadow.cropmonitoring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        String vehicleCode = AppUtil.createVehicleCode();
        vehicleDTO.setVehicleCode(vehicleCode);
        if (vehicleDao.existsByPlateNumber(vehicleDTO.getPlateNumber())){
            throw new AlreadyExistsException("Vehicle Plate Number Already Exists");
        }else {
            VehicleEntity save = vehicleDao.save(mapping.convertToEntity(vehicleDTO));
            if (save == null){
                throw new DataPersistFailedException("Vehicle Save failed");
            }
        }
    }
    @Override
    public void updateVehicle(String vehicleCode, VehicleDTO incomeVehicleDTO, String staffId) {
        VehicleEntity vehicle = vehicleDao.findById(vehicleCode)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        StaffEntity staff = null;
        if (!staffId.equals("N/A")) {
            staff = staffDao.findById(staffId)
                    .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
            vehicle.setStaff(staff);
        }
        vehicle.setPlateNumber(incomeVehicleDTO.getPlateNumber());
        vehicle.setVehicleCategory(incomeVehicleDTO.getVehicleCategory());
        vehicle.setFuelType(incomeVehicleDTO.getFuelType());
        vehicle.setStatus(incomeVehicleDTO.getStatus());
        vehicle.setRemarks(incomeVehicleDTO.getRemarks());
        if (staff != null) {
            vehicle.setStaff(staff);
        } else {
            vehicle.setStaff(null);
        }
        vehicleDao.save(vehicle);
    }
    @Override
    public void deleteVehicle(String vehicleCode) {
        Optional<VehicleEntity> findId = vehicleDao.findById(vehicleCode);
        if(!findId.isPresent()){
            throw new VehicleNotFoundException("Vehicle not found");
        }else {
            vehicleDao.deleteById(vehicleCode);
        }
    }
    @Override
    public VehicleResponse getSelectedVehicle(String vehicleCode) {
        if(vehicleDao.existsById(vehicleCode)){
            return mapping.convertToDTO(vehicleDao.getReferenceById(vehicleCode));
        }else {
            return new VehicleErrorResponse(0,"Vehicle not found");
        }
    }
    @Override
    public List<VehicleDTO> getAllVehicles() {
        return mapping.convertToDTOVehicleList(vehicleDao.findAll());
    }
}
