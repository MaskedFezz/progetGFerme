package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Parcelle;
import com.mycompany.myapp.repository.ParcelleRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Parcelle}.
 */
@RestController
@RequestMapping("/api/parcelles")
@Transactional
public class ParcelleResource {

    private final Logger log = LoggerFactory.getLogger(ParcelleResource.class);

    private static final String ENTITY_NAME = "parcelle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParcelleRepository parcelleRepository;

    public ParcelleResource(ParcelleRepository parcelleRepository) {
        this.parcelleRepository = parcelleRepository;
    }

    /**
     * {@code POST  /parcelles} : Create a new parcelle.
     *
     * @param parcelle the parcelle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parcelle, or with status {@code 400 (Bad Request)} if the parcelle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Parcelle> createParcelle(@RequestBody Parcelle parcelle) throws URISyntaxException {
        log.debug("REST request to save Parcelle : {}", parcelle);
        if (parcelle.getId() != null) {
            throw new BadRequestAlertException("A new parcelle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parcelle result = parcelleRepository.save(parcelle);
        return ResponseEntity
            .created(new URI("/api/parcelles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parcelles/:id} : Updates an existing parcelle.
     *
     * @param id the id of the parcelle to save.
     * @param parcelle the parcelle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcelle,
     * or with status {@code 400 (Bad Request)} if the parcelle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parcelle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Parcelle> updateParcelle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parcelle parcelle
    ) throws URISyntaxException {
        log.debug("REST request to update Parcelle : {}, {}", id, parcelle);
        if (parcelle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parcelle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parcelleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Parcelle result = parcelleRepository.save(parcelle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parcelle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parcelles/:id} : Partial updates given fields of an existing parcelle, field will ignore if it is null
     *
     * @param id the id of the parcelle to save.
     * @param parcelle the parcelle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcelle,
     * or with status {@code 400 (Bad Request)} if the parcelle is not valid,
     * or with status {@code 404 (Not Found)} if the parcelle is not found,
     * or with status {@code 500 (Internal Server Error)} if the parcelle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Parcelle> partialUpdateParcelle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parcelle parcelle
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parcelle partially : {}, {}", id, parcelle);
        if (parcelle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parcelle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parcelleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Parcelle> result = parcelleRepository
            .findById(parcelle.getId())
            .map(existingParcelle -> {
                if (parcelle.getParcelleLibelle() != null) {
                    existingParcelle.setParcelleLibelle(parcelle.getParcelleLibelle());
                }
                if (parcelle.getPhoto() != null) {
                    existingParcelle.setPhoto(parcelle.getPhoto());
                }
                if (parcelle.getPhotoContentType() != null) {
                    existingParcelle.setPhotoContentType(parcelle.getPhotoContentType());
                }

                return existingParcelle;
            })
            .map(parcelleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parcelle.getId().toString())
        );
    }

    /**
     * {@code GET  /parcelles} : get all the parcelles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parcelles in body.
     */
    @GetMapping("")
    public List<Parcelle> getAllParcelles() {
        log.debug("REST request to get all Parcelles");
        return parcelleRepository.findAll();
    }

    /**
     * {@code GET  /parcelles/:id} : get the "id" parcelle.
     *
     * @param id the id of the parcelle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parcelle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Parcelle> getParcelle(@PathVariable("id") Long id) {
        log.debug("REST request to get Parcelle : {}", id);
        Optional<Parcelle> parcelle = parcelleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parcelle);
    }

    /**
     * {@code DELETE  /parcelles/:id} : delete the "id" parcelle.
     *
     * @param id the id of the parcelle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcelle(@PathVariable("id") Long id) {
        log.debug("REST request to delete Parcelle : {}", id);
        parcelleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/{id}/ferme-libelle")
    public ResponseEntity<String> findFermeParParcelleId(@PathVariable Long id) {
        log.debug("REST request to get FermeLibelle for Parcelle: {}", id);
        String fermeLibelle = parcelleRepository.findFermeParParcelleId(id);
        return ResponseEntity.ok(fermeLibelle);
    }
}
