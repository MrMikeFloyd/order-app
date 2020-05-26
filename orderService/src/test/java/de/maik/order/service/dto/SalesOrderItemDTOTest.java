package de.maik.order.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.maik.order.web.rest.TestUtil;

public class SalesOrderItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesOrderItemDTO.class);
        SalesOrderItemDTO salesOrderItemDTO1 = new SalesOrderItemDTO();
        salesOrderItemDTO1.setId(1L);
        SalesOrderItemDTO salesOrderItemDTO2 = new SalesOrderItemDTO();
        assertThat(salesOrderItemDTO1).isNotEqualTo(salesOrderItemDTO2);
        salesOrderItemDTO2.setId(salesOrderItemDTO1.getId());
        assertThat(salesOrderItemDTO1).isEqualTo(salesOrderItemDTO2);
        salesOrderItemDTO2.setId(2L);
        assertThat(salesOrderItemDTO1).isNotEqualTo(salesOrderItemDTO2);
        salesOrderItemDTO1.setId(null);
        assertThat(salesOrderItemDTO1).isNotEqualTo(salesOrderItemDTO2);
    }
}
