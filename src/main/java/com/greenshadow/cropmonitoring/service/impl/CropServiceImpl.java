package com.greenshadow.cropmonitoring.service.impl;

import com.greenshadow.cropmonitoring.customObj.CropResponse;
import com.greenshadow.cropmonitoring.customObj.impl.CropErrorResponse;
import com.greenshadow.cropmonitoring.dao.CropDao;
import com.greenshadow.cropmonitoring.dto.impl.CropDTO;
import com.greenshadow.cropmonitoring.entity.impl.CropEntity;
import com.greenshadow.cropmonitoring.exception.CropNotFoundException;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
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
    private Mapping mapping;
    public void saveCrop(CropDTO cropDTO) {
        List<String> allCropCodes = cropDao.findAllCropCodes();
        String newCropCode = AppUtil.createCropCode(allCropCodes);
        cropDTO.setCropCode(newCropCode);
        var cropEntity = mapping.convertToEntity(cropDTO);
        var savedCrop = cropDao.save(cropEntity);
        if (savedCrop == null) {
            throw new DataPersistFailedException("Cannot save crop");
        }
    }
    @Override
    public void updateCrop(String cropCode,CropDTO incomeCropDTO) {
        Optional<CropEntity> tmpCropEntity= cropDao.findById(cropCode);
        if(!tmpCropEntity.isPresent()){
            throw new CropNotFoundException("Crop not found");
        }else {
            tmpCropEntity.get().setCropImage(incomeCropDTO.getCropImage());
            tmpCropEntity.get().setCropSeason(incomeCropDTO.getCropSeason());
            tmpCropEntity.get().setCropCommonName(incomeCropDTO.getCropCommonName());
            tmpCropEntity.get().setCropScientificName(incomeCropDTO.getCropScientificName());
            tmpCropEntity.get().setCategory(incomeCropDTO.getCategory());
        }
    }
    @Override
    public void deleteCrop(String cropCode) {
        Optional<CropEntity> findId = cropDao.findById(cropCode);
        if(!findId.isPresent()){
            throw new CropNotFoundException("Crop not found");
        }else {
            cropDao.deleteById(cropCode);
        }
    }
    @Override
    public CropResponse getSelectedCrop(String cropCode) {
        if(cropDao.existsById(cropCode)){
            return mapping.convertToDTO(cropDao.getReferenceById(cropCode));
        }else {
            return new CropErrorResponse(0,"Crop not found");
        }
    }
    @Override
    public List<CropDTO> getAllCrops() {
        return mapping.convertToDTOList(cropDao.findAll());
    }
}
