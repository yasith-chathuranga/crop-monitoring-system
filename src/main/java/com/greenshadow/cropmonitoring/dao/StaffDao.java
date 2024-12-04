package com.greenshadow.cropmonitoring.dao;

import com.greenshadow.cropmonitoring.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffDao extends JpaRepository<StaffEntity, String> {
    Optional<StaffEntity> findByEmail(String email);
}
