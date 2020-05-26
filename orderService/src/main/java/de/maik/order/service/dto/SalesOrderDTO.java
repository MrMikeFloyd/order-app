package de.maik.order.service.dto;

import java.time.Instant;
import java.io.Serializable;
import de.maik.order.domain.enumeration.SalesOrderStatus;

/**
 * A DTO for the {@link de.maik.order.domain.SalesOrder} entity.
 */
public class SalesOrderDTO implements Serializable {
    
    private Long id;

    private String salesOrderNumber;

    private String customerId;

    private Instant placed;

    private Instant cancelled;

    private Instant shipped;

    private Instant completed;

    private SalesOrderStatus status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Instant getPlaced() {
        return placed;
    }

    public void setPlaced(Instant placed) {
        this.placed = placed;
    }

    public Instant getCancelled() {
        return cancelled;
    }

    public void setCancelled(Instant cancelled) {
        this.cancelled = cancelled;
    }

    public Instant getShipped() {
        return shipped;
    }

    public void setShipped(Instant shipped) {
        this.shipped = shipped;
    }

    public Instant getCompleted() {
        return completed;
    }

    public void setCompleted(Instant completed) {
        this.completed = completed;
    }

    public SalesOrderStatus getStatus() {
        return status;
    }

    public void setStatus(SalesOrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesOrderDTO)) {
            return false;
        }

        return id != null && id.equals(((SalesOrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesOrderDTO{" +
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
