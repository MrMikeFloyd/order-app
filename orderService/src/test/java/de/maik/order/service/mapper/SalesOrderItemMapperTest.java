package de.maik.order.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SalesOrderItemMapperTest {

    private SalesOrderItemMapper salesOrderItemMapper;

    @BeforeEach
    public void setUp() {
        salesOrderItemMapper = new SalesOrderItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(salesOrderItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(salesOrderItemMapper.fromId(null)).isNull();
    }
}
