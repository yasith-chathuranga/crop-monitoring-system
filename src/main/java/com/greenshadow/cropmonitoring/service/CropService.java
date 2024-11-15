package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.customObj.CropResponse;
import com.greenshadow.cropmonitoring.dto.impl.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO);
    void updateCrop(String cropCode,CropDTO cropDTO);
    void deleteCrop(String cropCode);
    CropResponse getSelectedCrop(String cropCode);
    List<CropDTO> getAllCrops();
}
