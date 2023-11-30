package com.modeln.spaceit.controllers;

import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.Demo;
import com.modeln.spaceit.exceptions.CSICubicleNotFoundException;
import com.modeln.spaceit.repositories.ISICubicleRepository;
import com.modeln.spaceit.services.ISICubicleService;
import com.modeln.spaceit.utils.CSiSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/cubicle")
public class CSICubicleController {

    @Autowired
    ISICubicleService cubicleService;

    @Autowired
    ISICubicleRepository cubicleRepository;

    @GetMapping
    public ResponseEntity<List<CSICubicle>> getAllCubicles(@RequestParam(value = "search", required = false) String search) {
        CSiSpecificationBuilder<CSICubicle> builder = new CSiSpecificationBuilder<>();
        Pattern pattern = Pattern.compile("(\\w+?)(~|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<CSICubicle> spec = builder.build();
        List<CSICubicle> cubicles = cubicleService.getCubicles(spec);
        return new ResponseEntity<>(cubicles, HttpStatus.OK);
    }

    @GetMapping({"/{cubicleId}"})
    public ResponseEntity<CSICubicle> getCubicle(@PathVariable Long cubicleId) {
        return new ResponseEntity<>(cubicleService.getCubicleById(cubicleId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CSICubicle> saveCubicle(@RequestBody CSICubicle cubicle) {
        CSICubicle cubicle1 = cubicleService.insert(cubicle);
        return new ResponseEntity<>(cubicle1, HttpStatus.CREATED);
    }
    @GetMapping("/getCubicleIds")
    public ResponseEntity<List<String>> getCubicleIds() {
        List<String> cubicleIds = cubicleRepository.findAllCubicleIds();
        return new ResponseEntity<>(cubicleIds, HttpStatus.OK);
    }
    @PutMapping({"/{cubicleId}"})
    public ResponseEntity<CSICubicle> updateCubicle(@PathVariable("cubicleId") Long cubicleId, @RequestBody CSICubicle cubicle) throws CSICubicleNotFoundException {


        try {
            cubicleService.updateCubicle(cubicleId, cubicle);
        }
        catch(CSICubicleNotFoundException e){
            Logger logger = Logger.getLogger(String.valueOf(CSICubicleController.class));
            logger.info("NOT_FOUND");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cubicleService.getCubicleById(cubicleId), HttpStatus.OK);
    }

    @DeleteMapping({"/{cubicleId}"})
    public ResponseEntity<Demo> deleteCubicle(@PathVariable("cubicleId") Long cubicleId) {
        cubicleService.deleteCubicle(cubicleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
