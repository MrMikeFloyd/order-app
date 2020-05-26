package de.maik.order.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SalesOrderSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SalesOrderSearchRepositoryMockConfiguration {

    @MockBean
    private SalesOrderSearchRepository mockSalesOrderSearchRepository;

}
