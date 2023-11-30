package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.Demo;
import com.modeln.spaceit.repositories.DemoRepository;
import com.modeln.spaceit.utils.CSIDemoCsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSIDemoCsvService {
    @Autowired
    DemoRepository demoRepository;

    public void save(MultipartFile file) {
        try {
            List<Demo> demos = CSIDemoCsvUtil.csvToDemos(file.getInputStream());
            demoRepository.saveAll(demos);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Demo> getAllDemos() {
        return demoRepository.findAll();
    }
}
