package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Parcelle.
 */
@Entity
@Table(name = "parcelle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parcelle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parcelle_libelle")
    private String parcelleLibelle;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parcelleLibelle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "planteLibelle", "parcelleLibelle" }, allowSetters = true)
    private Set<Plantage> plantages = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    //@Column(name = "ferme")
    @JsonIgnoreProperties(value = { "parcelles" }, allowSetters = true)
    private Ferme fermeLibelle;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parcelle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcelleLibelle() {
        return this.parcelleLibelle;
    }

    public Parcelle parcelleLibelle(String parcelleLibelle) {
        this.setParcelleLibelle(parcelleLibelle);
        return this;
    }

    public void setParcelleLibelle(String parcelleLibelle) {
        this.parcelleLibelle = parcelleLibelle;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Parcelle photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Parcelle photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<Plantage> getPlantages() {
        return this.plantages;
    }

    public void setPlantages(Set<Plantage> plantages) {
        if (this.plantages != null) {
            this.plantages.forEach(i -> i.setParcelleLibelle(null));
        }
        if (plantages != null) {
            plantages.forEach(i -> i.setParcelleLibelle(this));
        }
        this.plantages = plantages;
    }

    public Parcelle plantages(Set<Plantage> plantages) {
        this.setPlantages(plantages);
        return this;
    }

    public Parcelle addPlantage(Plantage plantage) {
        this.plantages.add(plantage);
        plantage.setParcelleLibelle(this);
        return this;
    }

    public Parcelle removePlantage(Plantage plantage) {
        this.plantages.remove(plantage);
        plantage.setParcelleLibelle(null);
        return this;
    }

    public Ferme getFermeLibelle() {
        return this.fermeLibelle;
    }

    public void setFermeLibelle(Ferme ferme) {
        this.fermeLibelle = ferme;
    }

    public Parcelle fermeLibelle(Ferme ferme) {
        this.setFermeLibelle(ferme);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parcelle)) {
            return false;
        }
        return getId() != null && getId().equals(((Parcelle) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parcelle{" +
            "id=" + getId() +
            ", parcelleLibelle='" + getParcelleLibelle() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
