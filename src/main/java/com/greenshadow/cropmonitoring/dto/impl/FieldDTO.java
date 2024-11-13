package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.FieldResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldDTO implements SuperDTO, FieldResponse {
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private double fieldExtentSize;
    private String fieldImage1;
    private String fieldImage2;
    private List<CropDTO> crops = new ArrayList<>();
    private List<StaffDTO> staff = new ArrayList<>();
}
