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
    private final CropService cropService;
    private static final Logger logger = LoggerFactory.getLogger(CropController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("cropCommonName") String cropCommonName,
            @RequestPart("cropScientificName") String cropScientificName,
            @RequestPart("cropSeason") String cropSeason,
            @RequestPart("category") String category,
            @RequestParam("cropImage") MultipartFile cropImage,
            @RequestParam(value = "fieldCode") String fieldCode
    ) {
        logger.info("Saving new crop: {}", cropCommonName);
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCommonName(cropCommonName);
        cropDTO.setCategory(category);
        cropDTO.setCropSeason(cropSeason);
        cropDTO.setCropScientificName(cropScientificName);
        cropDTO.setCropImage(AppUtil.toBase64(cropImage));

        try {
            cropService.saveCrop(cropDTO, fieldCode);
            logger.info("Crop saved successfully: {}", cropCommonName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NotFoundException e) {
            logger.error("Crop save failed, not found: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataPersistFailedException e) {
            logger.error("Crop save failed due to data persistence issue: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while saving crop: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{cropCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(
            @RequestPart("updateCropCommonName") String updateCropCommonName,
            @RequestPart("updateCropScientificName") String updateCropScientificName,
            @RequestPart("updateCropSeason") String updateCropSeason,
            @RequestPart("updateCategory") String updateCategory,
            @RequestParam("updateCropImage") MultipartFile updateCropImage,
            @RequestParam("fieldCode") String fieldCode,
            @PathVariable String cropCode
    ) {
        logger.info("Updating crop: {}", cropCode);
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCommonName(updateCropCommonName);
        cropDTO.setCategory(updateCategory);
        cropDTO.setCropSeason(updateCropSeason);
        cropDTO.setCropScientificName(updateCropScientificName);
        cropDTO.setCropImage(AppUtil.toBase64(updateCropImage));

        try {
            cropService.updateCrop(cropDTO, fieldCode, cropCode);
            logger.info("Crop updated successfully: {}", cropCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Crop update failed, not found: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataPersistFailedException e) {
            logger.error("Crop update failed due to data persistence issue: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating crop: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cropCode}")
    public ResponseEntity<Void> deleteCrop(@PathVariable String cropCode) {
        logger.info("Deleting crop: {}", cropCode);
        try {
            cropService.deleteCrop(cropCode);
            logger.info("Crop deleted successfully: {}", cropCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFoundException e) {
            logger.error("Crop delete failed, not found: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while deleting crop: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cropCode}")
    public ResponseEntity<?> getSelectedCrop(@PathVariable String cropCode) {
        logger.info("Fetching crop details for: {}", cropCode);
        try {
            return new ResponseEntity<>(cropService.getSelectedCrop(cropCode), HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Crop not found: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching crop: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "allCrops", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCrops() {
        logger.info("Fetching all crops");
        try {
            return new ResponseEntity<>(cropService.getAllCrops(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while fetching all crops: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
