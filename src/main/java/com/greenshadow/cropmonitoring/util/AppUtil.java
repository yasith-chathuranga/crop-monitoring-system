package com.greenshadow.cropmonitoring.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class AppUtil {
    private static int userCounter = 1;
    private static int vehicleCounter = 1;
    private static int equipmentCounter = 1;
    private static int cropCounter = 1;
    private static int staffCounter= 1;
    private static int monitoringLogCounter= 1;
    private static int fieldCounter= 1;
    public static String createUserId() {
        String id = String.format("USR%03d", userCounter);
        userCounter++;
        return id;
    }
    public static String createCropCode() {
        String id = String.format("CRP%03d", cropCounter);
        cropCounter++;
        return id;
    }

    public static String createEquipmentId() {
        String id = String.format("EQP%03d", equipmentCounter);
        equipmentCounter++;
        return id;
    }

    public static String createFieldCode() {
        String id = String.format("FLD%03d", fieldCounter);
        fieldCounter++;
        return id;
    }

    public static String createLogCode() {
        String id = String.format("LOG%03d", monitoringLogCounter);
        monitoringLogCounter++;
        return id;
    }

    public static String createVehicleCode() {
        String id = String.format("VEH%03d", vehicleCounter);
        vehicleCounter++;
        return id;
    }

    public static String createStaffId() {
        String id = String.format("STF%03d", staffCounter);
        staffCounter++;
        return id;
    }

    public static String toBase64(MultipartFile file) {
        try {
            byte[] fileByteCollection = file.getBytes();
            return Base64.getEncoder().encodeToString(fileByteCollection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
