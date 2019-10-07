package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.MobileCfApp;
import com.japfa.mnt.app.domain.SAPMaster;
import com.japfa.mnt.app.repository.SAPMasterRepository;
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
 * Integration tests for the {@link SAPMasterResource} REST controller.
 */
@SpringBootTest(classes = MobileCfApp.class)
public class SAPMasterResourceIT {

    private static final String DEFAULT_FARMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FARMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FLOCK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLOCK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_OF_FARMER = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_OF_FARMER = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_P_O_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_P_O_NUMBER = "BBBBBBBBBB";

    @Autowired
    private SAPMasterRepository sAPMasterRepository;

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

    private MockMvc restSAPMasterMockMvc;

    private SAPMaster sAPMaster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SAPMasterResource sAPMasterResource = new SAPMasterResource(sAPMasterRepository);
        this.restSAPMasterMockMvc = MockMvcBuilders.standaloneSetup(sAPMasterResource)
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
    public static SAPMaster createEntity(EntityManager em) {
        SAPMaster sAPMaster = new SAPMaster()
            .farmerName(DEFAULT_FARMER_NAME)
            .farmerID(DEFAULT_FARMER_ID)
            .branchCode(DEFAULT_BRANCH_CODE)
            .flockNumber(DEFAULT_FLOCK_NUMBER)
            .addressOfFarmer(DEFAULT_ADDRESS_OF_FARMER)
            .itemCode(DEFAULT_ITEM_CODE)
            .quantity(DEFAULT_QUANTITY)
            .pONumber(DEFAULT_P_O_NUMBER);
        return sAPMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SAPMaster createUpdatedEntity(EntityManager em) {
        SAPMaster sAPMaster = new SAPMaster()
            .farmerName(UPDATED_FARMER_NAME)
            .farmerID(UPDATED_FARMER_ID)
            .branchCode(UPDATED_BRANCH_CODE)
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .addressOfFarmer(UPDATED_ADDRESS_OF_FARMER)
            .itemCode(UPDATED_ITEM_CODE)
            .quantity(UPDATED_QUANTITY)
            .pONumber(UPDATED_P_O_NUMBER);
        return sAPMaster;
    }

    @BeforeEach
    public void initTest() {
        sAPMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createSAPMaster() throws Exception {
        int databaseSizeBeforeCreate = sAPMasterRepository.findAll().size();

        // Create the SAPMaster
        restSAPMasterMockMvc.perform(post("/api/sap-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sAPMaster)))
            .andExpect(status().isCreated());

        // Validate the SAPMaster in the database
        List<SAPMaster> sAPMasterList = sAPMasterRepository.findAll();
        assertThat(sAPMasterList).hasSize(databaseSizeBeforeCreate + 1);
        SAPMaster testSAPMaster = sAPMasterList.get(sAPMasterList.size() - 1);
        assertThat(testSAPMaster.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testSAPMaster.getFarmerID()).isEqualTo(DEFAULT_FARMER_ID);
        assertThat(testSAPMaster.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testSAPMaster.getFlockNumber()).isEqualTo(DEFAULT_FLOCK_NUMBER);
        assertThat(testSAPMaster.getAddressOfFarmer()).isEqualTo(DEFAULT_ADDRESS_OF_FARMER);
        assertThat(testSAPMaster.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testSAPMaster.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSAPMaster.getpONumber()).isEqualTo(DEFAULT_P_O_NUMBER);
    }

    @Test
    @Transactional
    public void createSAPMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sAPMasterRepository.findAll().size();

        // Create the SAPMaster with an existing ID
        sAPMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSAPMasterMockMvc.perform(post("/api/sap-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sAPMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SAPMaster in the database
        List<SAPMaster> sAPMasterList = sAPMasterRepository.findAll();
        assertThat(sAPMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSAPMasters() throws Exception {
        // Initialize the database
        sAPMasterRepository.saveAndFlush(sAPMaster);

        // Get all the sAPMasterList
        restSAPMasterMockMvc.perform(get("/api/sap-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sAPMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].farmerID").value(hasItem(DEFAULT_FARMER_ID.toString())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE.toString())))
            .andExpect(jsonPath("$.[*].flockNumber").value(hasItem(DEFAULT_FLOCK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].addressOfFarmer").value(hasItem(DEFAULT_ADDRESS_OF_FARMER.toString())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.toString())))
            .andExpect(jsonPath("$.[*].pONumber").value(hasItem(DEFAULT_P_O_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getSAPMaster() throws Exception {
        // Initialize the database
        sAPMasterRepository.saveAndFlush(sAPMaster);

        // Get the sAPMaster
        restSAPMasterMockMvc.perform(get("/api/sap-masters/{id}", sAPMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sAPMaster.getId().intValue()))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME.toString()))
            .andExpect(jsonPath("$.farmerID").value(DEFAULT_FARMER_ID.toString()))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE.toString()))
            .andExpect(jsonPath("$.flockNumber").value(DEFAULT_FLOCK_NUMBER.toString()))
            .andExpect(jsonPath("$.addressOfFarmer").value(DEFAULT_ADDRESS_OF_FARMER.toString()))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.toString()))
            .andExpect(jsonPath("$.pONumber").value(DEFAULT_P_O_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSAPMaster() throws Exception {
        // Get the sAPMaster
        restSAPMasterMockMvc.perform(get("/api/sap-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSAPMaster() throws Exception {
        // Initialize the database
        sAPMasterRepository.saveAndFlush(sAPMaster);

        int databaseSizeBeforeUpdate = sAPMasterRepository.findAll().size();

        // Update the sAPMaster
        SAPMaster updatedSAPMaster = sAPMasterRepository.findById(sAPMaster.getId()).get();
        // Disconnect from session so that the updates on updatedSAPMaster are not directly saved in db
        em.detach(updatedSAPMaster);
        updatedSAPMaster
            .farmerName(UPDATED_FARMER_NAME)
            .farmerID(UPDATED_FARMER_ID)
            .branchCode(UPDATED_BRANCH_CODE)
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .addressOfFarmer(UPDATED_ADDRESS_OF_FARMER)
            .itemCode(UPDATED_ITEM_CODE)
            .quantity(UPDATED_QUANTITY)
            .pONumber(UPDATED_P_O_NUMBER);

        restSAPMasterMockMvc.perform(put("/api/sap-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSAPMaster)))
            .andExpect(status().isOk());

        // Validate the SAPMaster in the database
        List<SAPMaster> sAPMasterList = sAPMasterRepository.findAll();
        assertThat(sAPMasterList).hasSize(databaseSizeBeforeUpdate);
        SAPMaster testSAPMaster = sAPMasterList.get(sAPMasterList.size() - 1);
        assertThat(testSAPMaster.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testSAPMaster.getFarmerID()).isEqualTo(UPDATED_FARMER_ID);
        assertThat(testSAPMaster.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testSAPMaster.getFlockNumber()).isEqualTo(UPDATED_FLOCK_NUMBER);
        assertThat(testSAPMaster.getAddressOfFarmer()).isEqualTo(UPDATED_ADDRESS_OF_FARMER);
        assertThat(testSAPMaster.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testSAPMaster.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSAPMaster.getpONumber()).isEqualTo(UPDATED_P_O_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingSAPMaster() throws Exception {
        int databaseSizeBeforeUpdate = sAPMasterRepository.findAll().size();

        // Create the SAPMaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSAPMasterMockMvc.perform(put("/api/sap-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sAPMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SAPMaster in the database
        List<SAPMaster> sAPMasterList = sAPMasterRepository.findAll();
        assertThat(sAPMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSAPMaster() throws Exception {
        // Initialize the database
        sAPMasterRepository.saveAndFlush(sAPMaster);

        int databaseSizeBeforeDelete = sAPMasterRepository.findAll().size();

        // Delete the sAPMaster
        restSAPMasterMockMvc.perform(delete("/api/sap-masters/{id}", sAPMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SAPMaster> sAPMasterList = sAPMasterRepository.findAll();
        assertThat(sAPMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SAPMaster.class);
        SAPMaster sAPMaster1 = new SAPMaster();
        sAPMaster1.setId(1L);
        SAPMaster sAPMaster2 = new SAPMaster();
        sAPMaster2.setId(sAPMaster1.getId());
        assertThat(sAPMaster1).isEqualTo(sAPMaster2);
        sAPMaster2.setId(2L);
        assertThat(sAPMaster1).isNotEqualTo(sAPMaster2);
        sAPMaster1.setId(null);
        assertThat(sAPMaster1).isNotEqualTo(sAPMaster2);
    }
}
