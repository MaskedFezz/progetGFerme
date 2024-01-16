package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.access.annotation.Secured;

/**
 * A TypePlante.
 */
@Entity
@Table(name = "type_plante")
@Secured("ROLE_ADMIN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypePlante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "humidite_max")
    private Long humiditeMax;

    @Column(name = "humidite_min")
    private Long humiditeMin;

    @Column(name = "temperature")
    private Long temperature;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nom")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plantages", "nom" }, allowSetters = true)
    private Set<Plante> plantes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypePlante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public TypePlante nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getHumiditeMax() {
        return this.humiditeMax;
    }

    public TypePlante humiditeMax(Long humiditeMax) {
        this.setHumiditeMax(humiditeMax);
        return this;
    }

    public void setHumiditeMax(Long humiditeMax) {
        this.humiditeMax = humiditeMax;
    }

    public Long getHumiditeMin() {
        return this.humiditeMin;
    }

    public TypePlante humiditeMin(Long humiditeMin) {
        this.setHumiditeMin(humiditeMin);
        return this;
    }

    public void setHumiditeMin(Long humiditeMin) {
        this.humiditeMin = humiditeMin;
    }

    public Long getTemperature() {
        return this.temperature;
    }

    public TypePlante temperature(Long temperature) {
        this.setTemperature(temperature);
        return this;
    }

    public void setTemperature(Long temperature) {
        this.temperature = temperature;
    }

    public Set<Plante> getPlantes() {
        return this.plantes;
    }

    public void setPlantes(Set<Plante> plantes) {
        if (this.plantes != null) {
            this.plantes.forEach(i -> i.setNom(null));
        }
        if (plantes != null) {
            plantes.forEach(i -> i.setNom(this));
        }
        this.plantes = plantes;
    }

    public TypePlante plantes(Set<Plante> plantes) {
        this.setPlantes(plantes);
        return this;
    }

    public TypePlante addPlante(Plante plante) {
        this.plantes.add(plante);
        plante.setNom(this);
        return this;
    }

    public TypePlante removePlante(Plante plante) {
        this.plantes.remove(plante);
        plante.setNom(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypePlante)) {
            return false;
        }
        return getId() != null && getId().equals(((TypePlante) o).getId());
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypePlante{" +
                "id=" + getId() +
                ", nom='" + getNom() + "'" +
                ", humiditeMax=" + getHumiditeMax() +
                ", humiditeMin=" + getHumiditeMin() +
                ", temperature=" + getTemperature() +
                "}";
    }
}
