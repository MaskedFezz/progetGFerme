package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TypePlante;
import com.mycompany.myapp.repository.TypePlanteRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link TypePlanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypePlanteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Long DEFAULT_HUMIDITE_MAX = 1L;
    private static final Long UPDATED_HUMIDITE_MAX = 2L;

    private static final Long DEFAULT_HUMIDITE_MIN = 1L;
    private static final Long UPDATED_HUMIDITE_MIN = 2L;

    private static final Long DEFAULT_TEMPERATURE = 1L;
    private static final Long UPDATED_TEMPERATURE = 2L;

    private static final String ENTITY_API_URL = "/api/type-plantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypePlanteRepository typePlanteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypePlanteMockMvc;

    private TypePlante typePlante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePlante createEntity(EntityManager em) {
        TypePlante typePlante = new TypePlante()
            .nom(DEFAULT_NOM)
            .humiditeMax(DEFAULT_HUMIDITE_MAX)
            .humiditeMin(DEFAULT_HUMIDITE_MIN)
            .temperature(DEFAULT_TEMPERATURE);
        return typePlante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePlante createUpdatedEntity(EntityManager em) {
        TypePlante typePlante = new TypePlante()
            .nom(UPDATED_NOM)
            .humiditeMax(UPDATED_HUMIDITE_MAX)
            .humiditeMin(UPDATED_HUMIDITE_MIN)
            .temperature(UPDATED_TEMPERATURE);
        return typePlante;
    }

    @BeforeEach
    public void initTest() {
        typePlante = createEntity(em);
    }

    @Test
    @Transactional
    void createTypePlante() throws Exception {
        int databaseSizeBeforeCreate = typePlanteRepository.findAll().size();
        // Create the TypePlante
        restTypePlanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typePlante)))
            .andExpect(status().isCreated());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeCreate + 1);
        TypePlante testTypePlante = typePlanteList.get(typePlanteList.size() - 1);
        assertThat(testTypePlante.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTypePlante.getHumiditeMax()).isEqualTo(DEFAULT_HUMIDITE_MAX);
        assertThat(testTypePlante.getHumiditeMin()).isEqualTo(DEFAULT_HUMIDITE_MIN);
        assertThat(testTypePlante.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
    }

    @Test
    @Transactional
    void createTypePlanteWithExistingId() throws Exception {
        // Create the TypePlante with an existing ID
        typePlante.setId(1L);

        int databaseSizeBeforeCreate = typePlanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePlanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typePlante)))
            .andExpect(status().isBadRequest());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypePlantes() throws Exception {
        // Initialize the database
        typePlanteRepository.saveAndFlush(typePlante);

        // Get all the typePlanteList
        restTypePlanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePlante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].humiditeMax").value(hasItem(DEFAULT_HUMIDITE_MAX.intValue())))
            .andExpect(jsonPath("$.[*].humiditeMin").value(hasItem(DEFAULT_HUMIDITE_MIN.intValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.intValue())));
    }

    @Test
    @Transactional
    void getTypePlante() throws Exception {
        // Initialize the database
        typePlanteRepository.saveAndFlush(typePlante);

        // Get the typePlante
        restTypePlanteMockMvc
            .perform(get(ENTITY_API_URL_ID, typePlante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePlante.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.humiditeMax").value(DEFAULT_HUMIDITE_MAX.intValue()))
            .andExpect(jsonPath("$.humiditeMin").value(DEFAULT_HUMIDITE_MIN.intValue()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTypePlante() throws Exception {
        // Get the typePlante
        restTypePlanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypePlante() throws Exception {
        // Initialize the database
        typePlanteRepository.saveAndFlush(typePlante);

        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();

        // Update the typePlante
        TypePlante updatedTypePlante = typePlanteRepository.findById(typePlante.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypePlante are not directly saved in db
        em.detach(updatedTypePlante);
        updatedTypePlante
            .nom(UPDATED_NOM)
            .humiditeMax(UPDATED_HUMIDITE_MAX)
            .humiditeMin(UPDATED_HUMIDITE_MIN)
            .temperature(UPDATED_TEMPERATURE);

        restTypePlanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypePlante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypePlante))
            )
            .andExpect(status().isOk());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
        TypePlante testTypePlante = typePlanteList.get(typePlanteList.size() - 1);
        assertThat(testTypePlante.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypePlante.getHumiditeMax()).isEqualTo(UPDATED_HUMIDITE_MAX);
        assertThat(testTypePlante.getHumiditeMin()).isEqualTo(UPDATED_HUMIDITE_MIN);
        assertThat(testTypePlante.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void putNonExistingTypePlante() throws Exception {
        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();
        typePlante.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePlanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typePlante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typePlante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypePlante() throws Exception {
        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();
        typePlante.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypePlanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typePlante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypePlante() throws Exception {
        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();
        typePlante.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypePlanteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typePlante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypePlanteWithPatch() throws Exception {
        // Initialize the database
        typePlanteRepository.saveAndFlush(typePlante);

        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();

        // Update the typePlante using partial update
        TypePlante partialUpdatedTypePlante = new TypePlante();
        partialUpdatedTypePlante.setId(typePlante.getId());

        partialUpdatedTypePlante.nom(UPDATED_NOM).humiditeMax(UPDATED_HUMIDITE_MAX).temperature(UPDATED_TEMPERATURE);

        restTypePlanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypePlante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypePlante))
            )
            .andExpect(status().isOk());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
        TypePlante testTypePlante = typePlanteList.get(typePlanteList.size() - 1);
        assertThat(testTypePlante.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypePlante.getHumiditeMax()).isEqualTo(UPDATED_HUMIDITE_MAX);
        assertThat(testTypePlante.getHumiditeMin()).isEqualTo(DEFAULT_HUMIDITE_MIN);
        assertThat(testTypePlante.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void fullUpdateTypePlanteWithPatch() throws Exception {
        // Initialize the database
        typePlanteRepository.saveAndFlush(typePlante);

        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();

        // Update the typePlante using partial update
        TypePlante partialUpdatedTypePlante = new TypePlante();
        partialUpdatedTypePlante.setId(typePlante.getId());

        partialUpdatedTypePlante
            .nom(UPDATED_NOM)
            .humiditeMax(UPDATED_HUMIDITE_MAX)
            .humiditeMin(UPDATED_HUMIDITE_MIN)
            .temperature(UPDATED_TEMPERATURE);

        restTypePlanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypePlante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypePlante))
            )
            .andExpect(status().isOk());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
        TypePlante testTypePlante = typePlanteList.get(typePlanteList.size() - 1);
        assertThat(testTypePlante.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypePlante.getHumiditeMax()).isEqualTo(UPDATED_HUMIDITE_MAX);
        assertThat(testTypePlante.getHumiditeMin()).isEqualTo(UPDATED_HUMIDITE_MIN);
        assertThat(testTypePlante.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void patchNonExistingTypePlante() throws Exception {
        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();
        typePlante.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePlanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typePlante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typePlante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypePlante() throws Exception {
        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();
        typePlante.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypePlanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typePlante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypePlante() throws Exception {
        int databaseSizeBeforeUpdate = typePlanteRepository.findAll().size();
        typePlante.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypePlanteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typePlante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypePlante in the database
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypePlante() throws Exception {
        // Initialize the database
        typePlanteRepository.saveAndFlush(typePlante);

        int databaseSizeBeforeDelete = typePlanteRepository.findAll().size();

        // Delete the typePlante
        restTypePlanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, typePlante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePlante> typePlanteList = typePlanteRepository.findAll();
        assertThat(typePlanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
