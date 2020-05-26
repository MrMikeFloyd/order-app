package de.maik.order.repository.search;

import de.maik.order.domain.SalesOrderItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SalesOrderItem} entity.
 */
public interface SalesOrderItemSearchRepository extends ElasticsearchRepository<SalesOrderItem, Long> {
}
