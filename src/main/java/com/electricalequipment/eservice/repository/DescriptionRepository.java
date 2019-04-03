package com.electricalequipment.eservice.repository;

import com.electricalequipment.eservice.domain.Description;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Description entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {

}
