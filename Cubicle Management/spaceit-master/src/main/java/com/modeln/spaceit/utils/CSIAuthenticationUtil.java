package com.modeln.spaceit.utils;

import com.modeln.spaceit.entities.CSIEmployee;
import com.modeln.spaceit.repositories.ISIEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CSIAuthenticationUtil {
    @Autowired
    ISIEmployeeRepository employeeRepository;
    public CSIEmployee getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return employeeRepository.findByEmailId(email).get(0);
    }
}
