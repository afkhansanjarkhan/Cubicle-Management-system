package com.modeln.spaceit.services;

import java.util.ArrayList;
import java.util.List;

import com.modeln.spaceit.entities.CSIEmployee;
import com.modeln.spaceit.repositories.ISIEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CSIJwtUserDetailsService implements UserDetailsService {
    @Autowired
    ISIEmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<CSIEmployee> employee = employeeRepository.findByEmailId(username);
        if (employee.size() == 0 ) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(employee.get(0).getEmailId(), employee.get(0).getPassword(),new ArrayList<>());
    }
}