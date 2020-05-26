package de.maik.order.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SalesOrderItemSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SalesOrderItemSearchRepositoryMockConfiguration {

    @MockBean
    private SalesOrderItemSearchRepository mockSalesOrderItemSearchRepository;

}
