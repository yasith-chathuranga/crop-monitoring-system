package com.greenshadow.cropmonitoring.util;

import com.greenshadow.cropmonitoring.dto.impl.CropDTO;
import com.greenshadow.cropmonitoring.entity.impl.CropEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //matters of CropEntity and DTO
    public CropDTO convertToDTO(CropEntity crop) {
        return modelMapper.map(crop, CropDTO.class);
    }
    public CropEntity convertToEntity(CropDTO dto) {
        return modelMapper.map(dto, CropEntity.class);
    }
    public List<CropDTO> convertToDTOList(List<CropEntity> crops) {
        return modelMapper.map(crops, new TypeToken<List<CropDTO>>() {}.getType());
    }
}
