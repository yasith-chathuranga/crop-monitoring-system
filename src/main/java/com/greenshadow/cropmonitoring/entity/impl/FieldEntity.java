package com.greenshadow.cropmonitoring.entity.impl;

import com.greenshadow.cropmonitoring.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "field")
public class FieldEntity implements SuperEntity {
    @Id
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private double fieldExtentSize;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "field")
    private List<CropEntity> crops;
    @ManyToMany(mappedBy = "fields")
    private List<StaffEntity> staff;
}
