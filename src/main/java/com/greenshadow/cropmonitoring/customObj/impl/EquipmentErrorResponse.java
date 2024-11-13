package com.greenshadow.cropmonitoring.customObj.impl;

import com.greenshadow.cropmonitoring.customObj.EquipmentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentErrorResponse implements EquipmentResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
