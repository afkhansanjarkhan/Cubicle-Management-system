package com.modeln.spaceit.repositories;

import com.modeln.spaceit.entities.CSINotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ISINotificationRepository extends JpaRepository<CSINotification, Long> , JpaSpecificationExecutor<CSINotification> {

}
