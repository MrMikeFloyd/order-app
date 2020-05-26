package de.maik.order.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import de.maik.order.domain.enumeration.SalesOrderStatus;

/**
 * A SalesOrder.
 */
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "salesorder")
public class SalesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sales_order_number")
    private String salesOrderNumber;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "placed")
    private Instant placed;

    @Column(name = "cancelled")
    private Instant cancelled;

    @Column(name = "shipped")
    private Instant shipped;

    @Column(name = "completed")
    private Instant completed;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalesOrderStatus status;

    @OneToMany(mappedBy = "salesOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SalesOrderItem> orderItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public SalesOrder salesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
        return this;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public SalesOrder customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Instant getPlaced() {
        return placed;
    }

    public SalesOrder placed(Instant placed) {
        this.placed = placed;
        return this;
    }

    public void setPlaced(Instant placed) {
        this.placed = placed;
    }

    public Instant getCancelled() {
        return cancelled;
    }

    public SalesOrder cancelled(Instant cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public void setCancelled(Instant cancelled) {
        this.cancelled = cancelled;
    }

    public Instant getShipped() {
        return shipped;
    }

    public SalesOrder shipped(Instant shipped) {
        this.shipped = shipped;
        return this;
    }

    public void setShipped(Instant shipped) {
        this.shipped = shipped;
    }

    public Instant getCompleted() {
        return completed;
    }

    public SalesOrder completed(Instant completed) {
        this.completed = completed;
        return this;
    }

    public void setCompleted(Instant completed) {
        this.completed = completed;
    }

    public SalesOrderStatus getStatus() {
        return status;
    }

    public SalesOrder status(SalesOrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SalesOrderStatus status) {
        this.status = status;
    }

    public Set<SalesOrderItem> getOrderItems() {
        return orderItems;
    }

    public SalesOrder orderItems(Set<SalesOrderItem> salesOrderItems) {
        this.orderItems = salesOrderItems;
        return this;
    }

    public SalesOrder addOrderItems(SalesOrderItem salesOrderItem) {
        this.orderItems.add(salesOrderItem);
        salesOrderItem.setSalesOrder(this);
        return this;
    }

    public SalesOrder removeOrderItems(SalesOrderItem salesOrderItem) {
        this.orderItems.remove(salesOrderItem);
        salesOrderItem.setSalesOrder(null);
        return this;
    }

    public void setOrderItems(Set<SalesOrderItem> salesOrderItems) {
        this.orderItems = salesOrderItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesOrder)) {
            return false;
        }
        return id != null && id.equals(((SalesOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesOrder{" +
            "id=" + getId() +
            ", salesOrderNumber='" + getSalesOrderNumber() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", placed='" + getPlaced() + "'" +
            ", cancelled='" + getCancelled() + "'" +
            ", shipped='" + getShipped() + "'" +
            ", completed='" + getCompleted() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
