package com.greenshadow.cropmonitoring.dao;

import com.greenshadow.cropmonitoring.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropDao extends JpaRepository<CropEntity, String> {
    Optional<CropEntity> findByCropCode(String cropCode);
}
