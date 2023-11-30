package com.modeln.spaceit.repositories;

import com.modeln.spaceit.entities.Demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DemoRepository extends JpaRepository<Demo, Long> , JpaSpecificationExecutor<Demo> {
    //jpa repository provides basic operations like save and update and any specific queries like
    // searching by name can be provided here
    List<Demo> findByName(String name);
}
