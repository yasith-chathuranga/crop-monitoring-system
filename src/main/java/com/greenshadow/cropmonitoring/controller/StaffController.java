package com.greenshadow.cropmonitoring.controller;

import com.greenshadow.cropmonitoring.customObj.StaffResponse;
import com.greenshadow.cropmonitoring.customObj.impl.StaffErrorResponse;
import com.greenshadow.cropmonitoring.dto.impl.StaffDTO;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.StaffNotFoundException;
import com.greenshadow.cropmonitoring.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staffs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {
    private final StaffService staffService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staff) {
        if (staff == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                staffService.saveStaff(staff);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PatchMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable ("id") String id, @RequestBody StaffDTO staff) {
        try {
            if (staff == null && (id == null || id.isEmpty())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.updateStaff(id, staff);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (StaffNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value ="/{id}" )
    public ResponseEntity<Void> deleteStaff(@PathVariable ("id") String id) {
        try {
            staffService.deleteStaff(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (StaffNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffResponse getSelectedVehicle(@PathVariable ("id") String id)  {
        if(id.isEmpty() || id == null){
            return new StaffErrorResponse(1,"Staff valid staff id");
        }
        return staffService.getSelectedStaff(id);
    }

    @GetMapping(value = "allStaffs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAllStaffs(){
        return staffService.getAllStaff();
    }
}
