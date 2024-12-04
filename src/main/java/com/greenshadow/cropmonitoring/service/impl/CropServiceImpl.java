package com.greenshadow.cropmonitoring.service.impl;

import com.greenshadow.cropmonitoring.customObj.CropResponse;
import com.greenshadow.cropmonitoring.customObj.impl.CropErrorResponse;
import com.greenshadow.cropmonitoring.dao.CropDao;
import com.greenshadow.cropmonitoring.dao.FieldDao;
import com.greenshadow.cropmonitoring.dto.impl.CropDTO;
import com.greenshadow.cropmonitoring.entity.impl.CropEntity;
import com.greenshadow.cropmonitoring.entity.impl.FieldEntity;
import com.greenshadow.cropmonitoring.exception.CropNotFoundException;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.FieldNotFoundException;
import com.greenshadow.cropmonitoring.exception.NotFoundException;
import com.greenshadow.cropmonitoring.service.CropService;
import com.greenshadow.cropmonitoring.util.AppUtil;
import com.greenshadow.cropmonitoring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CropServiceImpl implements CropService {
    @Autowired
    private CropDao cropDao;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private Mapping mapping;
    public void saveCrop(CropDTO cropDTO, String fieldCode) {
        cropDTO.setCropCode(AppUtil.createCropCode());
        CropEntity crop = mapping.convertCropDTOToCrop(cropDTO);
        FieldEntity field = fieldDao.findById(fieldCode).orElseThrow(
                () -> new NotFoundException("Field not found")
        );
        crop.setField(field);
        CropEntity save = cropDao.save(crop);
        if (save == null){
            throw new DataPersistFailedException("Crop save failed");
        }
    }
    @Override
    public void updateCrop(CropDTO incomeCropDTO, String fieldCode, String id) {
        Optional<CropEntity> tmpCropEntity = cropDao.findByCropCode(id);
        if (tmpCropEntity.isPresent()){
            FieldEntity field = fieldDao.findById(fieldCode).orElseThrow(
                    () -> new FieldNotFoundException("Field not found")
            );
            tmpCropEntity.get().setField(field);
            tmpCropEntity.get().setCropCommonName(incomeCropDTO.getCropCommonName());
            tmpCropEntity.get().setCategory(incomeCropDTO.getCategory());
            tmpCropEntity.get().setCropSeason(incomeCropDTO.getCropSeason());
            tmpCropEntity.get().setCropScientificName(incomeCropDTO.getCropScientificName());
            tmpCropEntity.get().setCropImage(incomeCropDTO.getCropImage());
        }else {
            throw new CropNotFoundException("Crop not found");
        }
    }
    @Override
    public void deleteCrop(String cropCode) {
        Optional<CropEntity> byCropCode = cropDao.findByCropCode(cropCode);
        if (byCropCode.isPresent()){
            cropDao.delete(byCropCode.get());
        }else {
            throw new NotFoundException("Crop not found");
        }
    }
    @Override
    public CropResponse getSelectedCrop(String cropCode) {
        Optional<CropEntity> byCropCode = cropDao.findByCropCode(cropCode);
        if (byCropCode.isPresent()){
            CropDTO cropDTO = mapping.convertCropToCropDTO(byCropCode.get());
            return cropDTO;
        }else {
            return new CropErrorResponse(0,"Crop not found");
        }
    }
    @Override
    public List getAllCrops() {
        return mapping.convertCropListToCropDTOList(cropDao.findAll());
    }
}
