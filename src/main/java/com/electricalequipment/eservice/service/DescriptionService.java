package com.electricalequipment.eservice.service;

import com.electricalequipment.eservice.service.dto.DescriptionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Description.
 */
public interface DescriptionService {

    /**
     * Save a description.
     *
     * @param descriptionDTO the entity to save
     * @return the persisted entity
     */
    DescriptionDTO save(DescriptionDTO descriptionDTO);

    /**
     * Get all the descriptions.
     *
     * @return the list of entities
     */
    List<DescriptionDTO> findAll();


    /**
     * Get the "id" description.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DescriptionDTO> findOne(Long id);

    /**
     * Delete the "id" description.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
