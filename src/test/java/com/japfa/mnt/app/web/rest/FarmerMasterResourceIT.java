package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.MobileCfApp;
import com.japfa.mnt.app.domain.FarmerMaster;
import com.japfa.mnt.app.repository.FarmerMasterRepository;
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
 * Integration tests for the {@link FarmerMasterResource} REST controller.
 */
@SpringBootTest(classes = MobileCfApp.class)
public class FarmerMasterResourceIT {

    private static final String DEFAULT_FARMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FLOCK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLOCK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_OF_FARMER = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_OF_FARMER = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_SUPERVISOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LINE_SUPERVISOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_SUPERVISOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_LINE_SUPERVISOR_ID = "BBBBBBBBBB";

    @Autowired
    private FarmerMasterRepository farmerMasterRepository;

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

    private MockMvc restFarmerMasterMockMvc;

    private FarmerMaster farmerMaster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FarmerMasterResource farmerMasterResource = new FarmerMasterResource(farmerMasterRepository);
        this.restFarmerMasterMockMvc = MockMvcBuilders.standaloneSetup(farmerMasterResource)
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
    public static FarmerMaster createEntity(EntityManager em) {
        FarmerMaster farmerMaster = new FarmerMaster()
            .farmerName(DEFAULT_FARMER_NAME)
            .farmerID(DEFAULT_FARMER_ID)
            .flockNumber(DEFAULT_FLOCK_NUMBER)
            .addressOfFarmer(DEFAULT_ADDRESS_OF_FARMER)
            .lineSupervisorName(DEFAULT_LINE_SUPERVISOR_NAME)
            .lineSupervisorID(DEFAULT_LINE_SUPERVISOR_ID);
        return farmerMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FarmerMaster createUpdatedEntity(EntityManager em) {
        FarmerMaster farmerMaster = new FarmerMaster()
            .farmerName(UPDATED_FARMER_NAME)
            .farmerID(UPDATED_FARMER_ID)
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .addressOfFarmer(UPDATED_ADDRESS_OF_FARMER)
            .lineSupervisorName(UPDATED_LINE_SUPERVISOR_NAME)
            .lineSupervisorID(UPDATED_LINE_SUPERVISOR_ID);
        return farmerMaster;
    }

    @BeforeEach
    public void initTest() {
        farmerMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createFarmerMaster() throws Exception {
        int databaseSizeBeforeCreate = farmerMasterRepository.findAll().size();

        // Create the FarmerMaster
        restFarmerMasterMockMvc.perform(post("/api/farmer-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmerMaster)))
            .andExpect(status().isCreated());

        // Validate the FarmerMaster in the database
        List<FarmerMaster> farmerMasterList = farmerMasterRepository.findAll();
        assertThat(farmerMasterList).hasSize(databaseSizeBeforeCreate + 1);
        FarmerMaster testFarmerMaster = farmerMasterList.get(farmerMasterList.size() - 1);
        assertThat(testFarmerMaster.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testFarmerMaster.getFarmerID()).isEqualTo(DEFAULT_FARMER_ID);
        assertThat(testFarmerMaster.getFlockNumber()).isEqualTo(DEFAULT_FLOCK_NUMBER);
        assertThat(testFarmerMaster.getAddressOfFarmer()).isEqualTo(DEFAULT_ADDRESS_OF_FARMER);
        assertThat(testFarmerMaster.getLineSupervisorName()).isEqualTo(DEFAULT_LINE_SUPERVISOR_NAME);
        assertThat(testFarmerMaster.getLineSupervisorID()).isEqualTo(DEFAULT_LINE_SUPERVISOR_ID);
    }

    @Test
    @Transactional
    public void createFarmerMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = farmerMasterRepository.findAll().size();

        // Create the FarmerMaster with an existing ID
        farmerMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmerMasterMockMvc.perform(post("/api/farmer-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmerMaster)))
            .andExpect(status().isBadRequest());

        // Validate the FarmerMaster in the database
        List<FarmerMaster> farmerMasterList = farmerMasterRepository.findAll();
        assertThat(farmerMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFarmerMasters() throws Exception {
        // Initialize the database
        farmerMasterRepository.saveAndFlush(farmerMaster);

        // Get all the farmerMasterList
        restFarmerMasterMockMvc.perform(get("/api/farmer-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farmerMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].farmerID").value(hasItem(DEFAULT_FARMER_ID.toString())))
            .andExpect(jsonPath("$.[*].flockNumber").value(hasItem(DEFAULT_FLOCK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].addressOfFarmer").value(hasItem(DEFAULT_ADDRESS_OF_FARMER.toString())))
            .andExpect(jsonPath("$.[*].lineSupervisorName").value(hasItem(DEFAULT_LINE_SUPERVISOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].lineSupervisorID").value(hasItem(DEFAULT_LINE_SUPERVISOR_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getFarmerMaster() throws Exception {
        // Initialize the database
        farmerMasterRepository.saveAndFlush(farmerMaster);

        // Get the farmerMaster
        restFarmerMasterMockMvc.perform(get("/api/farmer-masters/{id}", farmerMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(farmerMaster.getId().intValue()))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME.toString()))
            .andExpect(jsonPath("$.farmerID").value(DEFAULT_FARMER_ID.toString()))
            .andExpect(jsonPath("$.flockNumber").value(DEFAULT_FLOCK_NUMBER.toString()))
            .andExpect(jsonPath("$.addressOfFarmer").value(DEFAULT_ADDRESS_OF_FARMER.toString()))
            .andExpect(jsonPath("$.lineSupervisorName").value(DEFAULT_LINE_SUPERVISOR_NAME.toString()))
            .andExpect(jsonPath("$.lineSupervisorID").value(DEFAULT_LINE_SUPERVISOR_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFarmerMaster() throws Exception {
        // Get the farmerMaster
        restFarmerMasterMockMvc.perform(get("/api/farmer-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFarmerMaster() throws Exception {
        // Initialize the database
        farmerMasterRepository.saveAndFlush(farmerMaster);

        int databaseSizeBeforeUpdate = farmerMasterRepository.findAll().size();

        // Update the farmerMaster
        FarmerMaster updatedFarmerMaster = farmerMasterRepository.findById(farmerMaster.getId()).get();
        // Disconnect from session so that the updates on updatedFarmerMaster are not directly saved in db
        em.detach(updatedFarmerMaster);
        updatedFarmerMaster
            .farmerName(UPDATED_FARMER_NAME)
            .farmerID(UPDATED_FARMER_ID)
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .addressOfFarmer(UPDATED_ADDRESS_OF_FARMER)
            .lineSupervisorName(UPDATED_LINE_SUPERVISOR_NAME)
            .lineSupervisorID(UPDATED_LINE_SUPERVISOR_ID);

        restFarmerMasterMockMvc.perform(put("/api/farmer-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFarmerMaster)))
            .andExpect(status().isOk());

        // Validate the FarmerMaster in the database
        List<FarmerMaster> farmerMasterList = farmerMasterRepository.findAll();
        assertThat(farmerMasterList).hasSize(databaseSizeBeforeUpdate);
        FarmerMaster testFarmerMaster = farmerMasterList.get(farmerMasterList.size() - 1);
        assertThat(testFarmerMaster.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testFarmerMaster.getFarmerID()).isEqualTo(UPDATED_FARMER_ID);
        assertThat(testFarmerMaster.getFlockNumber()).isEqualTo(UPDATED_FLOCK_NUMBER);
        assertThat(testFarmerMaster.getAddressOfFarmer()).isEqualTo(UPDATED_ADDRESS_OF_FARMER);
        assertThat(testFarmerMaster.getLineSupervisorName()).isEqualTo(UPDATED_LINE_SUPERVISOR_NAME);
        assertThat(testFarmerMaster.getLineSupervisorID()).isEqualTo(UPDATED_LINE_SUPERVISOR_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingFarmerMaster() throws Exception {
        int databaseSizeBeforeUpdate = farmerMasterRepository.findAll().size();

        // Create the FarmerMaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmerMasterMockMvc.perform(put("/api/farmer-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmerMaster)))
            .andExpect(status().isBadRequest());

        // Validate the FarmerMaster in the database
        List<FarmerMaster> farmerMasterList = farmerMasterRepository.findAll();
        assertThat(farmerMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFarmerMaster() throws Exception {
        // Initialize the database
        farmerMasterRepository.saveAndFlush(farmerMaster);

        int databaseSizeBeforeDelete = farmerMasterRepository.findAll().size();

        // Delete the farmerMaster
        restFarmerMasterMockMvc.perform(delete("/api/farmer-masters/{id}", farmerMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FarmerMaster> farmerMasterList = farmerMasterRepository.findAll();
        assertThat(farmerMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FarmerMaster.class);
        FarmerMaster farmerMaster1 = new FarmerMaster();
        farmerMaster1.setId(1L);
        FarmerMaster farmerMaster2 = new FarmerMaster();
        farmerMaster2.setId(farmerMaster1.getId());
        assertThat(farmerMaster1).isEqualTo(farmerMaster2);
        farmerMaster2.setId(2L);
        assertThat(farmerMaster1).isNotEqualTo(farmerMaster2);
        farmerMaster1.setId(null);
        assertThat(farmerMaster1).isNotEqualTo(farmerMaster2);
    }
}
