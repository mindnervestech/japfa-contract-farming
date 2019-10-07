package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.MobileCfApp;
import com.japfa.mnt.app.domain.DailyRecording;
import com.japfa.mnt.app.repository.DailyRecordingRepository;
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
 * Integration tests for the {@link DailyRecordingResource} REST controller.
 */
@SpringBootTest(classes = MobileCfApp.class)
public class DailyRecordingResourceIT {

    private static final String DEFAULT_FLOCK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLOCK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;
    private static final Integer SMALLER_CREATED_BY = 1 - 1;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_ON = LocalDate.ofEpochDay(-1L);

    @Autowired
    private DailyRecordingRepository dailyRecordingRepository;

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

    private MockMvc restDailyRecordingMockMvc;

    private DailyRecording dailyRecording;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DailyRecordingResource dailyRecordingResource = new DailyRecordingResource(dailyRecordingRepository);
        this.restDailyRecordingMockMvc = MockMvcBuilders.standaloneSetup(dailyRecordingResource)
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
    public static DailyRecording createEntity(EntityManager em) {
        DailyRecording dailyRecording = new DailyRecording()
            .flockNumber(DEFAULT_FLOCK_NUMBER)
            .itemCode(DEFAULT_ITEM_CODE)
            .quantity(DEFAULT_QUANTITY)
            .comment(DEFAULT_COMMENT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON);
        return dailyRecording;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyRecording createUpdatedEntity(EntityManager em) {
        DailyRecording dailyRecording = new DailyRecording()
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .itemCode(UPDATED_ITEM_CODE)
            .quantity(UPDATED_QUANTITY)
            .comment(UPDATED_COMMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);
        return dailyRecording;
    }

    @BeforeEach
    public void initTest() {
        dailyRecording = createEntity(em);
    }

    @Test
    @Transactional
    public void createDailyRecording() throws Exception {
        int databaseSizeBeforeCreate = dailyRecordingRepository.findAll().size();

        // Create the DailyRecording
        restDailyRecordingMockMvc.perform(post("/api/daily-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyRecording)))
            .andExpect(status().isCreated());

        // Validate the DailyRecording in the database
        List<DailyRecording> dailyRecordingList = dailyRecordingRepository.findAll();
        assertThat(dailyRecordingList).hasSize(databaseSizeBeforeCreate + 1);
        DailyRecording testDailyRecording = dailyRecordingList.get(dailyRecordingList.size() - 1);
        assertThat(testDailyRecording.getFlockNumber()).isEqualTo(DEFAULT_FLOCK_NUMBER);
        assertThat(testDailyRecording.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testDailyRecording.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDailyRecording.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testDailyRecording.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDailyRecording.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createDailyRecordingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dailyRecordingRepository.findAll().size();

        // Create the DailyRecording with an existing ID
        dailyRecording.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyRecordingMockMvc.perform(post("/api/daily-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyRecording)))
            .andExpect(status().isBadRequest());

        // Validate the DailyRecording in the database
        List<DailyRecording> dailyRecordingList = dailyRecordingRepository.findAll();
        assertThat(dailyRecordingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDailyRecordings() throws Exception {
        // Initialize the database
        dailyRecordingRepository.saveAndFlush(dailyRecording);

        // Get all the dailyRecordingList
        restDailyRecordingMockMvc.perform(get("/api/daily-recordings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyRecording.getId().intValue())))
            .andExpect(jsonPath("$.[*].flockNumber").value(hasItem(DEFAULT_FLOCK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getDailyRecording() throws Exception {
        // Initialize the database
        dailyRecordingRepository.saveAndFlush(dailyRecording);

        // Get the dailyRecording
        restDailyRecordingMockMvc.perform(get("/api/daily-recordings/{id}", dailyRecording.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dailyRecording.getId().intValue()))
            .andExpect(jsonPath("$.flockNumber").value(DEFAULT_FLOCK_NUMBER.toString()))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDailyRecording() throws Exception {
        // Get the dailyRecording
        restDailyRecordingMockMvc.perform(get("/api/daily-recordings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDailyRecording() throws Exception {
        // Initialize the database
        dailyRecordingRepository.saveAndFlush(dailyRecording);

        int databaseSizeBeforeUpdate = dailyRecordingRepository.findAll().size();

        // Update the dailyRecording
        DailyRecording updatedDailyRecording = dailyRecordingRepository.findById(dailyRecording.getId()).get();
        // Disconnect from session so that the updates on updatedDailyRecording are not directly saved in db
        em.detach(updatedDailyRecording);
        updatedDailyRecording
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .itemCode(UPDATED_ITEM_CODE)
            .quantity(UPDATED_QUANTITY)
            .comment(UPDATED_COMMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restDailyRecordingMockMvc.perform(put("/api/daily-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDailyRecording)))
            .andExpect(status().isOk());

        // Validate the DailyRecording in the database
        List<DailyRecording> dailyRecordingList = dailyRecordingRepository.findAll();
        assertThat(dailyRecordingList).hasSize(databaseSizeBeforeUpdate);
        DailyRecording testDailyRecording = dailyRecordingList.get(dailyRecordingList.size() - 1);
        assertThat(testDailyRecording.getFlockNumber()).isEqualTo(UPDATED_FLOCK_NUMBER);
        assertThat(testDailyRecording.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testDailyRecording.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDailyRecording.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testDailyRecording.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDailyRecording.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingDailyRecording() throws Exception {
        int databaseSizeBeforeUpdate = dailyRecordingRepository.findAll().size();

        // Create the DailyRecording

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyRecordingMockMvc.perform(put("/api/daily-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyRecording)))
            .andExpect(status().isBadRequest());

        // Validate the DailyRecording in the database
        List<DailyRecording> dailyRecordingList = dailyRecordingRepository.findAll();
        assertThat(dailyRecordingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDailyRecording() throws Exception {
        // Initialize the database
        dailyRecordingRepository.saveAndFlush(dailyRecording);

        int databaseSizeBeforeDelete = dailyRecordingRepository.findAll().size();

        // Delete the dailyRecording
        restDailyRecordingMockMvc.perform(delete("/api/daily-recordings/{id}", dailyRecording.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyRecording> dailyRecordingList = dailyRecordingRepository.findAll();
        assertThat(dailyRecordingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyRecording.class);
        DailyRecording dailyRecording1 = new DailyRecording();
        dailyRecording1.setId(1L);
        DailyRecording dailyRecording2 = new DailyRecording();
        dailyRecording2.setId(dailyRecording1.getId());
        assertThat(dailyRecording1).isEqualTo(dailyRecording2);
        dailyRecording2.setId(2L);
        assertThat(dailyRecording1).isNotEqualTo(dailyRecording2);
        dailyRecording1.setId(null);
        assertThat(dailyRecording1).isNotEqualTo(dailyRecording2);
    }
}
