package com.modeln.spaceit.controllers;

import java.util.Objects;

import com.modeln.spaceit.configurations.CSIJwtTokenUtil;
import com.modeln.spaceit.services.CSIJwtUserDetailsService;
import com.modeln.spaceit.utils.CSIJwtRequest;
import com.modeln.spaceit.utils.CSIJwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class CSIJwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CSIJwtTokenUtil jwtTokenUtil;

    @Autowired
    private CSIJwtUserDetailsService userDetailsService;

    @RequestMapping("/login")
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody CSIJwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new CSIJwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}