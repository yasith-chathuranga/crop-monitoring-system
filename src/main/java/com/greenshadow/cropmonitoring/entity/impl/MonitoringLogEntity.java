package com.greenshadow.cropmonitoring.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenshadow.cropmonitoring.entity.SuperEntity;
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
@Table(name = "monitoring_log")
public class MonitoringLogEntity implements SuperEntity {
    @Id
    @Column(name = "log_code")
    private String logCode;
    private Date logDate;
    private String logDetails;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "field_log",
            joinColumns = @JoinColumn(name = "field_code"),
            inverseJoinColumns = @JoinColumn(name = "log_code")
    )
    private List<FieldEntity> field;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "log_crop_details",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "crop_code")
    )
    private List<CropEntity> crop;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "log_staff",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<StaffEntity> staff;
}
