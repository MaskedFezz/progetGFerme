package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ferme.
 */
@Entity
@Table(name = "ferme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ferme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ferme_libelle")
    private String fermeLibelle;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fermeLibelle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plantages", "fermeLibelle" }, allowSetters = true)
    private Set<Parcelle> parcelles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ferme id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFermeLibelle() {
        return this.fermeLibelle;
    }

    public Ferme fermeLibelle(String fermeLibelle) {
        this.setFermeLibelle(fermeLibelle);
        return this;
    }

    public void setFermeLibelle(String fermeLibelle) {
        this.fermeLibelle = fermeLibelle;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Ferme photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Ferme photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<Parcelle> getParcelles() {
        return this.parcelles;
    }

    public void setParcelles(Set<Parcelle> parcelles) {
        if (this.parcelles != null) {
            this.parcelles.forEach(i -> i.setFermeLibelle(null));
        }
        if (parcelles != null) {
            parcelles.forEach(i -> i.setFermeLibelle(this));
        }
        this.parcelles = parcelles;
    }

    public Ferme parcelles(Set<Parcelle> parcelles) {
        this.setParcelles(parcelles);
        return this;
    }

    public Ferme addParcelle(Parcelle parcelle) {
        this.parcelles.add(parcelle);
        parcelle.setFermeLibelle(this);
        return this;
    }

    public Ferme removeParcelle(Parcelle parcelle) {
        this.parcelles.remove(parcelle);
        parcelle.setFermeLibelle(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ferme)) {
            return false;
        }
        return getId() != null && getId().equals(((Ferme) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ferme{" +
            "id=" + getId() +
            ", fermeLibelle='" + getFermeLibelle() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
