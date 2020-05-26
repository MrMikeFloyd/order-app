package de.maik.order.web.rest;

import de.maik.order.service.SalesOrderItemService;
import de.maik.order.web.rest.errors.BadRequestAlertException;
import de.maik.order.service.dto.SalesOrderItemDTO;

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
 * REST controller for managing {@link de.maik.order.domain.SalesOrderItem}.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderItemResource.class);

    private static final String ENTITY_NAME = "orderServiceSalesOrderItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesOrderItemService salesOrderItemService;

    public SalesOrderItemResource(SalesOrderItemService salesOrderItemService) {
        this.salesOrderItemService = salesOrderItemService;
    }

    /**
     * {@code POST  /sales-order-items} : Create a new salesOrderItem.
     *
     * @param salesOrderItemDTO the salesOrderItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesOrderItemDTO, or with status {@code 400 (Bad Request)} if the salesOrderItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-order-items")
    public ResponseEntity<SalesOrderItemDTO> createSalesOrderItem(@RequestBody SalesOrderItemDTO salesOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to save SalesOrderItem : {}", salesOrderItemDTO);
        if (salesOrderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new salesOrderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesOrderItemDTO result = salesOrderItemService.save(salesOrderItemDTO);
        return ResponseEntity.created(new URI("/api/sales-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-order-items} : Updates an existing salesOrderItem.
     *
     * @param salesOrderItemDTO the salesOrderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrderItemDTO,
     * or with status {@code 400 (Bad Request)} if the salesOrderItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesOrderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-order-items")
    public ResponseEntity<SalesOrderItemDTO> updateSalesOrderItem(@RequestBody SalesOrderItemDTO salesOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to update SalesOrderItem : {}", salesOrderItemDTO);
        if (salesOrderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalesOrderItemDTO result = salesOrderItemService.save(salesOrderItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sales-order-items} : get all the salesOrderItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesOrderItems in body.
     */
    @GetMapping("/sales-order-items")
    public List<SalesOrderItemDTO> getAllSalesOrderItems() {
        log.debug("REST request to get all SalesOrderItems");
        return salesOrderItemService.findAll();
    }

    /**
     * {@code GET  /sales-order-items/:id} : get the "id" salesOrderItem.
     *
     * @param id the id of the salesOrderItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesOrderItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-order-items/{id}")
    public ResponseEntity<SalesOrderItemDTO> getSalesOrderItem(@PathVariable Long id) {
        log.debug("REST request to get SalesOrderItem : {}", id);
        Optional<SalesOrderItemDTO> salesOrderItemDTO = salesOrderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesOrderItemDTO);
    }

    /**
     * {@code DELETE  /sales-order-items/:id} : delete the "id" salesOrderItem.
     *
     * @param id the id of the salesOrderItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-order-items/{id}")
    public ResponseEntity<Void> deleteSalesOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrderItem : {}", id);

        salesOrderItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/sales-order-items?query=:query} : search for the salesOrderItem corresponding
     * to the query.
     *
     * @param query the query of the salesOrderItem search.
     * @return the result of the search.
     */
    @GetMapping("/_search/sales-order-items")
    public List<SalesOrderItemDTO> searchSalesOrderItems(@RequestParam String query) {
        log.debug("REST request to search SalesOrderItems for query {}", query);
        return salesOrderItemService.search(query);
    }
}
