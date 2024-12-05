package com.greenshadow.cropmonitoring.controller;

import com.greenshadow.cropmonitoring.customObj.VehicleResponse;
import com.greenshadow.cropmonitoring.customObj.impl.VehicleErrorResponse;
import com.greenshadow.cropmonitoring.dto.impl.VehicleDTO;
import com.greenshadow.cropmonitoring.exception.AlreadyExistsException;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.NotFoundException;
import com.greenshadow.cropmonitoring.exception.VehicleNotFoundException;
import com.greenshadow.cropmonitoring.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@RequestBody VehicleDTO vehicle) {
        try {
            vehicleService.saveVehicle(vehicle);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value = "/{vehicleCode}", params = "staffId")
    public ResponseEntity<Void> updateVehicle(
            @PathVariable ("vehicleCode") String vehicleCode,
            @RequestBody VehicleDTO vehicle,
            @RequestParam("staffId") String staffId) {
        try {
            vehicleService.updateVehicle(vehicleCode, vehicle, staffId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value ="/{vehicleCode}" )
    public ResponseEntity<Void> deleteVehicle(@PathVariable ("vehicleCode") String vehicleCode) {
        try {
            vehicleService.deleteVehicle(vehicleCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (VehicleNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{vehicleCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleResponse getSelectedVehicle(@PathVariable ("vehicleCode") String vehicleCode)  {
        if(vehicleCode.isEmpty() || vehicleCode == null){
            return new VehicleErrorResponse(1,"Vehicle valid vehicle code");
        }
        return vehicleService.getSelectedVehicle(vehicleCode);
    }
    @GetMapping(value = "allVehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getAllVehicle(){
        return vehicleService.getAllVehicles();
    }
}
