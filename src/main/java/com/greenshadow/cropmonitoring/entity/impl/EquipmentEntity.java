package com.greenshadow.cropmonitoring.entity.impl;

import com.greenshadow.cropmonitoring.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class EquipmentEntity implements SuperEntity {
    @Id
    private String equipmentId;
    private String name;
    private String type;
    private String status;
    @OneToOne
    private StaffEntity assignStaff;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assign_field")
    private FieldEntity assignField;
}
