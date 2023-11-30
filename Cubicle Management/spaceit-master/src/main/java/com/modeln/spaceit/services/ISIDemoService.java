package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.Demo;
import com.modeln.spaceit.exceptions.DemoNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ISIDemoService {
    List<Demo> getDemos(Specification<Demo> spec);
    List<Demo> getDemoByName(String name);
    Demo getDemoById(Long id );
    Demo insert(Demo demo);
    void updateDemo(Long id, Demo demo) throws DemoNotFoundException;
    void deleteDemo(Long demoId);
}
