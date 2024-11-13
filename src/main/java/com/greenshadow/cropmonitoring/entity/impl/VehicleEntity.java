package com.greenshadow.cropmonitoring.entity.impl;

import com.greenshadow.cropmonitoring.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity implements SuperEntity {
    @Id
    private String vehicleCode;
    private String plateNumber;
    private String vehicleCategory;
    private String fuelType;
    private String status;
    private String remarks;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_vehicle_code", referencedColumnName = "vehicleCode")
    private List<StaffEntity> allocateStaff;
}
