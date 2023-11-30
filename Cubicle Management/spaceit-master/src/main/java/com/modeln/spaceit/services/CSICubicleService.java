package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.CSILocation;
import com.modeln.spaceit.exceptions.CSICubicleNotFoundException;
import com.modeln.spaceit.repositories.ISICubicleRepository;
import com.modeln.spaceit.repositories.ISILocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CSICubicleService implements ISICubicleService {

    @Autowired
    ISICubicleRepository cubicleRepository;

    @Autowired
    CSICubicleNotificationService cubicleNotificationService;

    @Autowired
    ISILocationRepository locationRepository;

    @Override
    public List<CSICubicle> getCubicles(Specification<CSICubicle> spec) {
        List<CSICubicle> cubicles = new ArrayList<>();
        cubicleRepository.findAll(spec).forEach(cubicles::add);
        return cubicles;
    }

    @Override
    public CSICubicle getCubicleById(Long id) {
        // findById is provided by jpa by default
        return cubicleRepository.findById(id).get();
    }

    @Override
    public CSICubicle insert(CSICubicle cubicle) {
        String locationName= cubicle.getLocation().getLocationName();
        long id=locationRepository.findLName(locationName);
        CSILocation locationFromDb=locationRepository.findById(id).get();
        cubicle.setLocation(locationFromDb);
        cubicle = cubicleRepository.save(cubicle);
        cubicleNotificationService.newCreateCubicleNotification(cubicle);
        return cubicle;
    }

    @Override
    public CSICubicle updateCubicle(Long id, CSICubicle cubicle) throws CSICubicleNotFoundException {
        CSICubicle cubicleFromDb = getCubicleById(id);
        System.out.println(cubicleFromDb.toString());
        if(cubicleFromDb == null){
            throw new CSICubicleNotFoundException("Cubicle not found for given Id");
        }
        cubicleFromDb.setCubicleId(cubicle.getCubicleId());
        cubicleFromDb.setStatus(cubicle.getStatus());
        cubicleRepository.save(cubicleFromDb);
        return cubicleFromDb;
    }

    @Override
    public void deleteCubicle(Long cubicleId){
        cubicleRepository.deleteById(cubicleId);
    }

}
