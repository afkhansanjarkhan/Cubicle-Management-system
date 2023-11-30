package com.modeln.spaceit.repositories;
import com.modeln.spaceit.entities.CSICompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ISICompanyRepository extends JpaRepository<CSICompany, Long>, JpaSpecificationExecutor<CSICompany> {
    List<CSICompany> findByCompanyName(String companyName);
}
