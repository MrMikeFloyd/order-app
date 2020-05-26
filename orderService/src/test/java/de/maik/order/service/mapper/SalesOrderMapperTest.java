package de.maik.order.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SalesOrderMapperTest {

    private SalesOrderMapper salesOrderMapper;

    @BeforeEach
    public void setUp() {
        salesOrderMapper = new SalesOrderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(salesOrderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(salesOrderMapper.fromId(null)).isNull();
    }
}
