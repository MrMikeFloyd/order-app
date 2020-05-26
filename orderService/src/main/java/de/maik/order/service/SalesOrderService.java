package de.maik.order.service;

import de.maik.order.domain.SalesOrder;
import de.maik.order.repository.SalesOrderRepository;
import de.maik.order.repository.search.SalesOrderSearchRepository;
import de.maik.order.service.dto.SalesOrderDTO;
import de.maik.order.service.mapper.SalesOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SalesOrder}.
 */
@Service
@Transactional
public class SalesOrderService {

    private final Logger log = LoggerFactory.getLogger(SalesOrderService.class);

    private final SalesOrderRepository salesOrderRepository;

    private final SalesOrderMapper salesOrderMapper;

    private final SalesOrderSearchRepository salesOrderSearchRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository, SalesOrderMapper salesOrderMapper, SalesOrderSearchRepository salesOrderSearchRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderMapper = salesOrderMapper;
        this.salesOrderSearchRepository = salesOrderSearchRepository;
    }

    /**
     * Save a salesOrder.
     *
     * @param salesOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesOrderDTO save(SalesOrderDTO salesOrderDTO) {
        log.debug("Request to save SalesOrder : {}", salesOrderDTO);
        SalesOrder salesOrder = salesOrderMapper.toEntity(salesOrderDTO);
        salesOrder = salesOrderRepository.save(salesOrder);
        SalesOrderDTO result = salesOrderMapper.toDto(salesOrder);
        salesOrderSearchRepository.save(salesOrder);
        return result;
    }

    /**
     * Get all the salesOrders.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SalesOrderDTO> findAll() {
        log.debug("Request to get all SalesOrders");
        return salesOrderRepository.findAll().stream()
            .map(salesOrderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one salesOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesOrderDTO> findOne(Long id) {
        log.debug("Request to get SalesOrder : {}", id);
        return salesOrderRepository.findById(id)
            .map(salesOrderMapper::toDto);
    }

    /**
     * Delete the salesOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesOrder : {}", id);

        salesOrderRepository.deleteById(id);
        salesOrderSearchRepository.deleteById(id);
    }

    /**
     * Search for the salesOrder corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SalesOrderDTO> search(String query) {
        log.debug("Request to search SalesOrders for query {}", query);
        return StreamSupport
            .stream(salesOrderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(salesOrderMapper::toDto)
        .collect(Collectors.toList());
    }
}
