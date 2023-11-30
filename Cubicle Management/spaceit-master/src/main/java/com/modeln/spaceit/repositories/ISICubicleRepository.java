package com.modeln.spaceit.repositories;

import com.modeln.spaceit.entities.CSICubicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISICubicleRepository extends JpaRepository<CSICubicle, Long> , JpaSpecificationExecutor<CSICubicle> {

    @Query(nativeQuery=true, value="SELECT id from cubicle where cubicle_id=:cubicleId")
    long findByCId(String cubicleId);


    @Query(nativeQuery = true, value="SELECT cubicle_id from cubicle where Status='OPEN'")
    List<String> findAllCubicleIds();
}
