package com.backendstyle.myapp.web.rest;

import static com.backendstyle.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.backendstyle.myapp.IntegrationTest;
import com.backendstyle.myapp.domain.Cita;
import com.backendstyle.myapp.domain.enumeration.EstadoCitaEnum;
import com.backendstyle.myapp.repository.CitaRepository;
import com.backendstyle.myapp.service.dto.CitaDTO;
import com.backendstyle.myapp.service.mapper.CitaMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CitaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitaResourceIT {

    private static final Instant DEFAULT_FECHA_CITA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CITA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoCitaEnum DEFAULT_ESTADO = EstadoCitaEnum.PENDIENTE;
    private static final EstadoCitaEnum UPDATED_ESTADO = EstadoCitaEnum.CONFIRMADA;

    private static final Long DEFAULT_PERSONA_ID = 1L;
    private static final Long UPDATED_PERSONA_ID = 2L;

    private static final String DEFAULT_NOMBRE_PERSONA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PERSONA = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTABLECIMIENTO_ID = 1L;
    private static final Long UPDATED_ESTABLECIMIENTO_ID = 2L;

    private static final String DEFAULT_NOMBRE_ESTABLECIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ESTABLECIMIENTO = "BBBBBBBBBB";

    private static final Long DEFAULT_SERVICIO_ID = 1L;
    private static final Long UPDATED_SERVICIO_ID = 2L;

    private static final Long DEFAULT_EMPLEADO_ID = 1L;
    private static final Long UPDATED_EMPLEADO_ID = 2L;

    private static final String DEFAULT_NOMBRE_EMPLEADO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EMPLEADO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR_SERVICIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SERVICIO = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/citas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitaMockMvc;

    private Cita cita;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createEntity(EntityManager em) {
        Cita cita = new Cita()
            .fechaCita(DEFAULT_FECHA_CITA)
            .estado(DEFAULT_ESTADO)
            .personaId(DEFAULT_PERSONA_ID)
            .nombrePersona(DEFAULT_NOMBRE_PERSONA)
            .establecimientoId(DEFAULT_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(DEFAULT_NOMBRE_ESTABLECIMIENTO)
            .servicioId(DEFAULT_SERVICIO_ID)
            .empleadoId(DEFAULT_EMPLEADO_ID)
            .nombreEmpleado(DEFAULT_NOMBRE_EMPLEADO)
            .valorServicio(DEFAULT_VALOR_SERVICIO);
        return cita;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createUpdatedEntity(EntityManager em) {
        Cita cita = new Cita()
            .fechaCita(UPDATED_FECHA_CITA)
            .estado(UPDATED_ESTADO)
            .personaId(UPDATED_PERSONA_ID)
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(UPDATED_NOMBRE_ESTABLECIMIENTO)
            .servicioId(UPDATED_SERVICIO_ID)
            .empleadoId(UPDATED_EMPLEADO_ID)
            .nombreEmpleado(UPDATED_NOMBRE_EMPLEADO)
            .valorServicio(UPDATED_VALOR_SERVICIO);
        return cita;
    }

    @BeforeEach
    public void initTest() {
        cita = createEntity(em);
    }

    @Test
    @Transactional
    void createCita() throws Exception {
        int databaseSizeBeforeCreate = citaRepository.findAll().size();
        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);
        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isCreated());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeCreate + 1);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFechaCita()).isEqualTo(DEFAULT_FECHA_CITA);
        assertThat(testCita.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCita.getPersonaId()).isEqualTo(DEFAULT_PERSONA_ID);
        assertThat(testCita.getNombrePersona()).isEqualTo(DEFAULT_NOMBRE_PERSONA);
        assertThat(testCita.getEstablecimientoId()).isEqualTo(DEFAULT_ESTABLECIMIENTO_ID);
        assertThat(testCita.getNombreEstablecimiento()).isEqualTo(DEFAULT_NOMBRE_ESTABLECIMIENTO);
        assertThat(testCita.getServicioId()).isEqualTo(DEFAULT_SERVICIO_ID);
        assertThat(testCita.getEmpleadoId()).isEqualTo(DEFAULT_EMPLEADO_ID);
        assertThat(testCita.getNombreEmpleado()).isEqualTo(DEFAULT_NOMBRE_EMPLEADO);
        assertThat(testCita.getValorServicio()).isEqualByComparingTo(DEFAULT_VALOR_SERVICIO);
    }

    @Test
    @Transactional
    void createCitaWithExistingId() throws Exception {
        // Create the Cita with an existing ID
        cita.setId(1L);
        CitaDTO citaDTO = citaMapper.toDto(cita);

        int databaseSizeBeforeCreate = citaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCitas() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList
        restCitaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cita.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaCita").value(hasItem(DEFAULT_FECHA_CITA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].personaId").value(hasItem(DEFAULT_PERSONA_ID.intValue())))
            .andExpect(jsonPath("$.[*].nombrePersona").value(hasItem(DEFAULT_NOMBRE_PERSONA)))
            .andExpect(jsonPath("$.[*].establecimientoId").value(hasItem(DEFAULT_ESTABLECIMIENTO_ID.intValue())))
            .andExpect(jsonPath("$.[*].nombreEstablecimiento").value(hasItem(DEFAULT_NOMBRE_ESTABLECIMIENTO)))
            .andExpect(jsonPath("$.[*].servicioId").value(hasItem(DEFAULT_SERVICIO_ID.intValue())))
            .andExpect(jsonPath("$.[*].empleadoId").value(hasItem(DEFAULT_EMPLEADO_ID.intValue())))
            .andExpect(jsonPath("$.[*].nombreEmpleado").value(hasItem(DEFAULT_NOMBRE_EMPLEADO)))
            .andExpect(jsonPath("$.[*].valorServicio").value(hasItem(sameNumber(DEFAULT_VALOR_SERVICIO))));
    }

    @Test
    @Transactional
    void getCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get the cita
        restCitaMockMvc
            .perform(get(ENTITY_API_URL_ID, cita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cita.getId().intValue()))
            .andExpect(jsonPath("$.fechaCita").value(DEFAULT_FECHA_CITA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.personaId").value(DEFAULT_PERSONA_ID.intValue()))
            .andExpect(jsonPath("$.nombrePersona").value(DEFAULT_NOMBRE_PERSONA))
            .andExpect(jsonPath("$.establecimientoId").value(DEFAULT_ESTABLECIMIENTO_ID.intValue()))
            .andExpect(jsonPath("$.nombreEstablecimiento").value(DEFAULT_NOMBRE_ESTABLECIMIENTO))
            .andExpect(jsonPath("$.servicioId").value(DEFAULT_SERVICIO_ID.intValue()))
            .andExpect(jsonPath("$.empleadoId").value(DEFAULT_EMPLEADO_ID.intValue()))
            .andExpect(jsonPath("$.nombreEmpleado").value(DEFAULT_NOMBRE_EMPLEADO))
            .andExpect(jsonPath("$.valorServicio").value(sameNumber(DEFAULT_VALOR_SERVICIO)));
    }

    @Test
    @Transactional
    void getNonExistingCita() throws Exception {
        // Get the cita
        restCitaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeUpdate = citaRepository.findAll().size();

        // Update the cita
        Cita updatedCita = citaRepository.findById(cita.getId()).get();
        // Disconnect from session so that the updates on updatedCita are not directly saved in db
        em.detach(updatedCita);
        updatedCita
            .fechaCita(UPDATED_FECHA_CITA)
            .estado(UPDATED_ESTADO)
            .personaId(UPDATED_PERSONA_ID)
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(UPDATED_NOMBRE_ESTABLECIMIENTO)
            .servicioId(UPDATED_SERVICIO_ID)
            .empleadoId(UPDATED_EMPLEADO_ID)
            .nombreEmpleado(UPDATED_NOMBRE_EMPLEADO)
            .valorServicio(UPDATED_VALOR_SERVICIO);
        CitaDTO citaDTO = citaMapper.toDto(updatedCita);

        restCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFechaCita()).isEqualTo(UPDATED_FECHA_CITA);
        assertThat(testCita.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCita.getPersonaId()).isEqualTo(UPDATED_PERSONA_ID);
        assertThat(testCita.getNombrePersona()).isEqualTo(UPDATED_NOMBRE_PERSONA);
        assertThat(testCita.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
        assertThat(testCita.getNombreEstablecimiento()).isEqualTo(UPDATED_NOMBRE_ESTABLECIMIENTO);
        assertThat(testCita.getServicioId()).isEqualTo(UPDATED_SERVICIO_ID);
        assertThat(testCita.getEmpleadoId()).isEqualTo(UPDATED_EMPLEADO_ID);
        assertThat(testCita.getNombreEmpleado()).isEqualTo(UPDATED_NOMBRE_EMPLEADO);
        assertThat(testCita.getValorServicio()).isEqualByComparingTo(UPDATED_VALOR_SERVICIO);
    }

    @Test
    @Transactional
    void putNonExistingCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitaWithPatch() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeUpdate = citaRepository.findAll().size();

        // Update the cita using partial update
        Cita partialUpdatedCita = new Cita();
        partialUpdatedCita.setId(cita.getId());

        partialUpdatedCita
            .fechaCita(UPDATED_FECHA_CITA)
            .personaId(UPDATED_PERSONA_ID)
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(UPDATED_NOMBRE_ESTABLECIMIENTO)
            .servicioId(UPDATED_SERVICIO_ID)
            .empleadoId(UPDATED_EMPLEADO_ID)
            .valorServicio(UPDATED_VALOR_SERVICIO);

        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFechaCita()).isEqualTo(UPDATED_FECHA_CITA);
        assertThat(testCita.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCita.getPersonaId()).isEqualTo(UPDATED_PERSONA_ID);
        assertThat(testCita.getNombrePersona()).isEqualTo(UPDATED_NOMBRE_PERSONA);
        assertThat(testCita.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
        assertThat(testCita.getNombreEstablecimiento()).isEqualTo(UPDATED_NOMBRE_ESTABLECIMIENTO);
        assertThat(testCita.getServicioId()).isEqualTo(UPDATED_SERVICIO_ID);
        assertThat(testCita.getEmpleadoId()).isEqualTo(UPDATED_EMPLEADO_ID);
        assertThat(testCita.getNombreEmpleado()).isEqualTo(DEFAULT_NOMBRE_EMPLEADO);
        assertThat(testCita.getValorServicio()).isEqualByComparingTo(UPDATED_VALOR_SERVICIO);
    }

    @Test
    @Transactional
    void fullUpdateCitaWithPatch() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeUpdate = citaRepository.findAll().size();

        // Update the cita using partial update
        Cita partialUpdatedCita = new Cita();
        partialUpdatedCita.setId(cita.getId());

        partialUpdatedCita
            .fechaCita(UPDATED_FECHA_CITA)
            .estado(UPDATED_ESTADO)
            .personaId(UPDATED_PERSONA_ID)
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(UPDATED_NOMBRE_ESTABLECIMIENTO)
            .servicioId(UPDATED_SERVICIO_ID)
            .empleadoId(UPDATED_EMPLEADO_ID)
            .nombreEmpleado(UPDATED_NOMBRE_EMPLEADO)
            .valorServicio(UPDATED_VALOR_SERVICIO);

        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFechaCita()).isEqualTo(UPDATED_FECHA_CITA);
        assertThat(testCita.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCita.getPersonaId()).isEqualTo(UPDATED_PERSONA_ID);
        assertThat(testCita.getNombrePersona()).isEqualTo(UPDATED_NOMBRE_PERSONA);
        assertThat(testCita.getEstablecimientoId()).isEqualTo(UPDATED_ESTABLECIMIENTO_ID);
        assertThat(testCita.getNombreEstablecimiento()).isEqualTo(UPDATED_NOMBRE_ESTABLECIMIENTO);
        assertThat(testCita.getServicioId()).isEqualTo(UPDATED_SERVICIO_ID);
        assertThat(testCita.getEmpleadoId()).isEqualTo(UPDATED_EMPLEADO_ID);
        assertThat(testCita.getNombreEmpleado()).isEqualTo(UPDATED_NOMBRE_EMPLEADO);
        assertThat(testCita.getValorServicio()).isEqualByComparingTo(UPDATED_VALOR_SERVICIO);
    }

    @Test
    @Transactional
    void patchNonExistingCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeDelete = citaRepository.findAll().size();

        // Delete the cita
        restCitaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cita.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
