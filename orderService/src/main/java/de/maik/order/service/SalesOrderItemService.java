package de.maik.order.service;

import de.maik.order.domain.SalesOrderItem;
import de.maik.order.repository.SalesOrderItemRepository;
import de.maik.order.repository.search.SalesOrderItemSearchRepository;
import de.maik.order.service.dto.SalesOrderItemDTO;
import de.maik.order.service.mapper.SalesOrderItemMapper;
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
 * Service Implementation for managing {@link SalesOrderItem}.
 */
@Service
@Transactional
public class SalesOrderItemService {

    private final Logger log = LoggerFactory.getLogger(SalesOrderItemService.class);

    private final SalesOrderItemRepository salesOrderItemRepository;

    private final SalesOrderItemMapper salesOrderItemMapper;

    private final SalesOrderItemSearchRepository salesOrderItemSearchRepository;

    public SalesOrderItemService(SalesOrderItemRepository salesOrderItemRepository, SalesOrderItemMapper salesOrderItemMapper, SalesOrderItemSearchRepository salesOrderItemSearchRepository) {
        this.salesOrderItemRepository = salesOrderItemRepository;
        this.salesOrderItemMapper = salesOrderItemMapper;
        this.salesOrderItemSearchRepository = salesOrderItemSearchRepository;
    }

    /**
     * Save a salesOrderItem.
     *
     * @param salesOrderItemDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesOrderItemDTO save(SalesOrderItemDTO salesOrderItemDTO) {
        log.debug("Request to save SalesOrderItem : {}", salesOrderItemDTO);
        SalesOrderItem salesOrderItem = salesOrderItemMapper.toEntity(salesOrderItemDTO);
        salesOrderItem = salesOrderItemRepository.save(salesOrderItem);
        SalesOrderItemDTO result = salesOrderItemMapper.toDto(salesOrderItem);
        salesOrderItemSearchRepository.save(salesOrderItem);
        return result;
    }

    /**
     * Get all the salesOrderItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SalesOrderItemDTO> findAll() {
        log.debug("Request to get all SalesOrderItems");
        return salesOrderItemRepository.findAll().stream()
            .map(salesOrderItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one salesOrderItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesOrderItemDTO> findOne(Long id) {
        log.debug("Request to get SalesOrderItem : {}", id);
        return salesOrderItemRepository.findById(id)
            .map(salesOrderItemMapper::toDto);
    }

    /**
     * Delete the salesOrderItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesOrderItem : {}", id);

        salesOrderItemRepository.deleteById(id);
        salesOrderItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the salesOrderItem corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SalesOrderItemDTO> search(String query) {
        log.debug("Request to search SalesOrderItems for query {}", query);
        return StreamSupport
            .stream(salesOrderItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(salesOrderItemMapper::toDto)
        .collect(Collectors.toList());
    }
}
