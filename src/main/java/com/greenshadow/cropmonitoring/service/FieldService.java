package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.customObj.FieldResponse;
import com.greenshadow.cropmonitoring.dto.impl.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    void updateField(FieldDTO fieldDTO);
    void deleteField(String fieldCode);
    FieldResponse getSelectedField(String fieldCode);
    List<FieldDTO> getAllFields();
}
