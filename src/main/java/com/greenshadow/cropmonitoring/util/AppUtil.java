package com.greenshadow.cropmonitoring.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class AppUtil {
    public static String createCropCode(List<String> allCropCodes) {
        String prefix = "CRP";
        // Convert codes to integers for easy sorting and checking
        List<Integer> codeNumbers = allCropCodes.stream()
                .filter(code -> code.startsWith(prefix))
                .map(code -> Integer.parseInt(code.substring(prefix.length())))
                .sorted()
                .collect(Collectors.toList());
        // Check for the first missing number in sequence
        int nextId = 1;
        for (int codeNumber : codeNumbers) {
            if (codeNumber != nextId) {
                // A missing code was found in the sequence, reuse this number
                break;
            }
            nextId++;
        }
        // Format with zero-padding to ensure three digits, e.g., "CRP001"
        return prefix + String.format("%03d", nextId);
    }

    public static String toBase64CropImage(MultipartFile cropImage){
        String cropImageBase64 = null;
        try {
            byte [] cropImageBytes = cropImage.getBytes();
            cropImageBase64 =  Base64.getEncoder().encodeToString(cropImageBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cropImageBase64;
    }
}
