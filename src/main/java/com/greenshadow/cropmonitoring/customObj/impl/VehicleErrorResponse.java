package com.greenshadow.cropmonitoring.customObj.impl;

import com.greenshadow.cropmonitoring.customObj.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleErrorResponse implements VehicleResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
