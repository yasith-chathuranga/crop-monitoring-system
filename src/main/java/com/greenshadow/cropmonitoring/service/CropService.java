package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.customObj.CropResponse;
import com.greenshadow.cropmonitoring.dto.impl.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO, String fieldCode);
    void updateCrop(CropDTO cropDTO, String fieldCode, String id);
    void deleteCrop(String cropCode);
    CropResponse getSelectedCrop(String cropCode);
    List<CropDTO> getAllCrops();
}
