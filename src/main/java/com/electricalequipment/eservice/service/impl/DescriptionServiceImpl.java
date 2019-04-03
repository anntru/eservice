package com.electricalequipment.eservice.service.impl;

import com.electricalequipment.eservice.service.DescriptionService;
import com.electricalequipment.eservice.domain.Description;
import com.electricalequipment.eservice.repository.DescriptionRepository;
import com.electricalequipment.eservice.service.dto.DescriptionDTO;
import com.electricalequipment.eservice.service.mapper.DescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Description.
 */
@Service
@Transactional
public class DescriptionServiceImpl implements DescriptionService {

    private final Logger log = LoggerFactory.getLogger(DescriptionServiceImpl.class);

    private final DescriptionRepository descriptionRepository;

    private final DescriptionMapper descriptionMapper;

    public DescriptionServiceImpl(DescriptionRepository descriptionRepository, DescriptionMapper descriptionMapper) {
        this.descriptionRepository = descriptionRepository;
        this.descriptionMapper = descriptionMapper;
    }

    /**
     * Save a description.
     *
     * @param descriptionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DescriptionDTO save(DescriptionDTO descriptionDTO) {
        log.debug("Request to save Description : {}", descriptionDTO);
        Description description = descriptionMapper.toEntity(descriptionDTO);
        description = descriptionRepository.save(description);
        return descriptionMapper.toDto(description);
    }

    /**
     * Get all the descriptions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DescriptionDTO> findAll() {
        log.debug("Request to get all Descriptions");
        return descriptionRepository.findAll().stream()
            .map(descriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one description by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DescriptionDTO> findOne(Long id) {
        log.debug("Request to get Description : {}", id);
        return descriptionRepository.findById(id)
            .map(descriptionMapper::toDto);
    }

    /**
     * Delete the description by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Description : {}", id);
        descriptionRepository.deleteById(id);
    }
}
