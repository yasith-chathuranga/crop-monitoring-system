package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.FieldResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldDTO implements SuperDTO, FieldResponse {
    private String fieldCode;
    @NotBlank
    private String fieldName;
    @NotBlank
    private Point fieldLocation;
    @NotNull
    private double fieldExtentSize;
    private String fieldImage1;
    private String fieldImage2;
    @NotNull
    private List<String> staffId;
}
