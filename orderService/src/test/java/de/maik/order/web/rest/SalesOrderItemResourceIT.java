package de.maik.order.web.rest;

import de.maik.order.OrderServiceApp;
import de.maik.order.config.TestSecurityConfiguration;
import de.maik.order.domain.SalesOrderItem;
import de.maik.order.repository.SalesOrderItemRepository;
import de.maik.order.repository.search.SalesOrderItemSearchRepository;
import de.maik.order.service.SalesOrderItemService;
import de.maik.order.service.dto.SalesOrderItemDTO;
import de.maik.order.service.mapper.SalesOrderItemMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.maik.order.domain.enumeration.SalesOrderItemStatus;
/**
 * Integration tests for the {@link SalesOrderItemResource} REST controller.
 */
@SpringBootTest(classes = { OrderServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalesOrderItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TAXABLE = false;
    private static final Boolean UPDATED_TAXABLE = true;

    private static final Double DEFAULT_GROS_WEIGHT = 1D;
    private static final Double UPDATED_GROS_WEIGHT = 2D;

    private static final LocalDate DEFAULT_SHIPPED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHIPPED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELIVERED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERED = LocalDate.now(ZoneId.systemDefault());

    private static final SalesOrderItemStatus DEFAULT_STATUS = SalesOrderItemStatus.PENDING;
    private static final SalesOrderItemStatus UPDATED_STATUS = SalesOrderItemStatus.DELIVERED;

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Autowired
    private SalesOrderItemRepository salesOrderItemRepository;

    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    @Autowired
    private SalesOrderItemService salesOrderItemService;

    /**
     * This repository is mocked in the de.maik.order.repository.search test package.
     *
     * @see de.maik.order.repository.search.SalesOrderItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private SalesOrderItemSearchRepository mockSalesOrderItemSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesOrderItemMockMvc;

    private SalesOrderItem salesOrderItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrderItem createEntity(EntityManager em) {
        SalesOrderItem salesOrderItem = new SalesOrderItem()
            .name(DEFAULT_NAME)
            .sku(DEFAULT_SKU)
            .taxable(DEFAULT_TAXABLE)
            .grosWeight(DEFAULT_GROS_WEIGHT)
            .shipped(DEFAULT_SHIPPED)
            .delivered(DEFAULT_DELIVERED)
            .status(DEFAULT_STATUS)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .amount(DEFAULT_AMOUNT);
        return salesOrderItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrderItem createUpdatedEntity(EntityManager em) {
        SalesOrderItem salesOrderItem = new SalesOrderItem()
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .taxable(UPDATED_TAXABLE)
            .grosWeight(UPDATED_GROS_WEIGHT)
            .shipped(UPDATED_SHIPPED)
            .delivered(UPDATED_DELIVERED)
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT);
        return salesOrderItem;
    }

    @BeforeEach
    public void initTest() {
        salesOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrderItem() throws Exception {
        int databaseSizeBeforeCreate = salesOrderItemRepository.findAll().size();
        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);
        restSalesOrderItemMockMvc.perform(post("/api/sales-order-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrderItem testSalesOrderItem = salesOrderItemList.get(salesOrderItemList.size() - 1);
        assertThat(testSalesOrderItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSalesOrderItem.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testSalesOrderItem.isTaxable()).isEqualTo(DEFAULT_TAXABLE);
        assertThat(testSalesOrderItem.getGrosWeight()).isEqualTo(DEFAULT_GROS_WEIGHT);
        assertThat(testSalesOrderItem.getShipped()).isEqualTo(DEFAULT_SHIPPED);
        assertThat(testSalesOrderItem.getDelivered()).isEqualTo(DEFAULT_DELIVERED);
        assertThat(testSalesOrderItem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSalesOrderItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSalesOrderItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testSalesOrderItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);

        // Validate the SalesOrderItem in Elasticsearch
        verify(mockSalesOrderItemSearchRepository, times(1)).save(testSalesOrderItem);
    }

    @Test
    @Transactional
    public void createSalesOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesOrderItemRepository.findAll().size();

        // Create the SalesOrderItem with an existing ID
        salesOrderItem.setId(1L);
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderItemMockMvc.perform(post("/api/sales-order-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the SalesOrderItem in Elasticsearch
        verify(mockSalesOrderItemSearchRepository, times(0)).save(salesOrderItem);
    }


    @Test
    @Transactional
    public void getAllSalesOrderItems() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        // Get all the salesOrderItemList
        restSalesOrderItemMockMvc.perform(get("/api/sales-order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].taxable").value(hasItem(DEFAULT_TAXABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].grosWeight").value(hasItem(DEFAULT_GROS_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].shipped").value(hasItem(DEFAULT_SHIPPED.toString())))
            .andExpect(jsonPath("$.[*].delivered").value(hasItem(DEFAULT_DELIVERED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        // Get the salesOrderItem
        restSalesOrderItemMockMvc.perform(get("/api/sales-order-items/{id}", salesOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU))
            .andExpect(jsonPath("$.taxable").value(DEFAULT_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.grosWeight").value(DEFAULT_GROS_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.shipped").value(DEFAULT_SHIPPED.toString()))
            .andExpect(jsonPath("$.delivered").value(DEFAULT_DELIVERED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSalesOrderItem() throws Exception {
        // Get the salesOrderItem
        restSalesOrderItemMockMvc.perform(get("/api/sales-order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();

        // Update the salesOrderItem
        SalesOrderItem updatedSalesOrderItem = salesOrderItemRepository.findById(salesOrderItem.getId()).get();
        // Disconnect from session so that the updates on updatedSalesOrderItem are not directly saved in db
        em.detach(updatedSalesOrderItem);
        updatedSalesOrderItem
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .taxable(UPDATED_TAXABLE)
            .grosWeight(UPDATED_GROS_WEIGHT)
            .shipped(UPDATED_SHIPPED)
            .delivered(UPDATED_DELIVERED)
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT);
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(updatedSalesOrderItem);

        restSalesOrderItemMockMvc.perform(put("/api/sales-order-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO)))
            .andExpect(status().isOk());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);
        SalesOrderItem testSalesOrderItem = salesOrderItemList.get(salesOrderItemList.size() - 1);
        assertThat(testSalesOrderItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSalesOrderItem.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testSalesOrderItem.isTaxable()).isEqualTo(UPDATED_TAXABLE);
        assertThat(testSalesOrderItem.getGrosWeight()).isEqualTo(UPDATED_GROS_WEIGHT);
        assertThat(testSalesOrderItem.getShipped()).isEqualTo(UPDATED_SHIPPED);
        assertThat(testSalesOrderItem.getDelivered()).isEqualTo(UPDATED_DELIVERED);
        assertThat(testSalesOrderItem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalesOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSalesOrderItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testSalesOrderItem.getAmount()).isEqualTo(UPDATED_AMOUNT);

        // Validate the SalesOrderItem in Elasticsearch
        verify(mockSalesOrderItemSearchRepository, times(1)).save(testSalesOrderItem);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderItemRepository.findAll().size();

        // Create the SalesOrderItem
        SalesOrderItemDTO salesOrderItemDTO = salesOrderItemMapper.toDto(salesOrderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderItemMockMvc.perform(put("/api/sales-order-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderItem in the database
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SalesOrderItem in Elasticsearch
        verify(mockSalesOrderItemSearchRepository, times(0)).save(salesOrderItem);
    }

    @Test
    @Transactional
    public void deleteSalesOrderItem() throws Exception {
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);

        int databaseSizeBeforeDelete = salesOrderItemRepository.findAll().size();

        // Delete the salesOrderItem
        restSalesOrderItemMockMvc.perform(delete("/api/sales-order-items/{id}", salesOrderItem.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesOrderItem> salesOrderItemList = salesOrderItemRepository.findAll();
        assertThat(salesOrderItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SalesOrderItem in Elasticsearch
        verify(mockSalesOrderItemSearchRepository, times(1)).deleteById(salesOrderItem.getId());
    }

    @Test
    @Transactional
    public void searchSalesOrderItem() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        salesOrderItemRepository.saveAndFlush(salesOrderItem);
        when(mockSalesOrderItemSearchRepository.search(queryStringQuery("id:" + salesOrderItem.getId())))
            .thenReturn(Collections.singletonList(salesOrderItem));

        // Search the salesOrderItem
        restSalesOrderItemMockMvc.perform(get("/api/_search/sales-order-items?query=id:" + salesOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].taxable").value(hasItem(DEFAULT_TAXABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].grosWeight").value(hasItem(DEFAULT_GROS_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].shipped").value(hasItem(DEFAULT_SHIPPED.toString())))
            .andExpect(jsonPath("$.[*].delivered").value(hasItem(DEFAULT_DELIVERED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
}
