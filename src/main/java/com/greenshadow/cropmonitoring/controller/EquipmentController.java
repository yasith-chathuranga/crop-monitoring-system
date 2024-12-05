package com.greenshadow.cropmonitoring.controller;

import com.greenshadow.cropmonitoring.customObj.EquipmentResponse;
import com.greenshadow.cropmonitoring.customObj.impl.EquipmentErrorResponse;
import com.greenshadow.cropmonitoring.dto.impl.EquipmentDTO;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.EquipmentNotFoundException;
import com.greenshadow.cropmonitoring.exception.NotFoundException;
import com.greenshadow.cropmonitoring.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/equipments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EquipmentController {
    private final EquipmentService equipmentService;
    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipment) {
        logger.info("Saving equipment: {}", equipment);
        if (equipment == null) {
            logger.error("Failed to save equipment: equipment data is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                equipmentService.saveEquipment(equipment);
                logger.info("Equipment saved successfully: {}", equipment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e) {
                logger.error("Data persistence error while saving equipment: {}", e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.error("Unexpected error while saving equipment: {}", e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PatchMapping(value = "/{equipmentId}", params = {"staffIds", "fieldCode"})
    public ResponseEntity<?> updateEquipment(
            @PathVariable("equipmentId") String equipmentId,
            @RequestBody EquipmentDTO equipment,
            @RequestParam("staffIds") String staffId,
            @RequestParam("fieldCode") String fieldCode
    ) {
        logger.info("Updating equipment: {}, with staffIds: {}, fieldCode: {}", equipmentId, staffId, fieldCode);
        try {
            equipmentService.updateEquipment(equipmentId, equipment, staffId, fieldCode);
            logger.info("Equipment updated successfully: {}", equipmentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataPersistFailedException e) {
            logger.error("Data persistence error while updating equipment: {}", e.getMessage());
            return new ResponseEntity<>("Data persistence issue: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            logger.error("Equipment not found while updating: {}", e.getMessage());
            return new ResponseEntity<>("Not found issue: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error while updating equipment: {}", e.getMessage());
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {
        logger.info("Deleting equipment: {}", equipmentId);
        try {
            equipmentService.deleteEquipment(equipmentId);
            logger.info("Equipment deleted successfully: {}", equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e) {
            logger.error("Equipment not found while deleting: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting equipment: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getSelectedEquipment(@PathVariable("equipmentId") String equipmentId) {
        logger.info("Fetching equipment details for: {}", equipmentId);
        if (equipmentId.isEmpty() || equipmentId == null) {
            logger.error("Invalid equipment ID: {}", equipmentId);
            return new EquipmentErrorResponse(1, "Equipment valid equipment id");
        }
        return equipmentService.getSelectedEquipment(equipmentId);
    }

    @GetMapping(value = "allEquipments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEquipments() {
        logger.info("Fetching all equipment");
        try {
            return new ResponseEntity<>(equipmentService.getAllEquipments(), HttpStatus.OK);
        } catch (DataPersistFailedException e) {
            logger.error("Data persistence error while fetching all equipment: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching all equipment: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
