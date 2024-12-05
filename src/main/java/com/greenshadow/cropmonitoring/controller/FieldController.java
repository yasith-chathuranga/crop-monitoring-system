package com.greenshadow.cropmonitoring.controller;

import com.greenshadow.cropmonitoring.customObj.FieldResponse;
import com.greenshadow.cropmonitoring.customObj.impl.FieldErrorResponse;
import com.greenshadow.cropmonitoring.dto.impl.FieldDTO;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.FieldNotFoundException;
import com.greenshadow.cropmonitoring.service.FieldService;
import com.greenshadow.cropmonitoring.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/fields")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FieldController {
    private final FieldService fieldService;
    private static final Logger logger = LoggerFactory.getLogger(FieldController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocationX") int fieldLocationX,
            @RequestParam("fieldLocationY") int fieldLocationY,
            @RequestParam("fieldExtentSize") double fieldExtentSize,
            @RequestParam("fieldImage1") MultipartFile fieldImage1,
            @RequestParam("fieldImage2") MultipartFile fieldImage2
    ) {
        logger.info("Saving field with name: {} and location: ({}, {})", fieldName, fieldLocationX, fieldLocationY);
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldName(fieldName);
        fieldDTO.setFieldLocation(new Point(fieldLocationX, fieldLocationY));
        fieldDTO.setFieldExtentSize(fieldExtentSize);
        fieldDTO.setFieldImage1(AppUtil.toBase64(fieldImage1));
        fieldDTO.setFieldImage2(AppUtil.toBase64(fieldImage2));

        try {
            fieldService.saveField(fieldDTO);
            logger.info("Field saved successfully with name: {}", fieldName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            logger.error("Error persisting field: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error while saving field: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{fieldCode}", params = "staffIds")
    public ResponseEntity<Void> updateField(
            @PathVariable("fieldCode") String fieldCode,
            @RequestParam("updateFieldName") String updateFieldName,
            @RequestParam("updateFieldLocationX") int updateFieldLocationX,
            @RequestParam("updateFieldLocationY") int updateFieldLocationY,
            @RequestParam("updateFieldExtentSize") double updateFieldExtentSize,
            @RequestParam("updateFieldImage1") MultipartFile updateFieldImage1,
            @RequestParam("updateFieldImage2") MultipartFile updateFieldImage2,
            @RequestParam("staffIds") List<String> staffIds
    ) {
        logger.info("Updating field with code: {}", fieldCode);
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldCode(fieldCode);
        fieldDTO.setFieldName(updateFieldName);
        fieldDTO.setFieldLocation(new Point(updateFieldLocationX, updateFieldLocationY));
        fieldDTO.setFieldExtentSize(updateFieldExtentSize);
        fieldDTO.setFieldImage1(AppUtil.toBase64(updateFieldImage1));
        fieldDTO.setFieldImage2(AppUtil.toBase64(updateFieldImage2));

        try {
            fieldService.updateField(fieldDTO, staffIds);
            logger.info("Field updated successfully with code: {}", fieldCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            logger.error("Field not found while updating: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            logger.error("Error persisting field update: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error while updating field: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode) {
        logger.info("Deleting field with code: {}", fieldCode);
        try {
            fieldService.deleteField(fieldCode);
            logger.info("Field deleted successfully with code: {}", fieldCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFoundException e) {
            logger.error("Field not found while deleting: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting field: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getSelectedField(@PathVariable("fieldCode") String fieldCode) {
        logger.info("Fetching details for field with code: {}", fieldCode);
        if (fieldCode.isEmpty() || fieldCode == null) {
            logger.error("Invalid field code: {}", fieldCode);
            return new FieldErrorResponse(1, "Not valid field code");
        }
        return fieldService.getSelectedField(fieldCode);
    }

    @GetMapping(value = "allFields", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields() {
        logger.info("Fetching all fields");
        return fieldService.getAllFields();
    }
}
