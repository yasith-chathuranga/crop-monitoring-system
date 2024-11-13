package com.greenshadow.cropmonitoring.entity.impl;

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
    private String logCode;
    private Date logDate;
    private String logDetails;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;
    @ManyToMany
    @JoinTable(name = "crop_log_details",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "crop_code"))
    private List<CropEntity> crops;
    @ManyToMany
    @JoinTable(name = "field_log_details",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "field_code"))
    private List<FieldEntity> fields;
    @ManyToMany
    @JoinTable(name = "staff_log_details",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "staff_id"))
    private List<StaffEntity> staff;
}
