package edu.wctc.srt.pitdcaradminservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shruthi Routhu
 */
@Entity
@Table(name = "part")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Part.findAll", query = "SELECT p FROM Part p"),
    @NamedQuery(name = "Part.findByPartId", query = "SELECT p FROM Part p WHERE p.partId = :partId"),
    @NamedQuery(name = "Part.findByEffDate", query = "SELECT p FROM Part p WHERE p.effDate = :effDate"),
    @NamedQuery(name = "Part.findByPartName", query = "SELECT p FROM Part p WHERE p.partName = :partName"),
    @NamedQuery(name = "Part.findByPartDescription", query = "SELECT p FROM Part p WHERE p.partDescription = :partDescription"),
    @NamedQuery(name = "Part.findByPartImage", query = "SELECT p FROM Part p WHERE p.partImage = :partImage"),
    @NamedQuery(name = "Part.findBySalePrice", query = "SELECT p FROM Part p WHERE p.salePrice = :salePrice"),
    @NamedQuery(name = "Part.findByQty", query = "SELECT p FROM Part p WHERE p.qty = :qty")})
public class Part implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "part_id")
    private Integer partId;
    @Column(name = "eff_date")
    @Temporal(TemporalType.DATE)
    private Date effDate;
    @Size(max = 50)
    @Column(name = "part_name")
    private String partName;
    @Size(max = 120)
    @Column(name = "part_description")
    private String partDescription;
    @Size(max = 200)
    @Column(name = "part_image")
    private String partImage;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salePrice")
    private BigDecimal salePrice;
    @Column(name = "qty")
    private Integer qty;
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "manufacturer_id")
    @ManyToOne
    private Manufacturer manufacturerId;

    public Part() {
    }

    public Part(Integer partId) {
        this.partId = partId;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getPartImage() {
        return partImage;
    }

    public void setPartImage(String partImage) {
        this.partImage = partImage;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Manufacturer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Manufacturer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partId != null ? partId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Part)) {
            return false;
        }
        Part other = (Part) object;
        if ((this.partId == null && other.partId != null) || (this.partId != null && !this.partId.equals(other.partId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.srt.pitdcaradminservice.entity.Part[ partId=" + partId + " ]";
    }
    
}
