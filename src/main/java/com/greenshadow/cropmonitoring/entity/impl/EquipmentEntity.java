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
    @Column(name = "eqiupment_id")
    private String equipmentId;
    private String name;
    private String type;
    private String status;
    @OneToOne(optional = true)
    @JoinColumn(name = "assign_staff_id", referencedColumnName = "id")
    private StaffEntity staff;
    @ManyToOne(optional = true)
    @JoinColumn(name = "assign_field_code", referencedColumnName = "field_code")
    private FieldEntity field;
}
