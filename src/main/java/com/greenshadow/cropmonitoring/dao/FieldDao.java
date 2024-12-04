package com.greenshadow.cropmonitoring.dao;

import com.greenshadow.cropmonitoring.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldDao extends JpaRepository<FieldEntity, String> {
}
