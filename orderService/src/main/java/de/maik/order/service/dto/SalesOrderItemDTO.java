package de.maik.order.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import de.maik.order.domain.enumeration.SalesOrderItemStatus;

/**
 * A DTO for the {@link de.maik.order.domain.SalesOrderItem} entity.
 */
public class SalesOrderItemDTO implements Serializable {
    
    private Long id;

    private String name;

    private String sku;

    private Boolean taxable;

    private Double grosWeight;

    private LocalDate shipped;

    private LocalDate delivered;

    private SalesOrderItemStatus status;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal amount;


    private Long salesOrderId;

    private String salesOrderSalesOrderNumber;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public Double getGrosWeight() {
        return grosWeight;
    }

    public void setGrosWeight(Double grosWeight) {
        this.grosWeight = grosWeight;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public void setShipped(LocalDate shipped) {
        this.shipped = shipped;
    }

    public LocalDate getDelivered() {
        return delivered;
    }

    public void setDelivered(LocalDate delivered) {
        this.delivered = delivered;
    }

    public SalesOrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(SalesOrderItemStatus status) {
        this.status = status;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSalesOrderSalesOrderNumber() {
        return salesOrderSalesOrderNumber;
    }

    public void setSalesOrderSalesOrderNumber(String salesOrderSalesOrderNumber) {
        this.salesOrderSalesOrderNumber = salesOrderSalesOrderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesOrderItemDTO)) {
            return false;
        }

        return id != null && id.equals(((SalesOrderItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesOrderItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sku='" + getSku() + "'" +
            ", taxable='" + isTaxable() + "'" +
            ", grosWeight=" + getGrosWeight() +
            ", shipped='" + getShipped() + "'" +
            ", delivered='" + getDelivered() + "'" +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", amount=" + getAmount() +
            ", salesOrderId=" + getSalesOrderId() +
            ", salesOrderSalesOrderNumber='" + getSalesOrderSalesOrderNumber() + "'" +
            "}";
    }
}
