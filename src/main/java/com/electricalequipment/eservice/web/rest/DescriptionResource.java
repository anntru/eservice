package com.electricalequipment.eservice.web.rest;
import com.electricalequipment.eservice.service.DescriptionService;
import com.electricalequipment.eservice.web.rest.errors.BadRequestAlertException;
import com.electricalequipment.eservice.web.rest.util.HeaderUtil;
import com.electricalequipment.eservice.service.dto.DescriptionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Description.
 */
@RestController
@RequestMapping("/api")
public class DescriptionResource {

    private final Logger log = LoggerFactory.getLogger(DescriptionResource.class);

    private static final String ENTITY_NAME = "description";

    private final DescriptionService descriptionService;

    public DescriptionResource(DescriptionService descriptionService) {
        this.descriptionService = descriptionService;
    }

    /**
     * POST  /descriptions : Create a new description.
     *
     * @param descriptionDTO the descriptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new descriptionDTO, or with status 400 (Bad Request) if the description has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/descriptions")
    public ResponseEntity<DescriptionDTO> createDescription(@Valid @RequestBody DescriptionDTO descriptionDTO) throws URISyntaxException {
        log.debug("REST request to save Description : {}", descriptionDTO);
        if (descriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new description cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DescriptionDTO result = descriptionService.save(descriptionDTO);
        return ResponseEntity.created(new URI("/api/descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /descriptions : Updates an existing description.
     *
     * @param descriptionDTO the descriptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated descriptionDTO,
     * or with status 400 (Bad Request) if the descriptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the descriptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/descriptions")
    public ResponseEntity<DescriptionDTO> updateDescription(@Valid @RequestBody DescriptionDTO descriptionDTO) throws URISyntaxException {
        log.debug("REST request to update Description : {}", descriptionDTO);
        if (descriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DescriptionDTO result = descriptionService.save(descriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, descriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /descriptions : get all the descriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of descriptions in body
     */
    @GetMapping("/descriptions")
    public List<DescriptionDTO> getAllDescriptions() {
        log.debug("REST request to get all Descriptions");
        return descriptionService.findAll();
    }

    /**
     * GET  /descriptions/:id : get the "id" description.
     *
     * @param id the id of the descriptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the descriptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/descriptions/{id}")
    public ResponseEntity<DescriptionDTO> getDescription(@PathVariable Long id) {
        log.debug("REST request to get Description : {}", id);
        Optional<DescriptionDTO> descriptionDTO = descriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(descriptionDTO);
    }

    /**
     * DELETE  /descriptions/:id : delete the "id" description.
     *
     * @param id the id of the descriptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/descriptions/{id}")
    public ResponseEntity<Void> deleteDescription(@PathVariable Long id) {
        log.debug("REST request to delete Description : {}", id);
        descriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
