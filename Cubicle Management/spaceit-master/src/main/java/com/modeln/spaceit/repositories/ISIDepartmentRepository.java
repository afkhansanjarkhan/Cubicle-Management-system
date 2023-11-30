package com.modeln.spaceit.repositories;
import com.modeln.spaceit.entities.CSIDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ISIDepartmentRepository extends JpaRepository<CSIDepartment,Long>, JpaSpecificationExecutor<CSIDepartment> {
    List<CSIDepartment> findByDepartmentName(String departmentName);
}
