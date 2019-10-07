package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.MobileCfApp;
import com.japfa.mnt.app.domain.PurchaseOrder;
import com.japfa.mnt.app.repository.PurchaseOrderRepository;
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
 * Integration tests for the {@link PurchaseOrderResource} REST controller.
 */
@SpringBootTest(classes = MobileCfApp.class)
public class PurchaseOrderResourceIT {

    private static final String DEFAULT_FARMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FLOCK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLOCK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_P_O_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_P_O_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    private static final String DEFAULT_SUPPLIER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSPOTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPOTER_NAME = "BBBBBBBBBB";

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

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

    private MockMvc restPurchaseOrderMockMvc;

    private PurchaseOrder purchaseOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrderResource purchaseOrderResource = new PurchaseOrderResource(purchaseOrderRepository);
        this.restPurchaseOrderMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderResource)
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
    public static PurchaseOrder createEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .farmerName(DEFAULT_FARMER_NAME)
            .flockNumber(DEFAULT_FLOCK_NUMBER)
            .pONumber(DEFAULT_P_O_NUMBER)
            .itemName(DEFAULT_ITEM_NAME)
            .itemID(DEFAULT_ITEM_ID)
            .quantity(DEFAULT_QUANTITY)
            .supplierName(DEFAULT_SUPPLIER_NAME)
            .transpoterName(DEFAULT_TRANSPOTER_NAME);
        return purchaseOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrder createUpdatedEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .farmerName(UPDATED_FARMER_NAME)
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .pONumber(UPDATED_P_O_NUMBER)
            .itemName(UPDATED_ITEM_NAME)
            .itemID(UPDATED_ITEM_ID)
            .quantity(UPDATED_QUANTITY)
            .supplierName(UPDATED_SUPPLIER_NAME)
            .transpoterName(UPDATED_TRANSPOTER_NAME);
        return purchaseOrder;
    }

    @BeforeEach
    public void initTest() {
        purchaseOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder
        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrder)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getFarmerName()).isEqualTo(DEFAULT_FARMER_NAME);
        assertThat(testPurchaseOrder.getFlockNumber()).isEqualTo(DEFAULT_FLOCK_NUMBER);
        assertThat(testPurchaseOrder.getpONumber()).isEqualTo(DEFAULT_P_O_NUMBER);
        assertThat(testPurchaseOrder.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testPurchaseOrder.getItemID()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testPurchaseOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchaseOrder.getSupplierName()).isEqualTo(DEFAULT_SUPPLIER_NAME);
        assertThat(testPurchaseOrder.getTranspoterName()).isEqualTo(DEFAULT_TRANSPOTER_NAME);
    }

    @Test
    @Transactional
    public void createPurchaseOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder with an existing ID
        purchaseOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrder)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].farmerName").value(hasItem(DEFAULT_FARMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].flockNumber").value(hasItem(DEFAULT_FLOCK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].pONumber").value(hasItem(DEFAULT_P_O_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemID").value(hasItem(DEFAULT_ITEM_ID.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME.toString())))
            .andExpect(jsonPath("$.[*].transpoterName").value(hasItem(DEFAULT_TRANSPOTER_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.farmerName").value(DEFAULT_FARMER_NAME.toString()))
            .andExpect(jsonPath("$.flockNumber").value(DEFAULT_FLOCK_NUMBER.toString()))
            .andExpect(jsonPath("$.pONumber").value(DEFAULT_P_O_NUMBER.toString()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.itemID").value(DEFAULT_ITEM_ID.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME.toString()))
            .andExpect(jsonPath("$.transpoterName").value(DEFAULT_TRANSPOTER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrder() throws Exception {
        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder
        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.findById(purchaseOrder.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrder are not directly saved in db
        em.detach(updatedPurchaseOrder);
        updatedPurchaseOrder
            .farmerName(UPDATED_FARMER_NAME)
            .flockNumber(UPDATED_FLOCK_NUMBER)
            .pONumber(UPDATED_P_O_NUMBER)
            .itemName(UPDATED_ITEM_NAME)
            .itemID(UPDATED_ITEM_ID)
            .quantity(UPDATED_QUANTITY)
            .supplierName(UPDATED_SUPPLIER_NAME)
            .transpoterName(UPDATED_TRANSPOTER_NAME);

        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseOrder)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getFarmerName()).isEqualTo(UPDATED_FARMER_NAME);
        assertThat(testPurchaseOrder.getFlockNumber()).isEqualTo(UPDATED_FLOCK_NUMBER);
        assertThat(testPurchaseOrder.getpONumber()).isEqualTo(UPDATED_P_O_NUMBER);
        assertThat(testPurchaseOrder.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testPurchaseOrder.getItemID()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testPurchaseOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchaseOrder.getSupplierName()).isEqualTo(UPDATED_SUPPLIER_NAME);
        assertThat(testPurchaseOrder.getTranspoterName()).isEqualTo(UPDATED_TRANSPOTER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrder)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        int databaseSizeBeforeDelete = purchaseOrderRepository.findAll().size();

        // Delete the purchaseOrder
        restPurchaseOrderMockMvc.perform(delete("/api/purchase-orders/{id}", purchaseOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrder.class);
        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setId(1L);
        PurchaseOrder purchaseOrder2 = new PurchaseOrder();
        purchaseOrder2.setId(purchaseOrder1.getId());
        assertThat(purchaseOrder1).isEqualTo(purchaseOrder2);
        purchaseOrder2.setId(2L);
        assertThat(purchaseOrder1).isNotEqualTo(purchaseOrder2);
        purchaseOrder1.setId(null);
        assertThat(purchaseOrder1).isNotEqualTo(purchaseOrder2);
    }
}
