package de.maik.order.web.rest;

import de.maik.order.service.SalesOrderService;
import de.maik.order.web.rest.errors.BadRequestAlertException;
import de.maik.order.service.dto.SalesOrderDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link de.maik.order.domain.SalesOrder}.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderResource.class);

    private static final String ENTITY_NAME = "orderServiceSalesOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesOrderService salesOrderService;

    public SalesOrderResource(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    /**
     * {@code POST  /sales-orders} : Create a new salesOrder.
     *
     * @param salesOrderDTO the salesOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesOrderDTO, or with status {@code 400 (Bad Request)} if the salesOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-orders")
    public ResponseEntity<SalesOrderDTO> createSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) throws URISyntaxException {
        log.debug("REST request to save SalesOrder : {}", salesOrderDTO);
        if (salesOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new salesOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesOrderDTO result = salesOrderService.save(salesOrderDTO);
        return ResponseEntity.created(new URI("/api/sales-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-orders} : Updates an existing salesOrder.
     *
     * @param salesOrderDTO the salesOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrderDTO,
     * or with status {@code 400 (Bad Request)} if the salesOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-orders")
    public ResponseEntity<SalesOrderDTO> updateSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) throws URISyntaxException {
        log.debug("REST request to update SalesOrder : {}", salesOrderDTO);
        if (salesOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalesOrderDTO result = salesOrderService.save(salesOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sales-orders} : get all the salesOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesOrders in body.
     */
    @GetMapping("/sales-orders")
    public List<SalesOrderDTO> getAllSalesOrders() {
        log.debug("REST request to get all SalesOrders");
        return salesOrderService.findAll();
    }

    /**
     * {@code GET  /sales-orders/:id} : get the "id" salesOrder.
     *
     * @param id the id of the salesOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-orders/{id}")
    public ResponseEntity<SalesOrderDTO> getSalesOrder(@PathVariable Long id) {
        log.debug("REST request to get SalesOrder : {}", id);
        Optional<SalesOrderDTO> salesOrderDTO = salesOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesOrderDTO);
    }

    /**
     * {@code DELETE  /sales-orders/:id} : delete the "id" salesOrder.
     *
     * @param id the id of the salesOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-orders/{id}")
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrder : {}", id);

        salesOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/sales-orders?query=:query} : search for the salesOrder corresponding
     * to the query.
     *
     * @param query the query of the salesOrder search.
     * @return the result of the search.
     */
    @GetMapping("/_search/sales-orders")
    public List<SalesOrderDTO> searchSalesOrders(@RequestParam String query) {
        log.debug("REST request to search SalesOrders for query {}", query);
        return salesOrderService.search(query);
    }
}
