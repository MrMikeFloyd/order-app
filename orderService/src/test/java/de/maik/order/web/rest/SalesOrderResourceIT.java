package de.maik.order.web.rest;

import de.maik.order.OrderServiceApp;
import de.maik.order.config.TestSecurityConfiguration;
import de.maik.order.domain.SalesOrder;
import de.maik.order.repository.SalesOrderRepository;
import de.maik.order.repository.search.SalesOrderSearchRepository;
import de.maik.order.service.SalesOrderService;
import de.maik.order.service.dto.SalesOrderDTO;
import de.maik.order.service.mapper.SalesOrderMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.maik.order.domain.enumeration.SalesOrderStatus;
/**
 * Integration tests for the {@link SalesOrderResource} REST controller.
 */
@SpringBootTest(classes = { OrderServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalesOrderResourceIT {

    private static final String DEFAULT_SALES_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SALES_ORDER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_PLACED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLACED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CANCELLED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CANCELLED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SHIPPED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHIPPED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_COMPLETED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SalesOrderStatus DEFAULT_STATUS = SalesOrderStatus.PENDING;
    private static final SalesOrderStatus UPDATED_STATUS = SalesOrderStatus.CANCELLED;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SalesOrderService salesOrderService;

    /**
     * This repository is mocked in the de.maik.order.repository.search test package.
     *
     * @see de.maik.order.repository.search.SalesOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private SalesOrderSearchRepository mockSalesOrderSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesOrderMockMvc;

    private SalesOrder salesOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrder createEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder()
            .salesOrderNumber(DEFAULT_SALES_ORDER_NUMBER)
            .customerId(DEFAULT_CUSTOMER_ID)
            .placed(DEFAULT_PLACED)
            .cancelled(DEFAULT_CANCELLED)
            .shipped(DEFAULT_SHIPPED)
            .completed(DEFAULT_COMPLETED)
            .status(DEFAULT_STATUS);
        return salesOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrder createUpdatedEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder()
            .salesOrderNumber(UPDATED_SALES_ORDER_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .placed(UPDATED_PLACED)
            .cancelled(UPDATED_CANCELLED)
            .shipped(UPDATED_SHIPPED)
            .completed(UPDATED_COMPLETED)
            .status(UPDATED_STATUS);
        return salesOrder;
    }

    @BeforeEach
    public void initTest() {
        salesOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrder() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();
        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);
        restSalesOrderMockMvc.perform(post("/api/sales-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
        assertThat(testSalesOrder.getSalesOrderNumber()).isEqualTo(DEFAULT_SALES_ORDER_NUMBER);
        assertThat(testSalesOrder.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testSalesOrder.getPlaced()).isEqualTo(DEFAULT_PLACED);
        assertThat(testSalesOrder.getCancelled()).isEqualTo(DEFAULT_CANCELLED);
        assertThat(testSalesOrder.getShipped()).isEqualTo(DEFAULT_SHIPPED);
        assertThat(testSalesOrder.getCompleted()).isEqualTo(DEFAULT_COMPLETED);
        assertThat(testSalesOrder.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the SalesOrder in Elasticsearch
        verify(mockSalesOrderSearchRepository, times(1)).save(testSalesOrder);
    }

    @Test
    @Transactional
    public void createSalesOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // Create the SalesOrder with an existing ID
        salesOrder.setId(1L);
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderMockMvc.perform(post("/api/sales-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the SalesOrder in Elasticsearch
        verify(mockSalesOrderSearchRepository, times(0)).save(salesOrder);
    }


    @Test
    @Transactional
    public void getAllSalesOrders() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get all the salesOrderList
        restSalesOrderMockMvc.perform(get("/api/sales-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesOrderNumber").value(hasItem(DEFAULT_SALES_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].placed").value(hasItem(DEFAULT_PLACED.toString())))
            .andExpect(jsonPath("$.[*].cancelled").value(hasItem(DEFAULT_CANCELLED.toString())))
            .andExpect(jsonPath("$.[*].shipped").value(hasItem(DEFAULT_SHIPPED.toString())))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrder.getId().intValue()))
            .andExpect(jsonPath("$.salesOrderNumber").value(DEFAULT_SALES_ORDER_NUMBER))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.placed").value(DEFAULT_PLACED.toString()))
            .andExpect(jsonPath("$.cancelled").value(DEFAULT_CANCELLED.toString()))
            .andExpect(jsonPath("$.shipped").value(DEFAULT_SHIPPED.toString()))
            .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSalesOrder() throws Exception {
        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder
        SalesOrder updatedSalesOrder = salesOrderRepository.findById(salesOrder.getId()).get();
        // Disconnect from session so that the updates on updatedSalesOrder are not directly saved in db
        em.detach(updatedSalesOrder);
        updatedSalesOrder
            .salesOrderNumber(UPDATED_SALES_ORDER_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .placed(UPDATED_PLACED)
            .cancelled(UPDATED_CANCELLED)
            .shipped(UPDATED_SHIPPED)
            .completed(UPDATED_COMPLETED)
            .status(UPDATED_STATUS);
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(updatedSalesOrder);

        restSalesOrderMockMvc.perform(put("/api/sales-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
            .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
        assertThat(testSalesOrder.getSalesOrderNumber()).isEqualTo(UPDATED_SALES_ORDER_NUMBER);
        assertThat(testSalesOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testSalesOrder.getPlaced()).isEqualTo(UPDATED_PLACED);
        assertThat(testSalesOrder.getCancelled()).isEqualTo(UPDATED_CANCELLED);
        assertThat(testSalesOrder.getShipped()).isEqualTo(UPDATED_SHIPPED);
        assertThat(testSalesOrder.getCompleted()).isEqualTo(UPDATED_COMPLETED);
        assertThat(testSalesOrder.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the SalesOrder in Elasticsearch
        verify(mockSalesOrderSearchRepository, times(1)).save(testSalesOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Create the SalesOrder
        SalesOrderDTO salesOrderDTO = salesOrderMapper.toDto(salesOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderMockMvc.perform(put("/api/sales-orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SalesOrder in Elasticsearch
        verify(mockSalesOrderSearchRepository, times(0)).save(salesOrder);
    }

    @Test
    @Transactional
    public void deleteSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        int databaseSizeBeforeDelete = salesOrderRepository.findAll().size();

        // Delete the salesOrder
        restSalesOrderMockMvc.perform(delete("/api/sales-orders/{id}", salesOrder.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SalesOrder in Elasticsearch
        verify(mockSalesOrderSearchRepository, times(1)).deleteById(salesOrder.getId());
    }

    @Test
    @Transactional
    public void searchSalesOrder() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);
        when(mockSalesOrderSearchRepository.search(queryStringQuery("id:" + salesOrder.getId())))
            .thenReturn(Collections.singletonList(salesOrder));

        // Search the salesOrder
        restSalesOrderMockMvc.perform(get("/api/_search/sales-orders?query=id:" + salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesOrderNumber").value(hasItem(DEFAULT_SALES_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].placed").value(hasItem(DEFAULT_PLACED.toString())))
            .andExpect(jsonPath("$.[*].cancelled").value(hasItem(DEFAULT_CANCELLED.toString())))
            .andExpect(jsonPath("$.[*].shipped").value(hasItem(DEFAULT_SHIPPED.toString())))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
