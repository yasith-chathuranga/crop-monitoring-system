package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.StaffResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import com.greenshadow.cropmonitoring.entity.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {
    private String id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String designation;
    @NotBlank
    private Gender gender;
    @NotBlank
    private Date joinedDate;
    @NotBlank
    private Date dob;
    @NotBlank
    private String addressName;
    @NotBlank
    private String addressLane;
    @NotBlank
    private String addressCity;
    @NotBlank
    private String addressState;
    @NotBlank
    private String addressCode;
    @NotBlank
    private String contactNo;
    @NotBlank
    private String email;
    @NotBlank
    private String role;
}
