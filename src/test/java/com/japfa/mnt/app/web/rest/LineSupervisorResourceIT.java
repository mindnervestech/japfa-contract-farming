package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.MobileCfApp;
import com.japfa.mnt.app.domain.LineSupervisor;
import com.japfa.mnt.app.repository.LineSupervisorRepository;
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
 * Integration tests for the {@link LineSupervisorResource} REST controller.
 */
@SpringBootTest(classes = MobileCfApp.class)
public class LineSupervisorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    @Autowired
    private LineSupervisorRepository lineSupervisorRepository;

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

    private MockMvc restLineSupervisorMockMvc;

    private LineSupervisor lineSupervisor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LineSupervisorResource lineSupervisorResource = new LineSupervisorResource(lineSupervisorRepository);
        this.restLineSupervisorMockMvc = MockMvcBuilders.standaloneSetup(lineSupervisorResource)
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
    public static LineSupervisor createEntity(EntityManager em) {
        LineSupervisor lineSupervisor = new LineSupervisor()
            .name(DEFAULT_NAME)
            .branchCode(DEFAULT_BRANCH_CODE);
        return lineSupervisor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineSupervisor createUpdatedEntity(EntityManager em) {
        LineSupervisor lineSupervisor = new LineSupervisor()
            .name(UPDATED_NAME)
            .branchCode(UPDATED_BRANCH_CODE);
        return lineSupervisor;
    }

    @BeforeEach
    public void initTest() {
        lineSupervisor = createEntity(em);
    }

    @Test
    @Transactional
    public void createLineSupervisor() throws Exception {
        int databaseSizeBeforeCreate = lineSupervisorRepository.findAll().size();

        // Create the LineSupervisor
        restLineSupervisorMockMvc.perform(post("/api/line-supervisors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineSupervisor)))
            .andExpect(status().isCreated());

        // Validate the LineSupervisor in the database
        List<LineSupervisor> lineSupervisorList = lineSupervisorRepository.findAll();
        assertThat(lineSupervisorList).hasSize(databaseSizeBeforeCreate + 1);
        LineSupervisor testLineSupervisor = lineSupervisorList.get(lineSupervisorList.size() - 1);
        assertThat(testLineSupervisor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLineSupervisor.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
    }

    @Test
    @Transactional
    public void createLineSupervisorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineSupervisorRepository.findAll().size();

        // Create the LineSupervisor with an existing ID
        lineSupervisor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineSupervisorMockMvc.perform(post("/api/line-supervisors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineSupervisor)))
            .andExpect(status().isBadRequest());

        // Validate the LineSupervisor in the database
        List<LineSupervisor> lineSupervisorList = lineSupervisorRepository.findAll();
        assertThat(lineSupervisorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLineSupervisors() throws Exception {
        // Initialize the database
        lineSupervisorRepository.saveAndFlush(lineSupervisor);

        // Get all the lineSupervisorList
        restLineSupervisorMockMvc.perform(get("/api/line-supervisors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineSupervisor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getLineSupervisor() throws Exception {
        // Initialize the database
        lineSupervisorRepository.saveAndFlush(lineSupervisor);

        // Get the lineSupervisor
        restLineSupervisorMockMvc.perform(get("/api/line-supervisors/{id}", lineSupervisor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lineSupervisor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLineSupervisor() throws Exception {
        // Get the lineSupervisor
        restLineSupervisorMockMvc.perform(get("/api/line-supervisors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineSupervisor() throws Exception {
        // Initialize the database
        lineSupervisorRepository.saveAndFlush(lineSupervisor);

        int databaseSizeBeforeUpdate = lineSupervisorRepository.findAll().size();

        // Update the lineSupervisor
        LineSupervisor updatedLineSupervisor = lineSupervisorRepository.findById(lineSupervisor.getId()).get();
        // Disconnect from session so that the updates on updatedLineSupervisor are not directly saved in db
        em.detach(updatedLineSupervisor);
        updatedLineSupervisor
            .name(UPDATED_NAME)
            .branchCode(UPDATED_BRANCH_CODE);

        restLineSupervisorMockMvc.perform(put("/api/line-supervisors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLineSupervisor)))
            .andExpect(status().isOk());

        // Validate the LineSupervisor in the database
        List<LineSupervisor> lineSupervisorList = lineSupervisorRepository.findAll();
        assertThat(lineSupervisorList).hasSize(databaseSizeBeforeUpdate);
        LineSupervisor testLineSupervisor = lineSupervisorList.get(lineSupervisorList.size() - 1);
        assertThat(testLineSupervisor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLineSupervisor.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingLineSupervisor() throws Exception {
        int databaseSizeBeforeUpdate = lineSupervisorRepository.findAll().size();

        // Create the LineSupervisor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineSupervisorMockMvc.perform(put("/api/line-supervisors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineSupervisor)))
            .andExpect(status().isBadRequest());

        // Validate the LineSupervisor in the database
        List<LineSupervisor> lineSupervisorList = lineSupervisorRepository.findAll();
        assertThat(lineSupervisorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLineSupervisor() throws Exception {
        // Initialize the database
        lineSupervisorRepository.saveAndFlush(lineSupervisor);

        int databaseSizeBeforeDelete = lineSupervisorRepository.findAll().size();

        // Delete the lineSupervisor
        restLineSupervisorMockMvc.perform(delete("/api/line-supervisors/{id}", lineSupervisor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LineSupervisor> lineSupervisorList = lineSupervisorRepository.findAll();
        assertThat(lineSupervisorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineSupervisor.class);
        LineSupervisor lineSupervisor1 = new LineSupervisor();
        lineSupervisor1.setId(1L);
        LineSupervisor lineSupervisor2 = new LineSupervisor();
        lineSupervisor2.setId(lineSupervisor1.getId());
        assertThat(lineSupervisor1).isEqualTo(lineSupervisor2);
        lineSupervisor2.setId(2L);
        assertThat(lineSupervisor1).isNotEqualTo(lineSupervisor2);
        lineSupervisor1.setId(null);
        assertThat(lineSupervisor1).isNotEqualTo(lineSupervisor2);
    }
}
