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
@Table(name = "crop")
public class CropEntity implements SuperEntity {
    @Id
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String cropImage;
    private String category;
    private String cropSeason;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "field_code")
    private FieldEntity field;
}
