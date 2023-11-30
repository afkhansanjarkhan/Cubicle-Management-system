package com.modeln.spaceit.controllers;

import com.modeln.spaceit.entities.Demo;
import com.modeln.spaceit.exceptions.DemoNotFoundException;
import com.modeln.spaceit.services.CSIDemoCsvService;
import com.modeln.spaceit.services.ISIDemoService;
import com.modeln.spaceit.utils.CSICsvResponse;
import com.modeln.spaceit.utils.CSIDemoCsvUtil;
import com.modeln.spaceit.utils.CSiSpecification;
import com.modeln.spaceit.utils.CSiSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    ISIDemoService demoService;

    @Autowired
    CSIDemoCsvService demoCsvService;

    //The function receives a GET request, processes it and gives back a list of Demo as a response.
    @GetMapping
    public ResponseEntity<List<Demo>> getAllDemos(@RequestParam(value = "search", required = false) String search) {
        CSiSpecificationBuilder<Demo> builder = new CSiSpecificationBuilder<>();
        Pattern pattern = Pattern.compile("(\\w+?)(~|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Demo> spec = builder.build();
        List<Demo> demos = demoService.getDemos(spec);
        return new ResponseEntity<>(demos, HttpStatus.OK);
    }

    //The function receives a GET request, processes it, and gives back a list of Demo as a response.
    @GetMapping({"/{demoId}"})
    public ResponseEntity<Demo> getDemo(@PathVariable Long demoId) {
        return new ResponseEntity<>(demoService.getDemoById(demoId), HttpStatus.OK);
    }

    //The function receives a POST request, processes it, creates a new Demo and saves it to the database, and returns a resource link to the created demo.           @PostMapping
    @PostMapping()
    public ResponseEntity<Demo> saveDemo(@RequestBody Demo demo) {
        Demo demo1 = demoService.insert(demo);
        return new ResponseEntity<>(demo1, HttpStatus.CREATED);
    }

    //The function receives a PUT request, updates the Demo with the specified Id and returns the updated Demo
    @PutMapping({"/{demoId}"})
    public ResponseEntity<Demo> updateDemo(@PathVariable("demoId") Long demoId, @RequestBody Demo demo) throws DemoNotFoundException {
        try {
            demoService.updateDemo(demoId, demo);
        } catch (DemoNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(demoService.getDemoById(demoId), HttpStatus.OK);
    }

    //The function receives a DELETE request, deletes the Demo with the specified Id.
    @DeleteMapping({"/{demoId}"})
    public ResponseEntity<Demo> deleteDemo(@PathVariable("demoId") Long demoId) {
        demoService.deleteDemo(demoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload")
    public ResponseEntity<CSICsvResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSIDemoCsvUtil.hasCSVFormat(file)) {
            try {
                demoCsvService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new CSICsvResponse(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new CSICsvResponse(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CSICsvResponse(message));
    }
}