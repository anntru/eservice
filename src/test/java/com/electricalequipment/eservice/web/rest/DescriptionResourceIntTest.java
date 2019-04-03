package com.electricalequipment.eservice.web.rest;

import com.electricalequipment.eservice.EserviceApp;

import com.electricalequipment.eservice.domain.Description;
import com.electricalequipment.eservice.repository.DescriptionRepository;
import com.electricalequipment.eservice.service.DescriptionService;
import com.electricalequipment.eservice.service.dto.DescriptionDTO;
import com.electricalequipment.eservice.service.mapper.DescriptionMapper;
import com.electricalequipment.eservice.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.electricalequipment.eservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DescriptionResource REST controller.
 *
 * @see DescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EserviceApp.class)
public class DescriptionResourceIntTest {

    private static final String DEFAULT_PARAMETER = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER = "BBBBBBBBBB";

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private DescriptionMapper descriptionMapper;

    @Autowired
    private DescriptionService descriptionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDescriptionMockMvc;

    private Description description;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DescriptionResource descriptionResource = new DescriptionResource(descriptionService);
        this.restDescriptionMockMvc = MockMvcBuilders.standaloneSetup(descriptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Description createEntity(EntityManager em) {
        Description description = new Description()
            .parameter(DEFAULT_PARAMETER);
        return description;
    }

    @Before
    public void initTest() {
        description = createEntity(em);
    }

    @Test
    @Transactional
    public void createDescription() throws Exception {
        int databaseSizeBeforeCreate = descriptionRepository.findAll().size();

        // Create the Description
        DescriptionDTO descriptionDTO = descriptionMapper.toDto(description);
        restDescriptionMockMvc.perform(post("/api/descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the Description in the database
        List<Description> descriptionList = descriptionRepository.findAll();
        assertThat(descriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Description testDescription = descriptionList.get(descriptionList.size() - 1);
        assertThat(testDescription.getParameter()).isEqualTo(DEFAULT_PARAMETER);
    }

    @Test
    @Transactional
    public void createDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = descriptionRepository.findAll().size();

        // Create the Description with an existing ID
        description.setId(1L);
        DescriptionDTO descriptionDTO = descriptionMapper.toDto(description);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescriptionMockMvc.perform(post("/api/descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Description in the database
        List<Description> descriptionList = descriptionRepository.findAll();
        assertThat(descriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkParameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = descriptionRepository.findAll().size();
        // set the field null
        description.setParameter(null);

        // Create the Description, which fails.
        DescriptionDTO descriptionDTO = descriptionMapper.toDto(description);

        restDescriptionMockMvc.perform(post("/api/descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descriptionDTO)))
            .andExpect(status().isBadRequest());

        List<Description> descriptionList = descriptionRepository.findAll();
        assertThat(descriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDescriptions() throws Exception {
        // Initialize the database
        descriptionRepository.saveAndFlush(description);

        // Get all the descriptionList
        restDescriptionMockMvc.perform(get("/api/descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(description.getId().intValue())))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER.toString())));
    }
    
    @Test
    @Transactional
    public void getDescription() throws Exception {
        // Initialize the database
        descriptionRepository.saveAndFlush(description);

        // Get the description
        restDescriptionMockMvc.perform(get("/api/descriptions/{id}", description.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(description.getId().intValue()))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDescription() throws Exception {
        // Get the description
        restDescriptionMockMvc.perform(get("/api/descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDescription() throws Exception {
        // Initialize the database
        descriptionRepository.saveAndFlush(description);

        int databaseSizeBeforeUpdate = descriptionRepository.findAll().size();

        // Update the description
        Description updatedDescription = descriptionRepository.findById(description.getId()).get();
        // Disconnect from session so that the updates on updatedDescription are not directly saved in db
        em.detach(updatedDescription);
        updatedDescription
            .parameter(UPDATED_PARAMETER);
        DescriptionDTO descriptionDTO = descriptionMapper.toDto(updatedDescription);

        restDescriptionMockMvc.perform(put("/api/descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descriptionDTO)))
            .andExpect(status().isOk());

        // Validate the Description in the database
        List<Description> descriptionList = descriptionRepository.findAll();
        assertThat(descriptionList).hasSize(databaseSizeBeforeUpdate);
        Description testDescription = descriptionList.get(descriptionList.size() - 1);
        assertThat(testDescription.getParameter()).isEqualTo(UPDATED_PARAMETER);
    }

    @Test
    @Transactional
    public void updateNonExistingDescription() throws Exception {
        int databaseSizeBeforeUpdate = descriptionRepository.findAll().size();

        // Create the Description
        DescriptionDTO descriptionDTO = descriptionMapper.toDto(description);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescriptionMockMvc.perform(put("/api/descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Description in the database
        List<Description> descriptionList = descriptionRepository.findAll();
        assertThat(descriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDescription() throws Exception {
        // Initialize the database
        descriptionRepository.saveAndFlush(description);

        int databaseSizeBeforeDelete = descriptionRepository.findAll().size();

        // Delete the description
        restDescriptionMockMvc.perform(delete("/api/descriptions/{id}", description.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Description> descriptionList = descriptionRepository.findAll();
        assertThat(descriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Description.class);
        Description description1 = new Description();
        description1.setId(1L);
        Description description2 = new Description();
        description2.setId(description1.getId());
        assertThat(description1).isEqualTo(description2);
        description2.setId(2L);
        assertThat(description1).isNotEqualTo(description2);
        description1.setId(null);
        assertThat(description1).isNotEqualTo(description2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescriptionDTO.class);
        DescriptionDTO descriptionDTO1 = new DescriptionDTO();
        descriptionDTO1.setId(1L);
        DescriptionDTO descriptionDTO2 = new DescriptionDTO();
        assertThat(descriptionDTO1).isNotEqualTo(descriptionDTO2);
        descriptionDTO2.setId(descriptionDTO1.getId());
        assertThat(descriptionDTO1).isEqualTo(descriptionDTO2);
        descriptionDTO2.setId(2L);
        assertThat(descriptionDTO1).isNotEqualTo(descriptionDTO2);
        descriptionDTO1.setId(null);
        assertThat(descriptionDTO1).isNotEqualTo(descriptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(descriptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(descriptionMapper.fromId(null)).isNull();
    }
}
