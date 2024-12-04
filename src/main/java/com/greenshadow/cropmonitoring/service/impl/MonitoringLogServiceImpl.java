package com.greenshadow.cropmonitoring.service.impl;

import com.greenshadow.cropmonitoring.customObj.MonitoringLogResponse;
import com.greenshadow.cropmonitoring.customObj.impl.MonitoringLogErrorResponse;
import com.greenshadow.cropmonitoring.dao.CropDao;
import com.greenshadow.cropmonitoring.dao.FieldDao;
import com.greenshadow.cropmonitoring.dao.MonitoringLogDao;
import com.greenshadow.cropmonitoring.dao.StaffDao;
import com.greenshadow.cropmonitoring.dto.impl.MonitoringLogDTO;
import com.greenshadow.cropmonitoring.entity.impl.CropEntity;
import com.greenshadow.cropmonitoring.entity.impl.FieldEntity;
import com.greenshadow.cropmonitoring.entity.impl.MonitoringLogEntity;
import com.greenshadow.cropmonitoring.entity.impl.StaffEntity;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.MonitoringLogNotFoundException;
import com.greenshadow.cropmonitoring.service.MonitoringLogService;
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
public class MonitoringLogServiceImpl implements MonitoringLogService {
    @Autowired
    private MonitoringLogDao monitoringLogDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CropDao cropDao;
    @Override
    public void saveMonitoringLog(MonitoringLogDTO monitoringLogDTO) {
        List<FieldEntity> filed = new ArrayList<>();
        List<CropEntity> crops = new ArrayList<>();
        List<StaffEntity> staff = new ArrayList<>();
        for (String fieldCode : monitoringLogDTO.getFieldCodes()) {
            fieldDao.findById(fieldCode).ifPresent(filed::add);
        }
        for (String cropCode : monitoringLogDTO.getCropCodes()) {
            cropDao.findByCropCode(cropCode).ifPresent(crops::add);
        }
        for (String staffId : monitoringLogDTO.getStaffIds()) {
            staffDao.findById(staffId).ifPresent(staff::add);
        }
        String logCode = AppUtil.createLogCode();
        monitoringLogDTO.setLogCode(logCode);
        MonitoringLogEntity monitoringLog = mapping.convertToEntity(monitoringLogDTO);
        monitoringLog.setField(filed);
        monitoringLog.setCrop(crops);
        monitoringLog.setStaff(staff);
        MonitoringLogEntity save = monitoringLogDao.save(monitoringLog);
        if (save == null){
            throw new DataPersistFailedException("Monitoring Log save failed");
        }
    }
    @Override
    public void updateMonitoringLog(String logCode, MonitoringLogDTO incomeMonitoringLogDTO) {
        Optional<MonitoringLogEntity> monitoringLogByLogCode = monitoringLogDao.findMonitoringLogEntityByLogCode(logCode);
        if (monitoringLogByLogCode.isPresent()){
            monitoringLogByLogCode.get().setLogDetails(incomeMonitoringLogDTO.getLogDetails());
            monitoringLogByLogCode.get().setObservedImage(incomeMonitoringLogDTO.getObservedImage());
        }else {
            throw new MonitoringLogNotFoundException("Monitoring Log not found");
        }
    }
    @Override
    public void deleteMonitoringLog(String logCode) {
        Optional<MonitoringLogEntity> monitoringLogByLogCode = monitoringLogDao.findMonitoringLogEntityByLogCode(logCode);
        if (monitoringLogByLogCode.isPresent()) {
            monitoringLogDao.delete(monitoringLogByLogCode.get());
        }else {
            throw new MonitoringLogNotFoundException("Monitoring Log not found");
        }
    }
    @Override
    public MonitoringLogResponse getSelectedMonitoringLog(String logCode) {
        Optional<MonitoringLogEntity> monitoringLogByLogCode = monitoringLogDao.findMonitoringLogEntityByLogCode(logCode);
        if (monitoringLogByLogCode.isPresent()){
            MonitoringLogDTO monitoringLogDTO = mapping.convertToDTO(monitoringLogByLogCode.get());
            if (monitoringLogByLogCode.get().getField() != null){
                List<String> fieldCodes = new ArrayList<>();
                monitoringLogByLogCode.get().getField().forEach(
                        field -> fieldCodes.add(field.getFieldCode())
                );
                monitoringLogDTO.setFieldCodes(fieldCodes);
            }
            if (monitoringLogByLogCode.get().getCrop() != null){
                List<String> cropCodes = new ArrayList<>();
                monitoringLogByLogCode.get().getCrop().forEach(
                        crop -> cropCodes.add(crop.getCropCode())
                );
                monitoringLogDTO.setCropCodes(cropCodes);
            }
            if (monitoringLogByLogCode.get().getStaff() != null){
                List<String> staffIds = new ArrayList<>();
                monitoringLogByLogCode.get().getStaff().forEach(
                        staff -> staffIds.add(staff.getId())
                );
                monitoringLogDTO.setStaffIds(staffIds);
            }
            return monitoringLogDTO;
        }else {
            return new MonitoringLogErrorResponse(0,"Crop details not found");
        }
    }
    @Override
    public List<MonitoringLogDTO> getAllMonitoringLogs() {
        List<MonitoringLogEntity> all = monitoringLogDao.findAll();
        return mapping.convertToDTOMonitoringLogList(all);
    }
}
