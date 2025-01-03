package com.backendstyle.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.backendstyle.myapp.IntegrationTest;
import com.backendstyle.myapp.domain.CategoriaProducto;
import com.backendstyle.myapp.repository.CategoriaProductoRepository;
import com.backendstyle.myapp.service.dto.CategoriaProductoDTO;
import com.backendstyle.myapp.service.mapper.CategoriaProductoMapper;
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
 * Integration tests for the {@link CategoriaProductoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriaProductoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTABLECIMIENTO_ID = 1L;
    private static final Long UPDATED_ESTABLECIMIENTO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/categoria-productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private CategoriaProductoMapper categoriaProductoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaProductoMockMvc;

    private CategoriaProducto categoriaProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaProducto createEntity(EntityManager em) {
        CategoriaProducto categoriaProducto = new CategoriaProducto().nombre(DEFAULT_NOMBRE).establecimientoId(DEFAULT_ESTABLECIMIENTO_ID);
        return categoriaProducto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaProducto createUpdatedEntity(EntityManager em) {
        CategoriaProducto categoriaProducto = new CategoriaProducto().nombre(UPDATED_NOMBRE).establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        return categoriaProducto;
    }

    @BeforeEach
    public void initTest() {
        categoriaProducto = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoriaProducto() throws Exception {
        int databaseSizeBeforeCreate = categoriaProductoRepository.findAll().size();
        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);
        restCategoriaProductoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaProducto testCategoriaProducto = categoriaProductoList.get(categoriaProductoList.size() - 1);
        assertThat(testCategoriaProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCategoriaProducto.getEstablecimientoId()).isEqualTo(DEFAULT_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void createCategoriaProductoWithExistingId() throws Exception {
        // Create the CategoriaProducto with an existing ID
        categoriaProducto.setId(1L);
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        int databaseSizeBeforeCreate = categoriaProductoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaProductoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategoriaProductos() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList
        restCategoriaProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].establecimientoId").value(hasItem(DEFAULT_ESTABLECIMIENTO_ID.intValue())));
    }

    @Test
    @Transactional
    void getCategoriaProducto() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get the categoriaProducto
        restCategoriaProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, categoriaProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaProducto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.establecimientoId").value(DEFAULT_ESTABLECIMIENTO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCategoriaProducto() throws Exception {
        // Get the categoriaProducto
        restCategoriaProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategoriaProducto() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();

        // Update the categoriaProducto
        CategoriaProducto updatedCategoriaProducto = categoriaProductoRepository.findById(categoriaProducto.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaProducto are not directly saved in db
        em.detach(updatedCategoriaProducto);
        updatedCategoriaProducto.nombre(UPDATED_NOMBRE).establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(updatedCategoriaProducto);

        restCategoriaProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaProducto testCategoriaProducto = categoriaProductoList.get(categoriaProductoList.size() - 1);
        assertThat(testCategoriaProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCategoriaProducto.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void putNonExistingCategoriaProducto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();
        categoriaProducto.setId(count.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoriaProducto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();
        categoriaProducto.setId(count.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoriaProducto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();
        categoriaProducto.setId(count.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriaProductoWithPatch() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();

        // Update the categoriaProducto using partial update
        CategoriaProducto partialUpdatedCategoriaProducto = new CategoriaProducto();
        partialUpdatedCategoriaProducto.setId(categoriaProducto.getId());

        partialUpdatedCategoriaProducto.establecimientoId(UPDATED_ESTABLECIMIENTO_ID);

        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaProducto))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaProducto testCategoriaProducto = categoriaProductoList.get(categoriaProductoList.size() - 1);
        assertThat(testCategoriaProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCategoriaProducto.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void fullUpdateCategoriaProductoWithPatch() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();

        // Update the categoriaProducto using partial update
        CategoriaProducto partialUpdatedCategoriaProducto = new CategoriaProducto();
        partialUpdatedCategoriaProducto.setId(categoriaProducto.getId());

        partialUpdatedCategoriaProducto.nombre(UPDATED_NOMBRE).establecimientoId(UPDATED_ESTABLECIMIENTO_ID);

        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaProducto))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaProducto testCategoriaProducto = categoriaProductoList.get(categoriaProductoList.size() - 1);
        assertThat(testCategoriaProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCategoriaProducto.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCategoriaProducto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();
        categoriaProducto.setId(count.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriaProductoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoriaProducto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();
        categoriaProducto.setId(count.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoriaProducto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();
        categoriaProducto.setId(count.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaProductoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoriaProducto() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        int databaseSizeBeforeDelete = categoriaProductoRepository.findAll().size();

        // Delete the categoriaProducto
        restCategoriaProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoriaProducto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
