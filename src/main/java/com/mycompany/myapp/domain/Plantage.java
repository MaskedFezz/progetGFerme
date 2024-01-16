package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Plantage.
 */
@Entity
@Table(name = "plantage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plantage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "nombre")
    private Long nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plantages", "nom" }, allowSetters = true)
    private Plante planteLibelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plantages", "fermeLibelle" }, allowSetters = true)
    private Parcelle parcelleLibelle;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plantage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Plantage date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getNombre() {
        return this.nombre;
    }

    public Plantage nombre(Long nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(Long nombre) {
        this.nombre = nombre;
    }

    public Plante getPlanteLibelle() {
        return this.planteLibelle;
    }

    public void setPlanteLibelle(Plante plante) {
        this.planteLibelle = plante;
    }

    public Plantage planteLibelle(Plante plante) {
        this.setPlanteLibelle(plante);
        return this;
    }

    public Parcelle getParcelleLibelle() {
        return this.parcelleLibelle;
    }

    public void setParcelleLibelle(Parcelle parcelle) {
        this.parcelleLibelle = parcelle;
    }

    public Plantage parcelleLibelle(Parcelle parcelle) {
        this.setParcelleLibelle(parcelle);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plantage)) {
            return false;
        }
        return getId() != null && getId().equals(((Plantage) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plantage{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", nombre=" + getNombre() +
            "}";
    }
}
