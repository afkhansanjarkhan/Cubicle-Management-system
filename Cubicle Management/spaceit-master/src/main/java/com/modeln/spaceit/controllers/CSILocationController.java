package com.modeln.spaceit.controllers;

import com.modeln.spaceit.repositories.ISILocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/location")
public class CSILocationController {

    @Autowired
    ISILocationRepository locationRepository;

    @GetMapping("/getLocationNames")
    public ResponseEntity<List<String>> getLocationNames() {
        List<String> locationNames = locationRepository.findAllLocationNames();
        return new ResponseEntity<>(locationNames, HttpStatus.OK);

    }
}
