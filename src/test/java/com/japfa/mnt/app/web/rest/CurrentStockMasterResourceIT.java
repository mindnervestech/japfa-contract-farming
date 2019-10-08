package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.MobileCfApp;
import com.japfa.mnt.app.domain.CurrentStockMaster;
import com.japfa.mnt.app.repository.CurrentStockMasterRepository;
import com.japfa.mnt.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.japfa.mnt.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CurrentStockMasterResource} REST controller.
 */
@SpringBootTest(classes = MobileCfApp.class)
public class CurrentStockMasterResourceIT {

    private static final String DEFAULT_FLOCK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLOCK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STOCK_IN_HAND = 1;
    private static final Integer UPDATED_STOCK_IN_HAND = 2;
    private static final Integer SMALLER_STOCK_IN_HAND = 1 - 1;

    @Autowired
    private CurrentStockMasterRepository currentStockMasterRepository;

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

    private MockMvc restCurrentStockMasterMockMvc;

    private CurrentStockMaster currentStockMaster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrentStockMasterResource currentStockMasterResource = new CurrentStockMasterResource(currentStockMasterRepository);
        this.restCurrentStockMasterMockMvc = MockMvcBuilders.standaloneSetup(currentStockMasterResource)
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
    public static CurrentStockMaster createEntity(EntityManager em) {
        CurrentStockMaster currentStockMaster = new CurrentStockMaster()
            .flockNumber(DEFAULT_FLOCK_NUMBER)
            .materialCode(DEFAULT_MATERIAL_CODE)
            .materialName(DEFAULT_MATERIAL_NAME)
            .stockInHand(DEFAULT_STOCK_IN_HAND);
        return currentStockMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentStockMaster createUpdatedEntity(EntityManager em) {
        CurrentStockMaster currentStockMaster = new CurrentStockMaster()
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialName(UPDATED_MATERIAL_NAME)
            .stockInHand(UPDATED_STOCK_IN_HAND);
        return currentStockMaster;
    }

    @BeforeEach
    public void initTest() {
        currentStockMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrentStockMaster() throws Exception {
        int databaseSizeBeforeCreate = currentStockMasterRepository.findAll().size();

        // Create the CurrentStockMaster
        restCurrentStockMasterMockMvc.perform(post("/api/current-stock-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentStockMaster)))
            .andExpect(status().isCreated());

        // Validate the CurrentStockMaster in the database
        List<CurrentStockMaster> currentStockMasterList = currentStockMasterRepository.findAll();
        assertThat(currentStockMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CurrentStockMaster testCurrentStockMaster = currentStockMasterList.get(currentStockMasterList.size() - 1);
        assertThat(testCurrentStockMaster.getFlockNumber()).isEqualTo(DEFAULT_FLOCK_NUMBER);
        assertThat(testCurrentStockMaster.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testCurrentStockMaster.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testCurrentStockMaster.getStockInHand()).isEqualTo(DEFAULT_STOCK_IN_HAND);
    }

    @Test
    @Transactional
    public void createCurrentStockMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currentStockMasterRepository.findAll().size();

        // Create the CurrentStockMaster with an existing ID
        currentStockMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrentStockMasterMockMvc.perform(post("/api/current-stock-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentStockMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CurrentStockMaster in the database
        List<CurrentStockMaster> currentStockMasterList = currentStockMasterRepository.findAll();
        assertThat(currentStockMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCurrentStockMasters() throws Exception {
        // Initialize the database
        currentStockMasterRepository.saveAndFlush(currentStockMaster);

        // Get all the currentStockMasterList
        restCurrentStockMasterMockMvc.perform(get("/api/current-stock-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currentStockMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].flockNumber").value(hasItem(DEFAULT_FLOCK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].stockInHand").value(hasItem(DEFAULT_STOCK_IN_HAND)));
    }
    
    @Test
    @Transactional
    public void getCurrentStockMaster() throws Exception {
        // Initialize the database
        currentStockMasterRepository.saveAndFlush(currentStockMaster);

        // Get the currentStockMaster
        restCurrentStockMasterMockMvc.perform(get("/api/current-stock-masters/{id}", currentStockMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currentStockMaster.getId().intValue()))
            .andExpect(jsonPath("$.flockNumber").value(DEFAULT_FLOCK_NUMBER.toString()))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE.toString()))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME.toString()))
            .andExpect(jsonPath("$.stockInHand").value(DEFAULT_STOCK_IN_HAND));
    }

    @Test
    @Transactional
    public void getNonExistingCurrentStockMaster() throws Exception {
        // Get the currentStockMaster
        restCurrentStockMasterMockMvc.perform(get("/api/current-stock-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrentStockMaster() throws Exception {
        // Initialize the database
        currentStockMasterRepository.saveAndFlush(currentStockMaster);

        int databaseSizeBeforeUpdate = currentStockMasterRepository.findAll().size();

        // Update the currentStockMaster
        CurrentStockMaster updatedCurrentStockMaster = currentStockMasterRepository.findById(currentStockMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCurrentStockMaster are not directly saved in db
        em.detach(updatedCurrentStockMaster);
        updatedCurrentStockMaster
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialName(UPDATED_MATERIAL_NAME)
            .stockInHand(UPDATED_STOCK_IN_HAND);

        restCurrentStockMasterMockMvc.perform(put("/api/current-stock-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCurrentStockMaster)))
            .andExpect(status().isOk());

        // Validate the CurrentStockMaster in the database
        List<CurrentStockMaster> currentStockMasterList = currentStockMasterRepository.findAll();
        assertThat(currentStockMasterList).hasSize(databaseSizeBeforeUpdate);
        CurrentStockMaster testCurrentStockMaster = currentStockMasterList.get(currentStockMasterList.size() - 1);
        assertThat(testCurrentStockMaster.getFlockNumber()).isEqualTo(UPDATED_FLOCK_NUMBER);
        assertThat(testCurrentStockMaster.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testCurrentStockMaster.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testCurrentStockMaster.getStockInHand()).isEqualTo(UPDATED_STOCK_IN_HAND);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrentStockMaster() throws Exception {
        int databaseSizeBeforeUpdate = currentStockMasterRepository.findAll().size();

        // Create the CurrentStockMaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentStockMasterMockMvc.perform(put("/api/current-stock-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentStockMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CurrentStockMaster in the database
        List<CurrentStockMaster> currentStockMasterList = currentStockMasterRepository.findAll();
        assertThat(currentStockMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrentStockMaster() throws Exception {
        // Initialize the database
        currentStockMasterRepository.saveAndFlush(currentStockMaster);

        int databaseSizeBeforeDelete = currentStockMasterRepository.findAll().size();

        // Delete the currentStockMaster
        restCurrentStockMasterMockMvc.perform(delete("/api/current-stock-masters/{id}", currentStockMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrentStockMaster> currentStockMasterList = currentStockMasterRepository.findAll();
        assertThat(currentStockMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentStockMaster.class);
        CurrentStockMaster currentStockMaster1 = new CurrentStockMaster();
        currentStockMaster1.setId(1L);
        CurrentStockMaster currentStockMaster2 = new CurrentStockMaster();
        currentStockMaster2.setId(currentStockMaster1.getId());
        assertThat(currentStockMaster1).isEqualTo(currentStockMaster2);
        currentStockMaster2.setId(2L);
        assertThat(currentStockMaster1).isNotEqualTo(currentStockMaster2);
        currentStockMaster1.setId(null);
        assertThat(currentStockMaster1).isNotEqualTo(currentStockMaster2);
    }
}
