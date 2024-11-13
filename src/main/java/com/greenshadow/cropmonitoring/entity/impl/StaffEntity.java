package com.greenshadow.cropmonitoring.entity.impl;

import com.greenshadow.cropmonitoring.entity.SuperEntity;
import com.greenshadow.cropmonitoring.entity.enums.Gender;
import com.greenshadow.cropmonitoring.entity.enums.Role;
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
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany
    @JoinTable(name = "staff_field_details",
    joinColumns = @JoinColumn(name = "staff_id"),
    inverseJoinColumns = @JoinColumn(name = "field_code"))
    private List<FieldEntity> fields;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_staff_id",referencedColumnName = "id")
    private List<VehicleEntity> vehicles;
}
