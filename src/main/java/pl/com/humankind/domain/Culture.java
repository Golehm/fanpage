package pl.com.humankind.domain;


import javax.persistence.*;

import java.io.Serializable;

import pl.com.humankind.domain.enumeration.Era;

import pl.com.humankind.domain.enumeration.Kind;

/**
 * A Culture.
 */
@Entity
@Table(name = "culture")
public class Culture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "era")
    private Era era;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind")
    private Kind kind;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "unit_description")
    private String unitDescription;

    @Column(name = "quarter")
    private String quarter;

    @Column(name = "quarter_description")
    private String quarterDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Era getEra() {
        return era;
    }

    public Culture era(Era era) {
        this.era = era;
        return this;
    }

    public void setEra(Era era) {
        this.era = era;
    }

    public Kind getKind() {
        return kind;
    }

    public Culture kind(Kind kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public byte[] getImage() {
        return image;
    }

    public Culture image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Culture imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getName() {
        return name;
    }

    public Culture name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public Culture unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public Culture unitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
        return this;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public String getQuarter() {
        return quarter;
    }

    public Culture quarter(String quarter) {
        this.quarter = quarter;
        return this;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getQuarterDescription() {
        return quarterDescription;
    }

    public Culture quarterDescription(String quarterDescription) {
        this.quarterDescription = quarterDescription;
        return this;
    }

    public void setQuarterDescription(String quarterDescription) {
        this.quarterDescription = quarterDescription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Culture)) {
            return false;
        }
        return id != null && id.equals(((Culture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Culture{" +
            "id=" + getId() +
            ", era='" + getEra() + "'" +
            ", kind='" + getKind() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", name='" + getName() + "'" +
            ", unit='" + getUnit() + "'" +
            ", unitDescription='" + getUnitDescription() + "'" +
            ", quarter='" + getQuarter() + "'" +
            ", quarterDescription='" + getQuarterDescription() + "'" +
            "}";
    }
}
