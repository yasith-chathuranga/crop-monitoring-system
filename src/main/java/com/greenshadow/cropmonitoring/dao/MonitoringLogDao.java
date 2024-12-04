package com.greenshadow.cropmonitoring.dao;

import com.greenshadow.cropmonitoring.entity.impl.MonitoringLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonitoringLogDao extends JpaRepository<MonitoringLogEntity, String> {
    Optional<MonitoringLogEntity> findMonitoringLogEntityByLogCode(String logCode);
}
