package com.greenshadow.cropmonitoring.controller;

import com.greenshadow.cropmonitoring.customObj.CropResponse;
import com.greenshadow.cropmonitoring.customObj.impl.CropErrorResponse;
import com.greenshadow.cropmonitoring.dto.impl.CropDTO;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.UserNotFoundException;
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

import java.util.List;

@RestController
@RequestMapping("api/v1/crops")
@RequiredArgsConstructor
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
            @RequestPart ("cropImage") MultipartFile cropImage) {
        try {
            logger.info("Received crop data: {} | {} | {} | {} | {} ", cropCommonName, cropScientificName, cropSeason, category, cropImage);
            if (cropImage.isEmpty()) {
                logger.error("Crop image is empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String base64CropImage = AppUtil.toBase64CropImage(cropImage);
            logger.info("Converted image to Base64");
            CropDTO buildCropDTO = new CropDTO();
            buildCropDTO.setCropCommonName(cropCommonName);
            buildCropDTO.setCropScientificName(cropScientificName);
            buildCropDTO.setCropSeason(cropSeason);
            buildCropDTO.setCategory(category);
            buildCropDTO.setCropImage(base64CropImage);
            logger.info("Saving crop data to the service layer...");
            cropService.saveCrop(buildCropDTO);
            logger.info("Crop saved successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            logger.error("Error saving crop data: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value = "/{cropCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(
            @PathVariable ("cropCode") String cropCode,
            @RequestPart("updateCropCommonName") String updateCropCommonName,
            @RequestPart ("updateCropScientificName") String updateCropScientificName,
            @RequestPart ("updateCropSeason") String updateCropSeason,
            @RequestPart ("updateCategory") String updateCategory,
            @RequestPart ("updateCropImage") MultipartFile updateCropImage
    ){
        try {
            String updateBase64CropImage = AppUtil.toBase64CropImage(updateCropImage);
            var updateCrop = new CropDTO();
            updateCrop.setCropCode(cropCode);
            updateCrop.setCropCommonName(updateCropCommonName);
            updateCrop.setCropScientificName(updateCropScientificName);
            updateCrop.setCropSeason(updateCropSeason);
            updateCrop.setCategory(updateCategory);
            updateCrop.setCropImage(updateBase64CropImage);
            cropService.updateCrop(cropCode, updateCrop);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{cropCode}")
    public ResponseEntity<Void> deleteCrop(@PathVariable ("cropCode") String cropCode) {
        try {
            cropService.deleteCrop(cropCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{cropCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectedCrop(@PathVariable ("cropCode") String cropCode){
        if(cropCode.isEmpty() || cropCode == null){
            return new CropErrorResponse(1,"Not valid crop code");
        }
        return cropService.getSelectedCrop(cropCode);
    }
    @GetMapping(value = "allCrops", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops(){
        return cropService.getAllCrops();
    }
}
