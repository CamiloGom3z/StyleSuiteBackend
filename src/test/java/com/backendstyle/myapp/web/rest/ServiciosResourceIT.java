package com.backendstyle.myapp.web.rest;

import static com.backendstyle.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.backendstyle.myapp.IntegrationTest;
import com.backendstyle.myapp.domain.Servicios;
import com.backendstyle.myapp.repository.ServiciosRepository;
import com.backendstyle.myapp.service.dto.ServiciosDTO;
import com.backendstyle.myapp.service.mapper.ServiciosMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ServiciosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiciosResourceIT {

    private static final BigDecimal DEFAULT_VALOR_SERVICIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SERVICIO = new BigDecimal(2);

    private static final String DEFAULT_TIPO_SERVICIO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SERVICIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTABLECIMIENTO_ID = 1L;
    private static final Long UPDATED_ESTABLECIMIENTO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Autowired
    private ServiciosMapper serviciosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiciosMockMvc;

    private Servicios servicios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicios createEntity(EntityManager em) {
        Servicios servicios = new Servicios()
            .valorServicio(DEFAULT_VALOR_SERVICIO)
            .tipoServicio(DEFAULT_TIPO_SERVICIO)
            .descripcion(DEFAULT_DESCRIPCION)
            .establecimientoId(DEFAULT_ESTABLECIMIENTO_ID);
        return servicios;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicios createUpdatedEntity(EntityManager em) {
        Servicios servicios = new Servicios()
            .valorServicio(UPDATED_VALOR_SERVICIO)
            .tipoServicio(UPDATED_TIPO_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        return servicios;
    }

    @BeforeEach
    public void initTest() {
        servicios = createEntity(em);
    }

    @Test
    @Transactional
    void createServicios() throws Exception {
        int databaseSizeBeforeCreate = serviciosRepository.findAll().size();
        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);
        restServiciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviciosDTO)))
            .andExpect(status().isCreated());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeCreate + 1);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getValorServicio()).isEqualByComparingTo(DEFAULT_VALOR_SERVICIO);
        assertThat(testServicios.getTipoServicio()).isEqualTo(DEFAULT_TIPO_SERVICIO);
        assertThat(testServicios.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testServicios.getEstablecimientoId()).isEqualTo(DEFAULT_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void createServiciosWithExistingId() throws Exception {
        // Create the Servicios with an existing ID
        servicios.setId(1L);
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        int databaseSizeBeforeCreate = serviciosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviciosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList
        restServiciosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorServicio").value(hasItem(sameNumber(DEFAULT_VALOR_SERVICIO))))
            .andExpect(jsonPath("$.[*].tipoServicio").value(hasItem(DEFAULT_TIPO_SERVICIO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].establecimientoId").value(hasItem(DEFAULT_ESTABLECIMIENTO_ID.intValue())));
    }

    @Test
    @Transactional
    void getServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get the servicios
        restServiciosMockMvc
            .perform(get(ENTITY_API_URL_ID, servicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicios.getId().intValue()))
            .andExpect(jsonPath("$.valorServicio").value(sameNumber(DEFAULT_VALOR_SERVICIO)))
            .andExpect(jsonPath("$.tipoServicio").value(DEFAULT_TIPO_SERVICIO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.establecimientoId").value(DEFAULT_ESTABLECIMIENTO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingServicios() throws Exception {
        // Get the servicios
        restServiciosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Update the servicios
        Servicios updatedServicios = serviciosRepository.findById(servicios.getId()).get();
        // Disconnect from session so that the updates on updatedServicios are not directly saved in db
        em.detach(updatedServicios);
        updatedServicios
            .valorServicio(UPDATED_VALOR_SERVICIO)
            .tipoServicio(UPDATED_TIPO_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(updatedServicios);

        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviciosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviciosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getValorServicio()).isEqualByComparingTo(UPDATED_VALOR_SERVICIO);
        assertThat(testServicios.getTipoServicio()).isEqualTo(UPDATED_TIPO_SERVICIO);
        assertThat(testServicios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testServicios.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void putNonExistingServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviciosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviciosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiciosWithPatch() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Update the servicios using partial update
        Servicios partialUpdatedServicios = new Servicios();
        partialUpdatedServicios.setId(servicios.getId());

        partialUpdatedServicios.tipoServicio(UPDATED_TIPO_SERVICIO).establecimientoId(UPDATED_ESTABLECIMIENTO_ID);

        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServicios))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getValorServicio()).isEqualByComparingTo(DEFAULT_VALOR_SERVICIO);
        assertThat(testServicios.getTipoServicio()).isEqualTo(UPDATED_TIPO_SERVICIO);
        assertThat(testServicios.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testServicios.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void fullUpdateServiciosWithPatch() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Update the servicios using partial update
        Servicios partialUpdatedServicios = new Servicios();
        partialUpdatedServicios.setId(servicios.getId());

        partialUpdatedServicios
            .valorServicio(UPDATED_VALOR_SERVICIO)
            .tipoServicio(UPDATED_TIPO_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);

        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServicios))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getValorServicio()).isEqualByComparingTo(UPDATED_VALOR_SERVICIO);
        assertThat(testServicios.getTipoServicio()).isEqualTo(UPDATED_TIPO_SERVICIO);
        assertThat(testServicios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testServicios.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
    }

    @Test
    @Transactional
    void patchNonExistingServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviciosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();
        servicios.setId(count.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(serviciosDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        int databaseSizeBeforeDelete = serviciosRepository.findAll().size();

        // Delete the servicios
        restServiciosMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
