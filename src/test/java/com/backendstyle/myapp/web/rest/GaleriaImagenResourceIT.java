package com.backendstyle.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.backendstyle.myapp.IntegrationTest;
import com.backendstyle.myapp.domain.GaleriaImagen;
import com.backendstyle.myapp.repository.GaleriaImagenRepository;
import com.backendstyle.myapp.service.dto.GaleriaImagenDTO;
import com.backendstyle.myapp.service.mapper.GaleriaImagenMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GaleriaImagenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GaleriaImagenResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_URL_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGEN = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTABLECIMIENTO_ID = 1L;
    private static final Long UPDATED_ESTABLECIMIENTO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/galeria-imagens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GaleriaImagenRepository galeriaImagenRepository;

    @Autowired
    private GaleriaImagenMapper galeriaImagenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGaleriaImagenMockMvc;

    private GaleriaImagen galeriaImagen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GaleriaImagen createEntity(EntityManager em) {
        GaleriaImagen galeriaImagen = new GaleriaImagen()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .urlImagen(DEFAULT_URL_IMAGEN)
            .establecimientoId(DEFAULT_ESTABLECIMIENTO_ID);
        return galeriaImagen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GaleriaImagen createUpdatedEntity(EntityManager em) {
        GaleriaImagen galeriaImagen = new GaleriaImagen()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .urlImagen(UPDATED_URL_IMAGEN)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        return galeriaImagen;
    }

    @BeforeEach
    public void initTest() {
        galeriaImagen = createEntity(em);
    }

    @Test
    @Transactional
    void createGaleriaImagen() throws Exception {
        int databaseSizeBeforeCreate = galeriaImagenRepository.findAll().size();
        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);
        restGaleriaImagenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeCreate + 1);
        GaleriaImagen testGaleriaImagen = galeriaImagenList.get(galeriaImagenList.size() - 1);
        assertThat(testGaleriaImagen.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testGaleriaImagen.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testGaleriaImagen.getUrlImagen()).isEqualTo(DEFAULT_URL_IMAGEN);
        assertThat(testGaleriaImagen.getEstablecimientoId()).isEqualTo(DEFAULT_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void createGaleriaImagenWithExistingId() throws Exception {
        // Create the GaleriaImagen with an existing ID
        galeriaImagen.setId(1L);
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        int databaseSizeBeforeCreate = galeriaImagenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGaleriaImagenMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGaleriaImagens() throws Exception {
        // Initialize the database
        galeriaImagenRepository.saveAndFlush(galeriaImagen);

        // Get all the galeriaImagenList
        restGaleriaImagenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galeriaImagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].urlImagen").value(hasItem(DEFAULT_URL_IMAGEN)))
            .andExpect(jsonPath("$.[*].establecimientoId").value(hasItem(DEFAULT_ESTABLECIMIENTO_ID.intValue())));
    }

    @Test
    @Transactional
    void getGaleriaImagen() throws Exception {
        // Initialize the database
        galeriaImagenRepository.saveAndFlush(galeriaImagen);

        // Get the galeriaImagen
        restGaleriaImagenMockMvc
            .perform(get(ENTITY_API_URL_ID, galeriaImagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(galeriaImagen.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.urlImagen").value(DEFAULT_URL_IMAGEN))
            .andExpect(jsonPath("$.establecimientoId").value(DEFAULT_ESTABLECIMIENTO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGaleriaImagen() throws Exception {
        // Get the galeriaImagen
        restGaleriaImagenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGaleriaImagen() throws Exception {
        // Initialize the database
        galeriaImagenRepository.saveAndFlush(galeriaImagen);

        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();

        // Update the galeriaImagen
        GaleriaImagen updatedGaleriaImagen = galeriaImagenRepository.findById(galeriaImagen.getId()).get();
        // Disconnect from session so that the updates on updatedGaleriaImagen are not directly saved in db
        em.detach(updatedGaleriaImagen);
        updatedGaleriaImagen
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .urlImagen(UPDATED_URL_IMAGEN)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(updatedGaleriaImagen);

        restGaleriaImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, galeriaImagenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isOk());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
        GaleriaImagen testGaleriaImagen = galeriaImagenList.get(galeriaImagenList.size() - 1);
        assertThat(testGaleriaImagen.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testGaleriaImagen.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testGaleriaImagen.getUrlImagen()).isEqualTo(UPDATED_URL_IMAGEN);
        assertThat(testGaleriaImagen.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void putNonExistingGaleriaImagen() throws Exception {
        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();
        galeriaImagen.setId(count.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, galeriaImagenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGaleriaImagen() throws Exception {
        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();
        galeriaImagen.setId(count.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGaleriaImagen() throws Exception {
        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();
        galeriaImagen.setId(count.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGaleriaImagenWithPatch() throws Exception {
        // Initialize the database
        galeriaImagenRepository.saveAndFlush(galeriaImagen);

        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();

        // Update the galeriaImagen using partial update
        GaleriaImagen partialUpdatedGaleriaImagen = new GaleriaImagen();
        partialUpdatedGaleriaImagen.setId(galeriaImagen.getId());

        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGaleriaImagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGaleriaImagen))
            )
            .andExpect(status().isOk());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
        GaleriaImagen testGaleriaImagen = galeriaImagenList.get(galeriaImagenList.size() - 1);
        assertThat(testGaleriaImagen.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testGaleriaImagen.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testGaleriaImagen.getUrlImagen()).isEqualTo(DEFAULT_URL_IMAGEN);
        assertThat(testGaleriaImagen.getEstablecimientoId()).isEqualTo(DEFAULT_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void fullUpdateGaleriaImagenWithPatch() throws Exception {
        // Initialize the database
        galeriaImagenRepository.saveAndFlush(galeriaImagen);

        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();

        // Update the galeriaImagen using partial update
        GaleriaImagen partialUpdatedGaleriaImagen = new GaleriaImagen();
        partialUpdatedGaleriaImagen.setId(galeriaImagen.getId());

        partialUpdatedGaleriaImagen
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .urlImagen(UPDATED_URL_IMAGEN)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);

        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGaleriaImagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGaleriaImagen))
            )
            .andExpect(status().isOk());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
        GaleriaImagen testGaleriaImagen = galeriaImagenList.get(galeriaImagenList.size() - 1);
        assertThat(testGaleriaImagen.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testGaleriaImagen.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testGaleriaImagen.getUrlImagen()).isEqualTo(UPDATED_URL_IMAGEN);
        assertThat(testGaleriaImagen.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void patchNonExistingGaleriaImagen() throws Exception {
        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();
        galeriaImagen.setId(count.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, galeriaImagenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGaleriaImagen() throws Exception {
        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();
        galeriaImagen.setId(count.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGaleriaImagen() throws Exception {
        int databaseSizeBeforeUpdate = galeriaImagenRepository.findAll().size();
        galeriaImagen.setId(count.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(galeriaImagenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GaleriaImagen in the database
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGaleriaImagen() throws Exception {
        // Initialize the database
        galeriaImagenRepository.saveAndFlush(galeriaImagen);

        int databaseSizeBeforeDelete = galeriaImagenRepository.findAll().size();

        // Delete the galeriaImagen
        restGaleriaImagenMockMvc
            .perform(delete(ENTITY_API_URL_ID, galeriaImagen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GaleriaImagen> galeriaImagenList = galeriaImagenRepository.findAll();
        assertThat(galeriaImagenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
