package com.modeln.spaceit.configurations;

import com.modeln.spaceit.entities.*;
import com.modeln.spaceit.enums.CSIRole;
import com.modeln.spaceit.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CSIDbInit {
    @Autowired
    DemoRepository demoRepository;

    @Autowired
    ISICompanyRepository companyRepository;

    @Autowired
    ISILocationRepository locationRepository;

    @Autowired
    ISIDepartmentRepository departmentRepository;

    @Autowired
    ISIEmployeeRepository employeeRepository;

    @PostConstruct
    public void postConstruct(){
        addSampleDemos();
        addCompany();
        addLocation();
        addDepartment();
        addEmployee();

    }


    private void addCompany() {
        if((companyRepository.findByCompanyName("ModelN").size()==0)){
            CSICompany company1 = new CSICompany();
            company1.setCompanyName("ModelN");
            company1.setCompanyAddress("Mind space");
            companyRepository.save(company1);
        }
    }

    private void addLocation() {
        if((locationRepository.findByLocationName("Hyderabad").size()==0)){
            CSILocation location1 = new CSILocation();
            location1.setLocationName("Hyderabad");
            location1.setAddress("Mind space");
            location1.setCompany(companyRepository.findByCompanyName("ModelN").get(0));
            locationRepository.save(location1);
        }
    }



    private void addSampleDemos(){
        if((demoRepository.findByName("pavan").size() ==0)) {
            Demo demo1 = new Demo();
            demo1.setName("pavan");
            demo1.setEmail("pavan@modeln.com");
            demoRepository.save(demo1);
        }
        if((demoRepository.findByName("ashok").size() ==0)) {
            Demo demo2 = new Demo();
            demo2.setName("ashok");
            demo2.setEmail("ashok@modeln.com");
            demoRepository.save(demo2);
        }
    }

    private void addDepartment() {
        if((departmentRepository.findByDepartmentName("LS").size()==0)){
            CSIDepartment department1 = new CSIDepartment();
            department1.setDepartmentName("LS");
            department1.setCompany(companyRepository.findByCompanyName("ModelN").get(0));
            departmentRepository.save(department1);
        }
        if((departmentRepository.findByDepartmentName("HT").size()==0)){
            CSIDepartment department2 = new CSIDepartment();
            department2.setDepartmentName("HT");
            department2.setCompany(companyRepository.findByCompanyName("ModelN").get(0));
            departmentRepository.save(department2);
        }
        if((departmentRepository.findByDepartmentName("Platform").size()==0)){
            CSIDepartment department3 = new CSIDepartment();
            department3.setDepartmentName("Platform");
            department3.setCompany(companyRepository.findByCompanyName("ModelN").get(0));
            departmentRepository.save(department3);
        }
    }

    private void addEmployee() {
        if((employeeRepository.findByEmployeeName("User").size()==0)){
            CSIEmployee employee1 = new CSIEmployee();
            employee1.setEmployeeId(4138l);
            employee1.setEmployeeName("User");
            employee1.setEmailId("user@modeln.com");
            employee1.setPassword("iAmUser");
            employee1.setDepartment(departmentRepository.findByDepartmentName("LS").get(0));
            employee1.setLocation(locationRepository.findByLocationName("Hyderabad").get(0));
            employee1.setRole(CSIRole.USER);
            employeeRepository.save(employee1);
        }
        if((employeeRepository.findByEmployeeName("Admin").size()==0)){
            CSIEmployee employee2 = new CSIEmployee();
            employee2.setEmployeeId(9999l);
            employee2.setEmployeeName("Admin");
            employee2.setEmailId("admin@modeln.com");
            employee2.setDepartment(departmentRepository.findByDepartmentName("HT").get(0));
            employee2.setLocation(locationRepository.findByLocationName("Hyderabad").get(0));
            employee2.setPassword("iAmAdmin");
            employee2.setRole(CSIRole.ADMIN);
            employeeRepository.save(employee2);
        }
    }
}
