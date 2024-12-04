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
    @Autowired
    private final FieldService fieldService;
    private static final Logger logger = LoggerFactory.getLogger(FieldController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocationX") int fieldLocationX,
            @RequestParam("fieldLocationY") int fieldLocationY,
            @RequestParam ("fieldExtentSize") double fieldExtentSize,
            @RequestParam ("fieldImage1") MultipartFile fieldImage1,
            @RequestParam ("fieldImage2") MultipartFile fieldImage2
    ){
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldName(fieldName);
        fieldDTO.setFieldLocation(new Point(fieldLocationX, fieldLocationY));
        fieldDTO.setFieldExtentSize(fieldExtentSize);
        fieldDTO.setFieldImage1(AppUtil.toBase64(fieldImage1));
        fieldDTO.setFieldImage2(AppUtil.toBase64(fieldImage2));
        try {
            fieldService.saveField(fieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value = "/{fieldCode}", params = "staffIds")
    public ResponseEntity<Void> updateField(
            @PathVariable ("fieldCode") String fieldCode,
            @RequestParam("updateFieldName") String updateFieldName,
            @RequestParam ("updateFieldLocationX") int updateFieldLocationX,
            @RequestParam ("updateFieldLocationY") int updateFieldLocationY,
            @RequestParam ("updateFieldExtentSize") double updateFieldExtentSize,
            @RequestParam ("updateFieldImage1") MultipartFile updateFieldImage1,
            @RequestParam ("updateFieldImage2") MultipartFile updateFieldImage2,
            @RequestParam("staffIds") List<String> staffIds

    ){
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setFieldCode(fieldCode);
        fieldDTO.setFieldName(updateFieldName);
        fieldDTO.setFieldLocation(new Point(updateFieldLocationX, updateFieldLocationY));
        fieldDTO.setFieldExtentSize(updateFieldExtentSize);
        fieldDTO.setFieldImage1(AppUtil.toBase64(updateFieldImage1));
        fieldDTO.setFieldImage2(AppUtil.toBase64(updateFieldImage2));
        try {
            fieldService.updateField(fieldDTO, staffIds);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FieldNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable ("fieldCode") String fieldCode) {
        try {
            fieldService.deleteField(fieldCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{fieldCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getSelectedField(@PathVariable ("fieldCode") String fieldCode){
        if(fieldCode.isEmpty() || fieldCode == null){
            return new FieldErrorResponse(1,"Not valid field code");
        }
        return fieldService.getSelectedField(fieldCode);
    }
    @GetMapping(value = "allFields", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields(){
        return fieldService.getAllFields();
    }
}
