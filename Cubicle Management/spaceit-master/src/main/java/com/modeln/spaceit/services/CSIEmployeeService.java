package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSIEmployee;
import com.modeln.spaceit.exceptions.CSIEmployeeNotFoundException;
import com.modeln.spaceit.repositories.ISIEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CSIEmployeeService implements ISIEmployeeService{

    @Autowired
    ISIEmployeeRepository ISIEmployeeRepository;
    @Override
    public List<CSIEmployee> getEmployees(Specification<CSIEmployee> spec) {
        List<CSIEmployee> employees = new ArrayList<>();
        ISIEmployeeRepository.findAll(spec).forEach(employees::add);
        return employees;
    }

    @Override
    public List<CSIEmployee> getEmployeeByName(String employeeName) {
        return ISIEmployeeRepository.findByEmployeeName(employeeName);
    }

    @Override
    public CSIEmployee getEmployeeById(Long id) {
        return ISIEmployeeRepository.findById(id).get();
    }

    @Override
    public CSIEmployee insert(CSIEmployee employee) {
        return ISIEmployeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(Long id, CSIEmployee employee) throws CSIEmployeeNotFoundException {
        CSIEmployee employeeFromDb = ISIEmployeeRepository.findById(id).get();
        System.out.println(employeeFromDb.toString());
        if(employeeFromDb == null){
            throw new CSIEmployeeNotFoundException("Employee not found for given Id");
        }
        employeeFromDb.setEmployeeId(employee.getEmployeeId());
        employeeFromDb.setEmployeeName(employee.getEmployeeName());
        employeeFromDb.setEmailId(employee.getEmailId());
        employeeFromDb.setPassword(employee.getPassword());
        employeeFromDb.setRole(employee.getRole());
        ISIEmployeeRepository.save(employeeFromDb);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        ISIEmployeeRepository.deleteById(employeeId);
    }

}
