package com.modeln.spaceit.controllers;

import com.modeln.spaceit.configurations.CSIWebSecurityConfig;
import com.modeln.spaceit.entities.CSIEmployee;
import com.modeln.spaceit.exceptions.CSIEmployeeNotFoundException;
import com.modeln.spaceit.repositories.ISIEmployeeRepository;
import com.modeln.spaceit.services.ISIEmployeeService;
import com.modeln.spaceit.utils.CSIAuthenticationUtil;
import com.modeln.spaceit.utils.CSIPasswordReset;
import com.modeln.spaceit.utils.CSiSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/employee")
public class CSIEmployeeController {
    @Autowired
    ISIEmployeeService employeeService;

    @Autowired
    CSIWebSecurityConfig webSecurityConfig;

    @Autowired
    CSIAuthenticationUtil authenticationUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ISIEmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<CSIEmployee>> getAllEmployees(@RequestParam(value = "search", required = false) String search){
        CSiSpecificationBuilder<CSIEmployee> builder = new CSiSpecificationBuilder<>();
        Pattern pattern = Pattern.compile("(\\w+?)(~|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<CSIEmployee> spec = builder.build();
        List<CSIEmployee> employees = employeeService.getEmployees(spec);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<CSIEmployee> getEmployee(@PathVariable("employeeId") Long employeeId){
        return new ResponseEntity<>(employeeService.getEmployeeById(employeeId),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CSIEmployee> saveEmployee(@RequestBody CSIEmployee employee){
        CSIEmployee newEmployee = employeeService.insert(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);

    }
    @GetMapping("/getEmployeeIds")
    public ResponseEntity<List<Integer>> getEmployeeIds() {
        List<Integer> employeeIds = employeeRepository.findAllEmployeeIds();
        return new ResponseEntity<>(employeeIds, HttpStatus.OK);

    }
    @PutMapping("/changePass")
    public ResponseEntity<CSIEmployee> changePassword(@RequestBody CSIPasswordReset pwdReset) throws Exception{
        CSIEmployee employee = authenticationUtil.getLoggedInUser();
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        CSIEmployee employeeFromDb=employeeRepository.findById(employee.getId()).get();

        if(!(passwordEncoder.matches(pwdReset.getNewPassword(),(pwdReset.getOldPassword())))){

            employeeFromDb.setPassword(pwdReset.getNewPassword());

            employeeRepository.save(employee);
//
            return new ResponseEntity<>(employee, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(employee, HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<CSIEmployee> updateEmployee(@PathVariable ("employeeId") Long employeeId, @RequestBody CSIEmployee employee) throws CSIEmployeeNotFoundException {
        try{
            employeeService.updateEmployee(employeeId, employee);
        }
        catch(CSIEmployeeNotFoundException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
    }
    @DeleteMapping({"/{employeeId}"})
    public ResponseEntity<CSIEmployee> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}