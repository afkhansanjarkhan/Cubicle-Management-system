package com.modeln.spaceit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bootstrap")
public class CSIBootstrapController {

    Logger logger= LoggerFactory.getLogger(CSIBootstrapController.class);
    @Value("${com.spaceit.bootstrapEnums}")
    public String bootstrapEnums;
    @GetMapping
    public ResponseEntity<Map> getEnums() {
        HashMap<String, List> result = new HashMap<>();
        long startTime = System.currentTimeMillis();
        for (String enumName : bootstrapEnums.split(",")) {

            try {
                Class<?> enumClass = ClassLoader.getSystemClassLoader().loadClass(enumName);
                List enumValues = Arrays.asList(enumClass.getEnumConstants());
                result.put(enumName, enumValues);
            } catch (Exception e) {
                logger.error("Enum class is not found for " + enumName);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("Completed Reading all enums from properties");
        logger.info("BootStrap data took "+(endTime-startTime)+" milliSeconds");
        return ResponseEntity.ok(result);
    }

}
