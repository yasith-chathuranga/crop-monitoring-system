package com.greenshadow.cropmonitoring.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "crop")
public class CropEntity implements SuperEntity {
    @Id
    @Column(name = "crop_code")
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;
    private String category;
    private String cropSeason;
    @ManyToOne
    @JoinColumn(name = "field_code", referencedColumnName = "field_code")
    private FieldEntity field;
    @ManyToMany(mappedBy = "crop")
    @JsonIgnore
    private List<MonitoringLogEntity> monitoringLogs;
}
