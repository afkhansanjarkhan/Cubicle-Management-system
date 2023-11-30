package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.exceptions.CSICubicleNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISICubicleService {

    List<CSICubicle> getCubicles(Specification<CSICubicle> spec);

    CSICubicle getCubicleById(Long id);

    CSICubicle insert(CSICubicle cubicle);

    CSICubicle updateCubicle(Long id, CSICubicle cubicle) throws CSICubicleNotFoundException;

    void deleteCubicle(Long cubicleId);
}
