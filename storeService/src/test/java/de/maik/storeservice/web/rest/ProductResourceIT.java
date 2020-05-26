package de.maik.storeservice.web.rest;

import de.maik.storeservice.StoreServiceApp;
import de.maik.storeservice.config.TestSecurityConfiguration;
import de.maik.storeservice.domain.Product;
import de.maik.storeservice.repository.ProductRepository;
import de.maik.storeservice.repository.search.ProductSearchRepository;
import de.maik.storeservice.service.ProductService;
import de.maik.storeservice.service.dto.ProductDTO;
import de.maik.storeservice.service.mapper.ProductMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.maik.storeservice.domain.enumeration.UnitOfMeasurement;
import de.maik.storeservice.domain.enumeration.ProductStatus;
/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@SpringBootTest(classes = { StoreServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SRP = new BigDecimal(1);
    private static final BigDecimal UPDATED_SRP = new BigDecimal(2);

    private static final Boolean DEFAULT_TAXABLE = false;
    private static final Boolean UPDATED_TAXABLE = true;

    private static final UnitOfMeasurement DEFAULT_SALES_UNIT = UnitOfMeasurement.PIECE;
    private static final UnitOfMeasurement UPDATED_SALES_UNIT = UnitOfMeasurement.KILOGRAM;

    private static final BigDecimal DEFAULT_SALES_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALES_QUANTITY = new BigDecimal(2);

    private static final ProductStatus DEFAULT_STATUS = ProductStatus.ONSALE;
    private static final ProductStatus UPDATED_STATUS = ProductStatus.LOCKED;

    private static final Double DEFAULT_GROS_WEIGHT = 1D;
    private static final Double UPDATED_GROS_WEIGHT = 2D;

    private static final Double DEFAULT_NET_WEIGHT = 1D;
    private static final Double UPDATED_NET_WEIGHT = 2D;

    private static final Double DEFAULT_LENGTH = 1D;
    private static final Double UPDATED_LENGTH = 2D;

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    /**
     * This repository is mocked in the de.maik.storeservice.repository.search test package.
     *
     * @see de.maik.storeservice.repository.search.ProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductSearchRepository mockProductSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .sku(DEFAULT_SKU)
            .description(DEFAULT_DESCRIPTION)
            .srp(DEFAULT_SRP)
            .taxable(DEFAULT_TAXABLE)
            .salesUnit(DEFAULT_SALES_UNIT)
            .salesQuantity(DEFAULT_SALES_QUANTITY)
            .status(DEFAULT_STATUS)
            .grosWeight(DEFAULT_GROS_WEIGHT)
            .netWeight(DEFAULT_NET_WEIGHT)
            .length(DEFAULT_LENGTH)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT);
        return product;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .description(UPDATED_DESCRIPTION)
            .srp(UPDATED_SRP)
            .taxable(UPDATED_TAXABLE)
            .salesUnit(UPDATED_SALES_UNIT)
            .salesQuantity(UPDATED_SALES_QUANTITY)
            .status(UPDATED_STATUS)
            .grosWeight(UPDATED_GROS_WEIGHT)
            .netWeight(UPDATED_NET_WEIGHT)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getSrp()).isEqualTo(DEFAULT_SRP);
        assertThat(testProduct.isTaxable()).isEqualTo(DEFAULT_TAXABLE);
        assertThat(testProduct.getSalesUnit()).isEqualTo(DEFAULT_SALES_UNIT);
        assertThat(testProduct.getSalesQuantity()).isEqualTo(DEFAULT_SALES_QUANTITY);
        assertThat(testProduct.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProduct.getGrosWeight()).isEqualTo(DEFAULT_GROS_WEIGHT);
        assertThat(testProduct.getNetWeight()).isEqualTo(DEFAULT_NET_WEIGHT);
        assertThat(testProduct.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testProduct.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testProduct.getHeight()).isEqualTo(DEFAULT_HEIGHT);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setName(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);


        restProductMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSkuIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setSku(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);


        restProductMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].srp").value(hasItem(DEFAULT_SRP.intValue())))
            .andExpect(jsonPath("$.[*].taxable").value(hasItem(DEFAULT_TAXABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].salesUnit").value(hasItem(DEFAULT_SALES_UNIT.toString())))
            .andExpect(jsonPath("$.[*].salesQuantity").value(hasItem(DEFAULT_SALES_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].grosWeight").value(hasItem(DEFAULT_GROS_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].netWeight").value(hasItem(DEFAULT_NET_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.srp").value(DEFAULT_SRP.intValue()))
            .andExpect(jsonPath("$.taxable").value(DEFAULT_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.salesUnit").value(DEFAULT_SALES_UNIT.toString()))
            .andExpect(jsonPath("$.salesQuantity").value(DEFAULT_SALES_QUANTITY.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.grosWeight").value(DEFAULT_GROS_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.netWeight").value(DEFAULT_NET_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .description(UPDATED_DESCRIPTION)
            .srp(UPDATED_SRP)
            .taxable(UPDATED_TAXABLE)
            .salesUnit(UPDATED_SALES_UNIT)
            .salesQuantity(UPDATED_SALES_QUANTITY)
            .status(UPDATED_STATUS)
            .grosWeight(UPDATED_GROS_WEIGHT)
            .netWeight(UPDATED_NET_WEIGHT)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc.perform(put("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getSrp()).isEqualTo(UPDATED_SRP);
        assertThat(testProduct.isTaxable()).isEqualTo(UPDATED_TAXABLE);
        assertThat(testProduct.getSalesUnit()).isEqualTo(UPDATED_SALES_UNIT);
        assertThat(testProduct.getSalesQuantity()).isEqualTo(UPDATED_SALES_QUANTITY);
        assertThat(testProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProduct.getGrosWeight()).isEqualTo(UPDATED_GROS_WEIGHT);
        assertThat(testProduct.getNetWeight()).isEqualTo(UPDATED_NET_WEIGHT);
        assertThat(testProduct.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testProduct.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testProduct.getHeight()).isEqualTo(UPDATED_HEIGHT);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).deleteById(product.getId());
    }

    @Test
    @Transactional
    public void searchProduct() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        productRepository.saveAndFlush(product);
        when(mockProductSearchRepository.search(queryStringQuery("id:" + product.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(product), PageRequest.of(0, 1), 1));

        // Search the product
        restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].srp").value(hasItem(DEFAULT_SRP.intValue())))
            .andExpect(jsonPath("$.[*].taxable").value(hasItem(DEFAULT_TAXABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].salesUnit").value(hasItem(DEFAULT_SALES_UNIT.toString())))
            .andExpect(jsonPath("$.[*].salesQuantity").value(hasItem(DEFAULT_SALES_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].grosWeight").value(hasItem(DEFAULT_GROS_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].netWeight").value(hasItem(DEFAULT_NET_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())));
    }
}
