package com.greenshadow.cropmonitoring.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenshadow.cropmonitoring.entity.SuperEntity;
import com.greenshadow.cropmonitoring.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity implements SuperEntity {
    @Id
    @Column(name = "id")
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    @Enumerated(EnumType.STRING)
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
    @ManyToMany(mappedBy = "staff")
    @JsonIgnore
    private List<FieldEntity> field;
    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private List<VehicleEntity> vehicles;
    @ManyToMany(mappedBy = "staff")
    @JsonIgnore
    private List<MonitoringLogEntity> monitoringLogs;
    @OneToOne(mappedBy = "staff",optional = true)
    @JsonIgnore
    private EquipmentEntity equipment;
}
