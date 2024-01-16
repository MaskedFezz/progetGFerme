package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Plante.
 */
@Entity
@Table(name = "plante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plante_libelle")
    private String planteLibelle;

    @Column(name = "racine")
    private String racine;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planteLibelle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "planteLibelle", "parcelleLibelle" }, allowSetters = true)
    private Set<Plantage> plantages = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plantes" }, allowSetters = true)
    private TypePlante nom;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanteLibelle() {
        return this.planteLibelle;
    }

    public Plante planteLibelle(String planteLibelle) {
        this.setPlanteLibelle(planteLibelle);
        return this;
    }

    public void setPlanteLibelle(String planteLibelle) {
        this.planteLibelle = planteLibelle;
    }

    public String getRacine() {
        return this.racine;
    }

    public Plante racine(String racine) {
        this.setRacine(racine);
        return this;
    }

    public void setRacine(String racine) {
        this.racine = racine;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Plante photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Plante photoContentType(String photoContentType) {
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
            this.plantages.forEach(i -> i.setPlanteLibelle(null));
        }
        if (plantages != null) {
            plantages.forEach(i -> i.setPlanteLibelle(this));
        }
        this.plantages = plantages;
    }

    public Plante plantages(Set<Plantage> plantages) {
        this.setPlantages(plantages);
        return this;
    }

    public Plante addPlantage(Plantage plantage) {
        this.plantages.add(plantage);
        plantage.setPlanteLibelle(this);
        return this;
    }

    public Plante removePlantage(Plantage plantage) {
        this.plantages.remove(plantage);
        plantage.setPlanteLibelle(null);
        return this;
    }

    public TypePlante getNom() {
        return this.nom;
    }

    public void setNom(TypePlante typePlante) {
        this.nom = typePlante;
    }

    public Plante nom(TypePlante typePlante) {
        this.setNom(typePlante);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plante)) {
            return false;
        }
        return getId() != null && getId().equals(((Plante) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plante{" +
            "id=" + getId() +
            ", planteLibelle='" + getPlanteLibelle() + "'" +
            ", racine='" + getRacine() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
