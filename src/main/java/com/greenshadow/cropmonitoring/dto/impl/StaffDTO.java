package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.StaffResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import com.greenshadow.cropmonitoring.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    private Gender gender;
    private Date joinedDate;
    private Date dob;
    private String addressName;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String addressCode;
    private String contactNo;
    private String email;
    private String role;
}
