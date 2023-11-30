package com.modeln.spaceit.repositories;

import com.modeln.spaceit.entities.CSIEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ISIEmployeeRepository extends JpaRepository<CSIEmployee, Long> ,JpaSpecificationExecutor<CSIEmployee> {
    List<CSIEmployee> findByEmployeeName(String employeeName);
    List<CSIEmployee> findByEmailId(String email);

    @Query(nativeQuery = true, value="SELECT id from employee where employee_id=:employeeId")
    long findByEId(long employeeId);

    @Query(nativeQuery = true, value="SELECT employee_id from employee")
    List<Integer> findAllEmployeeIds();
}
