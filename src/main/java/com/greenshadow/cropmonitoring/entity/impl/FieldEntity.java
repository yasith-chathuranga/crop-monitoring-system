package com.greenshadow.cropmonitoring.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenshadow.cropmonitoring.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "field")
public class FieldEntity implements SuperEntity {
    @Id
    @Column(name = "field_code")
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private double fieldExtentSize;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage2;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CropEntity> crop;
    @ManyToMany
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "field_code"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<StaffEntity> staff;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EquipmentEntity> equipment;
    @ManyToMany(mappedBy = "field")
    @JsonIgnore
    private List<MonitoringLogEntity> monitoringLog;
}
