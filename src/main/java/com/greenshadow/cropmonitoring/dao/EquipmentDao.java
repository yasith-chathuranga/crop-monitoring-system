package com.greenshadow.cropmonitoring.dao;

import com.greenshadow.cropmonitoring.entity.impl.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDao extends JpaRepository<EquipmentEntity, String> {
}
