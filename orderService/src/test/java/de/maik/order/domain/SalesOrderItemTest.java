package de.maik.order.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.maik.order.web.rest.TestUtil;

public class SalesOrderItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesOrderItem.class);
        SalesOrderItem salesOrderItem1 = new SalesOrderItem();
        salesOrderItem1.setId(1L);
        SalesOrderItem salesOrderItem2 = new SalesOrderItem();
        salesOrderItem2.setId(salesOrderItem1.getId());
        assertThat(salesOrderItem1).isEqualTo(salesOrderItem2);
        salesOrderItem2.setId(2L);
        assertThat(salesOrderItem1).isNotEqualTo(salesOrderItem2);
        salesOrderItem1.setId(null);
        assertThat(salesOrderItem1).isNotEqualTo(salesOrderItem2);
    }
}
