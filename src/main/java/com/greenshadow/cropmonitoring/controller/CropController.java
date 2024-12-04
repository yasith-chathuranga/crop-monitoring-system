package com.greenshadow.cropmonitoring.controller;

import com.greenshadow.cropmonitoring.dto.impl.CropDTO;
import com.greenshadow.cropmonitoring.exception.CropNotFoundException;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.NotFoundException;
import com.greenshadow.cropmonitoring.service.CropService;
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

@RestController
@RequestMapping("api/v1/crops")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CropController {
    @Autowired
    private final CropService cropService;
    private static final Logger logger = LoggerFactory.getLogger(CropController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("cropCommonName") String cropCommonName,
            @RequestPart ("cropScientificName") String cropScientificName,
            @RequestPart ("cropSeason") String cropSeason,
            @RequestPart ("category") String category,
            @RequestParam ("cropImage") MultipartFile cropImage,
            @RequestParam(value = "fieldCode") String fieldCode
    ){
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCommonName(cropCommonName);
        cropDTO.setCategory(category);
        cropDTO.setCropSeason(cropSeason);
        cropDTO.setCropScientificName(cropScientificName);
        cropDTO.setCropImage(AppUtil.toBase64(cropImage));
        try {
            cropService.saveCrop(cropDTO, fieldCode);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value = "/{cropCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(
            @RequestPart("updateCropCommonName") String updateCropCommonName,
            @RequestPart ("updateCropScientificName") String updateCropScientificName,
            @RequestPart ("updateCropSeason") String updateCropSeason,
            @RequestPart ("updateCategory") String updateCategory,
            @RequestParam ("updateCropImage") MultipartFile updateCropImage,
            @RequestParam("fieldCode") String fieldCode,
            @PathVariable String cropCode
    ){
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCommonName(updateCropCommonName);
        cropDTO.setCategory(updateCategory);
        cropDTO.setCropSeason(updateCropSeason);
        cropDTO.setCropScientificName(updateCropScientificName);
        cropDTO.setCropImage(AppUtil.toBase64(updateCropImage));
        try {
            cropService.updateCrop(cropDTO, fieldCode, cropCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{cropCode}")
    public ResponseEntity<Void> deleteCrop(@PathVariable String cropCode) {
        try {
            cropService.deleteCrop(cropCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{cropCode}")
    public ResponseEntity<?> getSelectedCrop(@PathVariable String cropCode){
        try {
            return new ResponseEntity<>(cropService.getSelectedCrop(cropCode), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "allCrops", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCrops(){
        return new ResponseEntity<>(cropService.getAllCrops(), HttpStatus.OK);
    }
}
