package de.maik.storeservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import de.maik.storeservice.domain.enumeration.UnitOfMeasurement;

import de.maik.storeservice.domain.enumeration.ProductStatus;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 6)
    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "description")
    private String description;

    @Column(name = "srp", precision = 21, scale = 2)
    private BigDecimal srp;

    @Column(name = "taxable")
    private Boolean taxable;

    @Enumerated(EnumType.STRING)
    @Column(name = "sales_unit")
    private UnitOfMeasurement salesUnit;

    @Column(name = "sales_quantity", precision = 21, scale = 2)
    private BigDecimal salesQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;

    @Column(name = "gros_weight")
    private Double grosWeight;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Photo> photos = new HashSet<>();

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public Product sku(String sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSrp() {
        return srp;
    }

    public Product srp(BigDecimal srp) {
        this.srp = srp;
        return this;
    }

    public void setSrp(BigDecimal srp) {
        this.srp = srp;
    }

    public Boolean isTaxable() {
        return taxable;
    }

    public Product taxable(Boolean taxable) {
        this.taxable = taxable;
        return this;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public UnitOfMeasurement getSalesUnit() {
        return salesUnit;
    }

    public Product salesUnit(UnitOfMeasurement salesUnit) {
        this.salesUnit = salesUnit;
        return this;
    }

    public void setSalesUnit(UnitOfMeasurement salesUnit) {
        this.salesUnit = salesUnit;
    }

    public BigDecimal getSalesQuantity() {
        return salesQuantity;
    }

    public Product salesQuantity(BigDecimal salesQuantity) {
        this.salesQuantity = salesQuantity;
        return this;
    }

    public void setSalesQuantity(BigDecimal salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public Product status(ProductStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Double getGrosWeight() {
        return grosWeight;
    }

    public Product grosWeight(Double grosWeight) {
        this.grosWeight = grosWeight;
        return this;
    }

    public void setGrosWeight(Double grosWeight) {
        this.grosWeight = grosWeight;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public Product netWeight(Double netWeight) {
        this.netWeight = netWeight;
        return this;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getLength() {
        return length;
    }

    public Product length(Double length) {
        this.length = length;
        return this;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public Product width(Double width) {
        this.width = width;
        return this;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public Product height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Product photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Product addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setProduct(this);
        return this;
    }

    public Product removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setProduct(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
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
