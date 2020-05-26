package de.maik.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import de.maik.order.domain.enumeration.SalesOrderItemStatus;

/**
 * A SalesOrderItem.
 */
@Entity
@Table(name = "sales_order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "salesorderitem")
public class SalesOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String sku;

    @Column(name = "taxable")
    private Boolean taxable;

    @Column(name = "gros_weight")
    private Double grosWeight;

    @Column(name = "shipped")
    private LocalDate shipped;

    @Column(name = "delivered")
    private LocalDate delivered;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalesOrderItemStatus status;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private SalesOrder salesOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SalesOrderItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public SalesOrderItem sku(String sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean isTaxable() {
        return taxable;
    }

    public SalesOrderItem taxable(Boolean taxable) {
        this.taxable = taxable;
        return this;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public Double getGrosWeight() {
        return grosWeight;
    }

    public SalesOrderItem grosWeight(Double grosWeight) {
        this.grosWeight = grosWeight;
        return this;
    }

    public void setGrosWeight(Double grosWeight) {
        this.grosWeight = grosWeight;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public SalesOrderItem shipped(LocalDate shipped) {
        this.shipped = shipped;
        return this;
    }

    public void setShipped(LocalDate shipped) {
        this.shipped = shipped;
    }

    public LocalDate getDelivered() {
        return delivered;
    }

    public SalesOrderItem delivered(LocalDate delivered) {
        this.delivered = delivered;
        return this;
    }

    public void setDelivered(LocalDate delivered) {
        this.delivered = delivered;
    }

    public SalesOrderItemStatus getStatus() {
        return status;
    }

    public SalesOrderItem status(SalesOrderItemStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SalesOrderItemStatus status) {
        this.status = status;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public SalesOrderItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public SalesOrderItem unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public SalesOrderItem amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public SalesOrderItem salesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
        return this;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesOrderItem)) {
            return false;
        }
        return id != null && id.equals(((SalesOrderItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesOrderItem{" +
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
            "}";
    }
}
