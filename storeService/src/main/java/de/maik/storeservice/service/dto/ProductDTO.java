package de.maik.storeservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import de.maik.storeservice.domain.enumeration.UnitOfMeasurement;
import de.maik.storeservice.domain.enumeration.ProductStatus;

/**
 * A DTO for the {@link de.maik.storeservice.domain.Product} entity.
 */
public class ProductDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @Size(min = 6)
    private String sku;

    private String description;

    private BigDecimal srp;

    private Boolean taxable;

    private UnitOfMeasurement salesUnit;

    private BigDecimal salesQuantity;

    private ProductStatus status;

    private Double grosWeight;

    private Double netWeight;

    private Double length;

    private Double width;

    private Double height;

    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSrp() {
        return srp;
    }

    public void setSrp(BigDecimal srp) {
        this.srp = srp;
    }

    public Boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public UnitOfMeasurement getSalesUnit() {
        return salesUnit;
    }

    public void setSalesUnit(UnitOfMeasurement salesUnit) {
        this.salesUnit = salesUnit;
    }

    public BigDecimal getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(BigDecimal salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Double getGrosWeight() {
        return grosWeight;
    }

    public void setGrosWeight(Double grosWeight) {
        this.grosWeight = grosWeight;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sku='" + getSku() + "'" +
            ", description='" + getDescription() + "'" +
            ", srp=" + getSrp() +
            ", taxable='" + isTaxable() + "'" +
            ", salesUnit='" + getSalesUnit() + "'" +
            ", salesQuantity=" + getSalesQuantity() +
            ", status='" + getStatus() + "'" +
            ", grosWeight=" + getGrosWeight() +
            ", netWeight=" + getNetWeight() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            "}";
    }
}
