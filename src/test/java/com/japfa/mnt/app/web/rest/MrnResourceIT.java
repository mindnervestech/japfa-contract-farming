package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.MobileCfApp;
import com.japfa.mnt.app.domain.Mrn;
import com.japfa.mnt.app.repository.MrnRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.japfa.mnt.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MrnResource} REST controller.
 */
@SpringBootTest(classes = MobileCfApp.class)
public class MrnResourceIT {

    private static final String DEFAULT_VEHICLE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_D_C_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_D_C_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_D_C_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_D_C_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_D_C_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_POSTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_POSTING_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_P_O_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_P_O_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AVG_WEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_AVG_WEIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;
    private static final Integer SMALLER_CREATED_BY = 1 - 1;

    private static final Integer DEFAULT_ITEM_RECIEVED = 1;
    private static final Integer UPDATED_ITEM_RECIEVED = 2;
    private static final Integer SMALLER_ITEM_RECIEVED = 1 - 1;

    private static final String DEFAULT_FLOCK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLOCK_NUMBER = "BBBBBBBBBB";

    @Autowired
    private MrnRepository mrnRepository;

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

    private MockMvc restMrnMockMvc;

    private Mrn mrn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MrnResource mrnResource = new MrnResource(mrnRepository);
        this.restMrnMockMvc = MockMvcBuilders.standaloneSetup(mrnResource)
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
    public static Mrn createEntity(EntityManager em) {
        Mrn mrn = new Mrn()
            .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
            .dCDate(DEFAULT_D_C_DATE)
            .dCNumber(DEFAULT_D_C_NUMBER)
            .postingDate(DEFAULT_POSTING_DATE)
            .pONumber(DEFAULT_P_O_NUMBER)
            .itemNumber(DEFAULT_ITEM_NUMBER)
            .avgWeight(DEFAULT_AVG_WEIGHT)
            .condition(DEFAULT_CONDITION)
            .createdBy(DEFAULT_CREATED_BY)
            .itemRecieved(DEFAULT_ITEM_RECIEVED)
            .flockNumber(DEFAULT_FLOCK_NUMBER);
        return mrn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mrn createUpdatedEntity(EntityManager em) {
        Mrn mrn = new Mrn()
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .dCDate(UPDATED_D_C_DATE)
            .dCNumber(UPDATED_D_C_NUMBER)
            .postingDate(UPDATED_POSTING_DATE)
            .pONumber(UPDATED_P_O_NUMBER)
            .itemNumber(UPDATED_ITEM_NUMBER)
            .avgWeight(UPDATED_AVG_WEIGHT)
            .condition(UPDATED_CONDITION)
            .createdBy(UPDATED_CREATED_BY)
            .itemRecieved(UPDATED_ITEM_RECIEVED)
            .flockNumber(UPDATED_FLOCK_NUMBER);
        return mrn;
    }

    @BeforeEach
    public void initTest() {
        mrn = createEntity(em);
    }

    @Test
    @Transactional
    public void createMrn() throws Exception {
        int databaseSizeBeforeCreate = mrnRepository.findAll().size();

        // Create the Mrn
        restMrnMockMvc.perform(post("/api/mrns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mrn)))
            .andExpect(status().isCreated());

        // Validate the Mrn in the database
        List<Mrn> mrnList = mrnRepository.findAll();
        assertThat(mrnList).hasSize(databaseSizeBeforeCreate + 1);
        Mrn testMrn = mrnList.get(mrnList.size() - 1);
        assertThat(testMrn.getVehicleNumber()).isEqualTo(DEFAULT_VEHICLE_NUMBER);
        assertThat(testMrn.getdCDate()).isEqualTo(DEFAULT_D_C_DATE);
        assertThat(testMrn.getdCNumber()).isEqualTo(DEFAULT_D_C_NUMBER);
        assertThat(testMrn.getPostingDate()).isEqualTo(DEFAULT_POSTING_DATE);
        assertThat(testMrn.getpONumber()).isEqualTo(DEFAULT_P_O_NUMBER);
        assertThat(testMrn.getItemNumber()).isEqualTo(DEFAULT_ITEM_NUMBER);
        assertThat(testMrn.getAvgWeight()).isEqualTo(DEFAULT_AVG_WEIGHT);
        assertThat(testMrn.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testMrn.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMrn.getItemRecieved()).isEqualTo(DEFAULT_ITEM_RECIEVED);
        assertThat(testMrn.getFlockNumber()).isEqualTo(DEFAULT_FLOCK_NUMBER);
    }

    @Test
    @Transactional
    public void createMrnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mrnRepository.findAll().size();

        // Create the Mrn with an existing ID
        mrn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMrnMockMvc.perform(post("/api/mrns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mrn)))
            .andExpect(status().isBadRequest());

        // Validate the Mrn in the database
        List<Mrn> mrnList = mrnRepository.findAll();
        assertThat(mrnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMrns() throws Exception {
        // Initialize the database
        mrnRepository.saveAndFlush(mrn);

        // Get all the mrnList
        restMrnMockMvc.perform(get("/api/mrns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mrn.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dCDate").value(hasItem(DEFAULT_D_C_DATE.toString())))
            .andExpect(jsonPath("$.[*].dCNumber").value(hasItem(DEFAULT_D_C_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].postingDate").value(hasItem(DEFAULT_POSTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].pONumber").value(hasItem(DEFAULT_P_O_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].itemNumber").value(hasItem(DEFAULT_ITEM_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].avgWeight").value(hasItem(DEFAULT_AVG_WEIGHT.toString())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].itemRecieved").value(hasItem(DEFAULT_ITEM_RECIEVED)))
            .andExpect(jsonPath("$.[*].flockNumber").value(hasItem(DEFAULT_FLOCK_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getMrn() throws Exception {
        // Initialize the database
        mrnRepository.saveAndFlush(mrn);

        // Get the mrn
        restMrnMockMvc.perform(get("/api/mrns/{id}", mrn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mrn.getId().intValue()))
            .andExpect(jsonPath("$.vehicleNumber").value(DEFAULT_VEHICLE_NUMBER.toString()))
            .andExpect(jsonPath("$.dCDate").value(DEFAULT_D_C_DATE.toString()))
            .andExpect(jsonPath("$.dCNumber").value(DEFAULT_D_C_NUMBER.toString()))
            .andExpect(jsonPath("$.postingDate").value(DEFAULT_POSTING_DATE.toString()))
            .andExpect(jsonPath("$.pONumber").value(DEFAULT_P_O_NUMBER.toString()))
            .andExpect(jsonPath("$.itemNumber").value(DEFAULT_ITEM_NUMBER.toString()))
            .andExpect(jsonPath("$.avgWeight").value(DEFAULT_AVG_WEIGHT.toString()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.itemRecieved").value(DEFAULT_ITEM_RECIEVED))
            .andExpect(jsonPath("$.flockNumber").value(DEFAULT_FLOCK_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMrn() throws Exception {
        // Get the mrn
        restMrnMockMvc.perform(get("/api/mrns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMrn() throws Exception {
        // Initialize the database
        mrnRepository.saveAndFlush(mrn);

        int databaseSizeBeforeUpdate = mrnRepository.findAll().size();

        // Update the mrn
        Mrn updatedMrn = mrnRepository.findById(mrn.getId()).get();
        // Disconnect from session so that the updates on updatedMrn are not directly saved in db
        em.detach(updatedMrn);
        updatedMrn
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .dCDate(UPDATED_D_C_DATE)
            .dCNumber(UPDATED_D_C_NUMBER)
            .postingDate(UPDATED_POSTING_DATE)
            .pONumber(UPDATED_P_O_NUMBER)
            .itemNumber(UPDATED_ITEM_NUMBER)
            .avgWeight(UPDATED_AVG_WEIGHT)
            .condition(UPDATED_CONDITION)
            .createdBy(UPDATED_CREATED_BY)
            .itemRecieved(UPDATED_ITEM_RECIEVED)
            .flockNumber(UPDATED_FLOCK_NUMBER);

        restMrnMockMvc.perform(put("/api/mrns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMrn)))
            .andExpect(status().isOk());

        // Validate the Mrn in the database
        List<Mrn> mrnList = mrnRepository.findAll();
        assertThat(mrnList).hasSize(databaseSizeBeforeUpdate);
        Mrn testMrn = mrnList.get(mrnList.size() - 1);
        assertThat(testMrn.getVehicleNumber()).isEqualTo(UPDATED_VEHICLE_NUMBER);
        assertThat(testMrn.getdCDate()).isEqualTo(UPDATED_D_C_DATE);
        assertThat(testMrn.getdCNumber()).isEqualTo(UPDATED_D_C_NUMBER);
        assertThat(testMrn.getPostingDate()).isEqualTo(UPDATED_POSTING_DATE);
        assertThat(testMrn.getpONumber()).isEqualTo(UPDATED_P_O_NUMBER);
        assertThat(testMrn.getItemNumber()).isEqualTo(UPDATED_ITEM_NUMBER);
        assertThat(testMrn.getAvgWeight()).isEqualTo(UPDATED_AVG_WEIGHT);
        assertThat(testMrn.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testMrn.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMrn.getItemRecieved()).isEqualTo(UPDATED_ITEM_RECIEVED);
        assertThat(testMrn.getFlockNumber()).isEqualTo(UPDATED_FLOCK_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingMrn() throws Exception {
        int databaseSizeBeforeUpdate = mrnRepository.findAll().size();

        // Create the Mrn

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMrnMockMvc.perform(put("/api/mrns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mrn)))
            .andExpect(status().isBadRequest());

        // Validate the Mrn in the database
        List<Mrn> mrnList = mrnRepository.findAll();
        assertThat(mrnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMrn() throws Exception {
        // Initialize the database
        mrnRepository.saveAndFlush(mrn);

        int databaseSizeBeforeDelete = mrnRepository.findAll().size();

        // Delete the mrn
        restMrnMockMvc.perform(delete("/api/mrns/{id}", mrn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mrn> mrnList = mrnRepository.findAll();
        assertThat(mrnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mrn.class);
        Mrn mrn1 = new Mrn();
        mrn1.setId(1L);
        Mrn mrn2 = new Mrn();
        mrn2.setId(mrn1.getId());
        assertThat(mrn1).isEqualTo(mrn2);
        mrn2.setId(2L);
        assertThat(mrn1).isNotEqualTo(mrn2);
        mrn1.setId(null);
        assertThat(mrn1).isNotEqualTo(mrn2);
    }
}
