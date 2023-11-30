package com.modeln.spaceit.repositories;

import com.modeln.spaceit.entities.CSILocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISILocationRepository extends JpaRepository<CSILocation, Long>, JpaSpecificationExecutor <CSILocation>{
    List<CSILocation> findByLocationName(String locationName);

    @Query(nativeQuery=true , value="SELECT location_id from location where location_name=:locationName")
    long findLName(String locationName);

    @Query(nativeQuery = true,value="SELECT location_name from location")
    List<String> findAllLocationNames();
}
