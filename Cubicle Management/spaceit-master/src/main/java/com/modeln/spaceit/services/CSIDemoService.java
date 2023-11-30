package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.Demo;
import com.modeln.spaceit.exceptions.DemoNotFoundException;
import com.modeln.spaceit.repositories.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CSIDemoService implements ISIDemoService {

        @Autowired
        DemoRepository demoRepository;
        @Override
        public List<Demo> getDemos(Specification<Demo> spec) {
            List<Demo> demos = new ArrayList<>();
            demoRepository.findAll(spec).forEach(demos::add);
            return demos;
        }

        @Override
        public List<Demo> getDemoByName(String name) {
            return demoRepository.findByName(name);
        }

    @Override
    public Demo getDemoById(Long id) {
            // findById is provided by jpa by default
        return demoRepository.findById(id).get();
    }
        @Override
        public Demo insert(Demo demo) {
            return demoRepository.save(demo);
        }

        @Override
        public void updateDemo(Long id, Demo demo) throws  DemoNotFoundException {
            Demo demoFromDb = demoRepository.findById(id).get();
            System.out.println(demoFromDb.toString());
            if(demoFromDb == null){
                throw new DemoNotFoundException("Demo not found for given Id");
            }
            demoFromDb.setName(demo.getName());
            demoFromDb.setEmail(demo.getEmail());
            demoRepository.save(demoFromDb);
        }

        @Override
        public void deleteDemo(Long demoId) {
            demoRepository.deleteById(demoId);
        }
    
}
