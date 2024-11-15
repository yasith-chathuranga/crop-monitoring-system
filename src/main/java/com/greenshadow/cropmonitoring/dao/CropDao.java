package com.greenshadow.cropmonitoring.dao;

import com.greenshadow.cropmonitoring.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropDao extends JpaRepository<CropEntity, String> {
    @Query("SELECT c.cropCode FROM CropEntity c WHERE c.cropCode LIKE 'CRP%'")
    List<String> findAllCropCodes();

}
