package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Plante;
import com.mycompany.myapp.repository.PlanteRepository;
import jakarta.persistence.EntityManager;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanteResourceIT {

    private static final String DEFAULT_PLANTE_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_PLANTE_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_RACINE = "AAAAAAAAAA";
    private static final String UPDATED_RACINE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/plantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanteRepository planteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanteMockMvc;

    private Plante plante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plante createEntity(EntityManager em) {
        Plante plante = new Plante()
            .planteLibelle(DEFAULT_PLANTE_LIBELLE)
            .racine(DEFAULT_RACINE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return plante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plante createUpdatedEntity(EntityManager em) {
        Plante plante = new Plante()
            .planteLibelle(UPDATED_PLANTE_LIBELLE)
            .racine(UPDATED_RACINE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return plante;
    }

    @BeforeEach
    public void initTest() {
        plante = createEntity(em);
    }

    @Test
    @Transactional
    void createPlante() throws Exception {
        int databaseSizeBeforeCreate = planteRepository.findAll().size();
        // Create the Plante
        restPlanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plante)))
            .andExpect(status().isCreated());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeCreate + 1);
        Plante testPlante = planteList.get(planteList.size() - 1);
        assertThat(testPlante.getPlanteLibelle()).isEqualTo(DEFAULT_PLANTE_LIBELLE);
        assertThat(testPlante.getRacine()).isEqualTo(DEFAULT_RACINE);
        assertThat(testPlante.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPlante.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPlanteWithExistingId() throws Exception {
        // Create the Plante with an existing ID
        plante.setId(1L);

        int databaseSizeBeforeCreate = planteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plante)))
            .andExpect(status().isBadRequest());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlantes() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList
        restPlanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plante.getId().intValue())))
            .andExpect(jsonPath("$.[*].planteLibelle").value(hasItem(DEFAULT_PLANTE_LIBELLE)))
            .andExpect(jsonPath("$.[*].racine").value(hasItem(DEFAULT_RACINE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    void getPlante() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get the plante
        restPlanteMockMvc
            .perform(get(ENTITY_API_URL_ID, plante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plante.getId().intValue()))
            .andExpect(jsonPath("$.planteLibelle").value(DEFAULT_PLANTE_LIBELLE))
            .andExpect(jsonPath("$.racine").value(DEFAULT_RACINE))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64.getEncoder().encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getNonExistingPlante() throws Exception {
        // Get the plante
        restPlanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlante() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        int databaseSizeBeforeUpdate = planteRepository.findAll().size();

        // Update the plante
        Plante updatedPlante = planteRepository.findById(plante.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlante are not directly saved in db
        em.detach(updatedPlante);
        updatedPlante
            .planteLibelle(UPDATED_PLANTE_LIBELLE)
            .racine(UPDATED_RACINE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restPlanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlante))
            )
            .andExpect(status().isOk());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
        Plante testPlante = planteList.get(planteList.size() - 1);
        assertThat(testPlante.getPlanteLibelle()).isEqualTo(UPDATED_PLANTE_LIBELLE);
        assertThat(testPlante.getRacine()).isEqualTo(UPDATED_RACINE);
        assertThat(testPlante.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPlante.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPlante() throws Exception {
        int databaseSizeBeforeUpdate = planteRepository.findAll().size();
        plante.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlante() throws Exception {
        int databaseSizeBeforeUpdate = planteRepository.findAll().size();
        plante.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlante() throws Exception {
        int databaseSizeBeforeUpdate = planteRepository.findAll().size();
        plante.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanteWithPatch() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        int databaseSizeBeforeUpdate = planteRepository.findAll().size();

        // Update the plante using partial update
        Plante partialUpdatedPlante = new Plante();
        partialUpdatedPlante.setId(plante.getId());

        partialUpdatedPlante
            .planteLibelle(UPDATED_PLANTE_LIBELLE)
            .racine(UPDATED_RACINE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restPlanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlante))
            )
            .andExpect(status().isOk());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
        Plante testPlante = planteList.get(planteList.size() - 1);
        assertThat(testPlante.getPlanteLibelle()).isEqualTo(UPDATED_PLANTE_LIBELLE);
        assertThat(testPlante.getRacine()).isEqualTo(UPDATED_RACINE);
        assertThat(testPlante.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPlante.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePlanteWithPatch() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        int databaseSizeBeforeUpdate = planteRepository.findAll().size();

        // Update the plante using partial update
        Plante partialUpdatedPlante = new Plante();
        partialUpdatedPlante.setId(plante.getId());

        partialUpdatedPlante
            .planteLibelle(UPDATED_PLANTE_LIBELLE)
            .racine(UPDATED_RACINE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restPlanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlante))
            )
            .andExpect(status().isOk());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
        Plante testPlante = planteList.get(planteList.size() - 1);
        assertThat(testPlante.getPlanteLibelle()).isEqualTo(UPDATED_PLANTE_LIBELLE);
        assertThat(testPlante.getRacine()).isEqualTo(UPDATED_RACINE);
        assertThat(testPlante.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPlante.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPlante() throws Exception {
        int databaseSizeBeforeUpdate = planteRepository.findAll().size();
        plante.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlante() throws Exception {
        int databaseSizeBeforeUpdate = planteRepository.findAll().size();
        plante.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlante() throws Exception {
        int databaseSizeBeforeUpdate = planteRepository.findAll().size();
        plante.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlante() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        int databaseSizeBeforeDelete = planteRepository.findAll().size();

        // Delete the plante
        restPlanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, plante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
