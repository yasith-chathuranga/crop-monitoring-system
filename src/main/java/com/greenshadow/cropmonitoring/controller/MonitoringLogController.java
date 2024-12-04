package com.greenshadow.cropmonitoring.controller;

import com.greenshadow.cropmonitoring.customObj.MonitoringLogResponse;
import com.greenshadow.cropmonitoring.customObj.impl.MonitoringLogErrorResponse;
import com.greenshadow.cropmonitoring.dto.impl.MonitoringLogDTO;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.MonitoringLogNotFoundException;
import com.greenshadow.cropmonitoring.service.MonitoringLogService;
import com.greenshadow.cropmonitoring.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/monitoringLogs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MonitoringLogController {
    @Autowired
    private final MonitoringLogService monitoringLogService;
    @PostMapping
    public ResponseEntity<?> saveMonitoringLog(
            @RequestPart ("logDetails") String logDetails,
            @RequestPart (value = "observedImage") MultipartFile observedImage,
            @RequestParam(value = "fieldCode") List<String> fieldCodes,
            @RequestParam(value = "cropCode") List<String> cropCodes,
            @RequestParam(value = "staffId") List<String> staffIds
    ) {
        try {
            String Base64observedImage = AppUtil.toBase64(observedImage);
            MonitoringLogDTO tmpMonitoringLogDTO = new MonitoringLogDTO();
            tmpMonitoringLogDTO.setLogDate(new Date());
            tmpMonitoringLogDTO.setLogDetails(logDetails);
            tmpMonitoringLogDTO.setObservedImage(Base64observedImage);
            tmpMonitoringLogDTO.setFieldCodes(fieldCodes);
            tmpMonitoringLogDTO.setCropCodes(cropCodes);
            tmpMonitoringLogDTO.setStaffIds(staffIds);
            monitoringLogService.saveMonitoringLog(tmpMonitoringLogDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping(value = "/{logCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMonitoringLog(
            @PathVariable ("logCode") String logCode,
            @RequestPart ("updateLogDetails") String updateLogDetails,
            @RequestPart ("updateObservedImage") MultipartFile updateObservedImage
    ){
        try {
            String updateBase64ObservedImage = AppUtil.toBase64(updateObservedImage);
            MonitoringLogDTO updateMonitoringLog = new MonitoringLogDTO();
            updateMonitoringLog.setLogDetails(updateLogDetails);
            updateMonitoringLog.setObservedImage(updateBase64ObservedImage);
            monitoringLogService.updateMonitoringLog(logCode, updateMonitoringLog);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (MonitoringLogNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{logCode}")
    public ResponseEntity<Void> deleteMonitoringLog(@PathVariable ("logCode") String logCode) {
        try {
            monitoringLogService.deleteMonitoringLog(logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MonitoringLogNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{logCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitoringLogResponse getSelectedMonitoringLog(@PathVariable ("logCode") String logCode){
        if(logCode.isEmpty() || logCode == null){
            return new MonitoringLogErrorResponse(1,"Not valid log code");
        }
        return monitoringLogService.getSelectedMonitoringLog(logCode);
    }
    @GetMapping(value = "allMonitoringLogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitoringLogDTO> getAllMonitoringLogs(){
        return monitoringLogService.getAllMonitoringLogs();
    }
}
