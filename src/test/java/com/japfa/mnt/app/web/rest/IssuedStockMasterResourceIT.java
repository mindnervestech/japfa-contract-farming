package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.MobileCfApp;
import com.japfa.mnt.app.domain.IssuedStockMaster;
import com.japfa.mnt.app.repository.IssuedStockMasterRepository;
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
 * Integration tests for the {@link IssuedStockMasterResource} REST controller.
 */
@SpringBootTest(classes = MobileCfApp.class)
public class IssuedStockMasterResourceIT {

    private static final String DEFAULT_FLOCK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLOCK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STOCK_ISSUED = 1;
    private static final Integer UPDATED_STOCK_ISSUED = 2;
    private static final Integer SMALLER_STOCK_ISSUED = 1 - 1;

    @Autowired
    private IssuedStockMasterRepository issuedStockMasterRepository;

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

    private MockMvc restIssuedStockMasterMockMvc;

    private IssuedStockMaster issuedStockMaster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IssuedStockMasterResource issuedStockMasterResource = new IssuedStockMasterResource(issuedStockMasterRepository);
        this.restIssuedStockMasterMockMvc = MockMvcBuilders.standaloneSetup(issuedStockMasterResource)
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
    public static IssuedStockMaster createEntity(EntityManager em) {
        IssuedStockMaster issuedStockMaster = new IssuedStockMaster()
            .flockNumber(DEFAULT_FLOCK_NUMBER)
            .materialCode(DEFAULT_MATERIAL_CODE)
            .materialName(DEFAULT_MATERIAL_NAME)
            .stockIssued(DEFAULT_STOCK_ISSUED);
        return issuedStockMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssuedStockMaster createUpdatedEntity(EntityManager em) {
        IssuedStockMaster issuedStockMaster = new IssuedStockMaster()
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialName(UPDATED_MATERIAL_NAME)
            .stockIssued(UPDATED_STOCK_ISSUED);
        return issuedStockMaster;
    }

    @BeforeEach
    public void initTest() {
        issuedStockMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssuedStockMaster() throws Exception {
        int databaseSizeBeforeCreate = issuedStockMasterRepository.findAll().size();

        // Create the IssuedStockMaster
        restIssuedStockMasterMockMvc.perform(post("/api/issued-stock-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issuedStockMaster)))
            .andExpect(status().isCreated());

        // Validate the IssuedStockMaster in the database
        List<IssuedStockMaster> issuedStockMasterList = issuedStockMasterRepository.findAll();
        assertThat(issuedStockMasterList).hasSize(databaseSizeBeforeCreate + 1);
        IssuedStockMaster testIssuedStockMaster = issuedStockMasterList.get(issuedStockMasterList.size() - 1);
        assertThat(testIssuedStockMaster.getFlockNumber()).isEqualTo(DEFAULT_FLOCK_NUMBER);
        assertThat(testIssuedStockMaster.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testIssuedStockMaster.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testIssuedStockMaster.getStockIssued()).isEqualTo(DEFAULT_STOCK_ISSUED);
    }

    @Test
    @Transactional
    public void createIssuedStockMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issuedStockMasterRepository.findAll().size();

        // Create the IssuedStockMaster with an existing ID
        issuedStockMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssuedStockMasterMockMvc.perform(post("/api/issued-stock-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issuedStockMaster)))
            .andExpect(status().isBadRequest());

        // Validate the IssuedStockMaster in the database
        List<IssuedStockMaster> issuedStockMasterList = issuedStockMasterRepository.findAll();
        assertThat(issuedStockMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIssuedStockMasters() throws Exception {
        // Initialize the database
        issuedStockMasterRepository.saveAndFlush(issuedStockMaster);

        // Get all the issuedStockMasterList
        restIssuedStockMasterMockMvc.perform(get("/api/issued-stock-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issuedStockMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].flockNumber").value(hasItem(DEFAULT_FLOCK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].stockIssued").value(hasItem(DEFAULT_STOCK_ISSUED)));
    }
    
    @Test
    @Transactional
    public void getIssuedStockMaster() throws Exception {
        // Initialize the database
        issuedStockMasterRepository.saveAndFlush(issuedStockMaster);

        // Get the issuedStockMaster
        restIssuedStockMasterMockMvc.perform(get("/api/issued-stock-masters/{id}", issuedStockMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(issuedStockMaster.getId().intValue()))
            .andExpect(jsonPath("$.flockNumber").value(DEFAULT_FLOCK_NUMBER.toString()))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE.toString()))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME.toString()))
            .andExpect(jsonPath("$.stockIssued").value(DEFAULT_STOCK_ISSUED));
    }

    @Test
    @Transactional
    public void getNonExistingIssuedStockMaster() throws Exception {
        // Get the issuedStockMaster
        restIssuedStockMasterMockMvc.perform(get("/api/issued-stock-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssuedStockMaster() throws Exception {
        // Initialize the database
        issuedStockMasterRepository.saveAndFlush(issuedStockMaster);

        int databaseSizeBeforeUpdate = issuedStockMasterRepository.findAll().size();

        // Update the issuedStockMaster
        IssuedStockMaster updatedIssuedStockMaster = issuedStockMasterRepository.findById(issuedStockMaster.getId()).get();
        // Disconnect from session so that the updates on updatedIssuedStockMaster are not directly saved in db
        em.detach(updatedIssuedStockMaster);
        updatedIssuedStockMaster
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialName(UPDATED_MATERIAL_NAME)
            .stockIssued(UPDATED_STOCK_ISSUED);

        restIssuedStockMasterMockMvc.perform(put("/api/issued-stock-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIssuedStockMaster)))
            .andExpect(status().isOk());

        // Validate the IssuedStockMaster in the database
        List<IssuedStockMaster> issuedStockMasterList = issuedStockMasterRepository.findAll();
        assertThat(issuedStockMasterList).hasSize(databaseSizeBeforeUpdate);
        IssuedStockMaster testIssuedStockMaster = issuedStockMasterList.get(issuedStockMasterList.size() - 1);
        assertThat(testIssuedStockMaster.getFlockNumber()).isEqualTo(UPDATED_FLOCK_NUMBER);
        assertThat(testIssuedStockMaster.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testIssuedStockMaster.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testIssuedStockMaster.getStockIssued()).isEqualTo(UPDATED_STOCK_ISSUED);
    }

    @Test
    @Transactional
    public void updateNonExistingIssuedStockMaster() throws Exception {
        int databaseSizeBeforeUpdate = issuedStockMasterRepository.findAll().size();

        // Create the IssuedStockMaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssuedStockMasterMockMvc.perform(put("/api/issued-stock-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issuedStockMaster)))
            .andExpect(status().isBadRequest());

        // Validate the IssuedStockMaster in the database
        List<IssuedStockMaster> issuedStockMasterList = issuedStockMasterRepository.findAll();
        assertThat(issuedStockMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssuedStockMaster() throws Exception {
        // Initialize the database
        issuedStockMasterRepository.saveAndFlush(issuedStockMaster);

        int databaseSizeBeforeDelete = issuedStockMasterRepository.findAll().size();

        // Delete the issuedStockMaster
        restIssuedStockMasterMockMvc.perform(delete("/api/issued-stock-masters/{id}", issuedStockMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssuedStockMaster> issuedStockMasterList = issuedStockMasterRepository.findAll();
        assertThat(issuedStockMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssuedStockMaster.class);
        IssuedStockMaster issuedStockMaster1 = new IssuedStockMaster();
        issuedStockMaster1.setId(1L);
        IssuedStockMaster issuedStockMaster2 = new IssuedStockMaster();
        issuedStockMaster2.setId(issuedStockMaster1.getId());
        assertThat(issuedStockMaster1).isEqualTo(issuedStockMaster2);
        issuedStockMaster2.setId(2L);
        assertThat(issuedStockMaster1).isNotEqualTo(issuedStockMaster2);
        issuedStockMaster1.setId(null);
        assertThat(issuedStockMaster1).isNotEqualTo(issuedStockMaster2);
    }
}
