package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSIEmployee;
import com.modeln.spaceit.exceptions.CSIEmployeeNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ISIEmployeeService {
    List<CSIEmployee> getEmployees(Specification<CSIEmployee> spec);
    List<CSIEmployee> getEmployeeByName(String employeeName);
    CSIEmployee getEmployeeById(Long employeeId);
    CSIEmployee insert(CSIEmployee employee);
    void updateEmployee(Long employeeId, CSIEmployee employee) throws CSIEmployeeNotFoundException;
    void deleteEmployee(Long employeeId);
}
