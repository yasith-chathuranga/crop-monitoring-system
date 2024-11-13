package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.CropResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CropDTO implements SuperDTO, CropResponse {
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String cropImage;
    private String category;
    private String cropSeason;
    private FieldDTO field;
}
