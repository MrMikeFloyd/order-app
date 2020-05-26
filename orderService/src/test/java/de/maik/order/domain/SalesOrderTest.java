package de.maik.order.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.maik.order.web.rest.TestUtil;

public class SalesOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesOrder.class);
        SalesOrder salesOrder1 = new SalesOrder();
        salesOrder1.setId(1L);
        SalesOrder salesOrder2 = new SalesOrder();
        salesOrder2.setId(salesOrder1.getId());
        assertThat(salesOrder1).isEqualTo(salesOrder2);
        salesOrder2.setId(2L);
        assertThat(salesOrder1).isNotEqualTo(salesOrder2);
        salesOrder1.setId(null);
        assertThat(salesOrder1).isNotEqualTo(salesOrder2);
    }
}
