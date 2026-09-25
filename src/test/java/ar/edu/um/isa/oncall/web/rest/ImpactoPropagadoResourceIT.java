package ar.edu.um.isa.oncall.web.rest;

import static ar.edu.um.isa.oncall.domain.ImpactoPropagadoAsserts.*;
import static ar.edu.um.isa.oncall.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.isa.oncall.IntegrationTest;
import ar.edu.um.isa.oncall.domain.DependenciaDeServicio;
import ar.edu.um.isa.oncall.domain.ImpactoPropagado;
import ar.edu.um.isa.oncall.domain.Incidente;
import ar.edu.um.isa.oncall.domain.Servicio;
import ar.edu.um.isa.oncall.domain.enumeration.Severidad;
import ar.edu.um.isa.oncall.repository.ImpactoPropagadoRepository;
import ar.edu.um.isa.oncall.service.ImpactoPropagadoService;
import ar.edu.um.isa.oncall.service.dto.ImpactoPropagadoDTO;
import ar.edu.um.isa.oncall.service.mapper.ImpactoPropagadoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ImpactoPropagadoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ImpactoPropagadoResourceIT {

    private static final Severidad DEFAULT_SEVERIDAD = Severidad.SEV1;
    private static final Severidad UPDATED_SEVERIDAD = Severidad.SEV2;

    private static final Instant DEFAULT_DESDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DESDE = Instant.ofEpochMilli(1701729143509L);

    private static final Instant DEFAULT_HASTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HASTA = Instant.ofEpochMilli(1701729143509L);

    private static final String ENTITY_API_URL = "/api/impacto-propagados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ImpactoPropagadoRepository impactoPropagadoRepository;

    @Mock
    private ImpactoPropagadoRepository impactoPropagadoRepositoryMock;

    @Autowired
    private ImpactoPropagadoMapper impactoPropagadoMapper;

    @Mock
    private ImpactoPropagadoService impactoPropagadoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImpactoPropagadoMockMvc;

    private ImpactoPropagado impactoPropagado;

    private ImpactoPropagado insertedImpactoPropagado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpactoPropagado createEntity(EntityManager em) {
        ImpactoPropagado impactoPropagado = new ImpactoPropagado().severidad(DEFAULT_SEVERIDAD).desde(DEFAULT_DESDE).hasta(DEFAULT_HASTA);
        // Add required entity
        Incidente incidente;
        if (TestUtil.findAll(em, Incidente.class).isEmpty()) {
            incidente = IncidenteResourceIT.createEntity();
            em.persist(incidente);
            em.flush();
        } else {
            incidente = TestUtil.findAll(em, Incidente.class).get(0);
        }
        impactoPropagado.setIncidente(incidente);
        // Add required entity
        Servicio servicio;
        if (TestUtil.findAll(em, Servicio.class).isEmpty()) {
            servicio = ServicioResourceIT.createEntity(em);
            em.persist(servicio);
            em.flush();
        } else {
            servicio = TestUtil.findAll(em, Servicio.class).get(0);
        }
        impactoPropagado.setServicio(servicio);
        // Add required entity
        DependenciaDeServicio dependenciaDeServicio;
        if (TestUtil.findAll(em, DependenciaDeServicio.class).isEmpty()) {
            dependenciaDeServicio = DependenciaDeServicioResourceIT.createEntity(em);
            em.persist(dependenciaDeServicio);
            em.flush();
        } else {
            dependenciaDeServicio = TestUtil.findAll(em, DependenciaDeServicio.class).get(0);
        }
        impactoPropagado.setDependencia(dependenciaDeServicio);
        return impactoPropagado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpactoPropagado createUpdatedEntity(EntityManager em) {
        ImpactoPropagado updatedImpactoPropagado = new ImpactoPropagado()
            .severidad(UPDATED_SEVERIDAD)
            .desde(UPDATED_DESDE)
            .hasta(UPDATED_HASTA);
        // Add required entity
        Incidente incidente;
        if (TestUtil.findAll(em, Incidente.class).isEmpty()) {
            incidente = IncidenteResourceIT.createUpdatedEntity();
            em.persist(incidente);
            em.flush();
        } else {
            incidente = TestUtil.findAll(em, Incidente.class).get(0);
        }
        updatedImpactoPropagado.setIncidente(incidente);
        // Add required entity
        Servicio servicio;
        if (TestUtil.findAll(em, Servicio.class).isEmpty()) {
            servicio = ServicioResourceIT.createUpdatedEntity(em);
            em.persist(servicio);
            em.flush();
        } else {
            servicio = TestUtil.findAll(em, Servicio.class).get(0);
        }
        updatedImpactoPropagado.setServicio(servicio);
        // Add required entity
        DependenciaDeServicio dependenciaDeServicio;
        if (TestUtil.findAll(em, DependenciaDeServicio.class).isEmpty()) {
            dependenciaDeServicio = DependenciaDeServicioResourceIT.createUpdatedEntity(em);
            em.persist(dependenciaDeServicio);
            em.flush();
        } else {
            dependenciaDeServicio = TestUtil.findAll(em, DependenciaDeServicio.class).get(0);
        }
        updatedImpactoPropagado.setDependencia(dependenciaDeServicio);
        return updatedImpactoPropagado;
    }

    @BeforeEach
    void initTest() {
        impactoPropagado = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedImpactoPropagado != null) {
            impactoPropagadoRepository.delete(insertedImpactoPropagado);
            insertedImpactoPropagado = null;
        }
    }

    @Test
    @Transactional
    void createImpactoPropagado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ImpactoPropagado
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);
        var returnedImpactoPropagadoDTO = om.readValue(
            restImpactoPropagadoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impactoPropagadoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ImpactoPropagadoDTO.class
        );

        // Validate the ImpactoPropagado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedImpactoPropagado = impactoPropagadoMapper.toEntity(returnedImpactoPropagadoDTO);
        assertImpactoPropagadoUpdatableFieldsEquals(returnedImpactoPropagado, getPersistedImpactoPropagado(returnedImpactoPropagado));

        insertedImpactoPropagado = returnedImpactoPropagado;
    }

    @Test
    @Transactional
    void createImpactoPropagadoWithExistingId() throws Exception {
        // Create the ImpactoPropagado with an existing ID
        impactoPropagado.setId(1L);
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpactoPropagadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impactoPropagadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImpactoPropagado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSeveridadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        impactoPropagado.setSeveridad(null);

        // Create the ImpactoPropagado, which fails.
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        restImpactoPropagadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impactoPropagadoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesdeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        impactoPropagado.setDesde(null);

        // Create the ImpactoPropagado, which fails.
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        restImpactoPropagadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impactoPropagadoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllImpactoPropagados() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList
        restImpactoPropagadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impactoPropagado.getId().intValue())))
            .andExpect(jsonPath("$.[*].severidad").value(hasItem(DEFAULT_SEVERIDAD.toString())))
            .andExpect(jsonPath("$.[*].desde").value(hasItem(DEFAULT_DESDE.toString())))
            .andExpect(jsonPath("$.[*].hasta").value(hasItem(DEFAULT_HASTA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpactoPropagadosWithEagerRelationshipsIsEnabled() throws Exception {
        when(impactoPropagadoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpactoPropagadoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(impactoPropagadoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImpactoPropagadosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(impactoPropagadoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImpactoPropagadoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(impactoPropagadoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getImpactoPropagado() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get the impactoPropagado
        restImpactoPropagadoMockMvc
            .perform(get(ENTITY_API_URL_ID, impactoPropagado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(impactoPropagado.getId().intValue()))
            .andExpect(jsonPath("$.severidad").value(DEFAULT_SEVERIDAD.toString()))
            .andExpect(jsonPath("$.desde").value(DEFAULT_DESDE.toString()))
            .andExpect(jsonPath("$.hasta").value(DEFAULT_HASTA.toString()));
    }

    @Test
    @Transactional
    void getImpactoPropagadosByIdFiltering() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        Long id = impactoPropagado.getId();

        defaultImpactoPropagadoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultImpactoPropagadoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultImpactoPropagadoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosBySeveridadIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where severidad equals to
        defaultImpactoPropagadoFiltering("severidad.equals=" + DEFAULT_SEVERIDAD, "severidad.equals=" + UPDATED_SEVERIDAD);
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosBySeveridadIsInShouldWork() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where severidad in
        defaultImpactoPropagadoFiltering(
            "severidad.in=" + DEFAULT_SEVERIDAD + "," + UPDATED_SEVERIDAD,
            "severidad.in=" + UPDATED_SEVERIDAD
        );
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosBySeveridadIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where severidad is not null
        defaultImpactoPropagadoFiltering("severidad.specified=true", "severidad.specified=false");
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByDesdeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where desde equals to
        defaultImpactoPropagadoFiltering("desde.equals=" + DEFAULT_DESDE, "desde.equals=" + UPDATED_DESDE);
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByDesdeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where desde in
        defaultImpactoPropagadoFiltering("desde.in=" + DEFAULT_DESDE + "," + UPDATED_DESDE, "desde.in=" + UPDATED_DESDE);
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByDesdeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where desde is not null
        defaultImpactoPropagadoFiltering("desde.specified=true", "desde.specified=false");
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByHastaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where hasta equals to
        defaultImpactoPropagadoFiltering("hasta.equals=" + DEFAULT_HASTA, "hasta.equals=" + UPDATED_HASTA);
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByHastaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where hasta in
        defaultImpactoPropagadoFiltering("hasta.in=" + DEFAULT_HASTA + "," + UPDATED_HASTA, "hasta.in=" + UPDATED_HASTA);
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByHastaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        // Get all the impactoPropagadoList where hasta is not null
        defaultImpactoPropagadoFiltering("hasta.specified=true", "hasta.specified=false");
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByIncidenteIsEqualToSomething() throws Exception {
        Incidente incidente;
        if (TestUtil.findAll(em, Incidente.class).isEmpty()) {
            impactoPropagadoRepository.saveAndFlush(impactoPropagado);
            incidente = IncidenteResourceIT.createEntity();
        } else {
            incidente = TestUtil.findAll(em, Incidente.class).get(0);
        }
        em.persist(incidente);
        em.flush();
        impactoPropagado.setIncidente(incidente);
        impactoPropagadoRepository.saveAndFlush(impactoPropagado);
        Long incidenteId = incidente.getId();
        // Get all the impactoPropagadoList where incidente equals to incidenteId
        defaultImpactoPropagadoShouldBeFound("incidenteId.equals=" + incidenteId);

        // Get all the impactoPropagadoList where incidente equals to (incidenteId + 1)
        defaultImpactoPropagadoShouldNotBeFound("incidenteId.equals=" + (incidenteId + 1));
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByServicioIsEqualToSomething() throws Exception {
        Servicio servicio;
        if (TestUtil.findAll(em, Servicio.class).isEmpty()) {
            impactoPropagadoRepository.saveAndFlush(impactoPropagado);
            servicio = ServicioResourceIT.createEntity(em);
        } else {
            servicio = TestUtil.findAll(em, Servicio.class).get(0);
        }
        em.persist(servicio);
        em.flush();
        impactoPropagado.setServicio(servicio);
        impactoPropagadoRepository.saveAndFlush(impactoPropagado);
        Long servicioId = servicio.getId();
        // Get all the impactoPropagadoList where servicio equals to servicioId
        defaultImpactoPropagadoShouldBeFound("servicioId.equals=" + servicioId);

        // Get all the impactoPropagadoList where servicio equals to (servicioId + 1)
        defaultImpactoPropagadoShouldNotBeFound("servicioId.equals=" + (servicioId + 1));
    }

    @Test
    @Transactional
    void getAllImpactoPropagadosByDependenciaIsEqualToSomething() throws Exception {
        DependenciaDeServicio dependencia;
        if (TestUtil.findAll(em, DependenciaDeServicio.class).isEmpty()) {
            impactoPropagadoRepository.saveAndFlush(impactoPropagado);
            dependencia = DependenciaDeServicioResourceIT.createEntity(em);
        } else {
            dependencia = TestUtil.findAll(em, DependenciaDeServicio.class).get(0);
        }
        em.persist(dependencia);
        em.flush();
        impactoPropagado.setDependencia(dependencia);
        impactoPropagadoRepository.saveAndFlush(impactoPropagado);
        Long dependenciaId = dependencia.getId();
        // Get all the impactoPropagadoList where dependencia equals to dependenciaId
        defaultImpactoPropagadoShouldBeFound("dependenciaId.equals=" + dependenciaId);

        // Get all the impactoPropagadoList where dependencia equals to (dependenciaId + 1)
        defaultImpactoPropagadoShouldNotBeFound("dependenciaId.equals=" + (dependenciaId + 1));
    }

    private void defaultImpactoPropagadoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultImpactoPropagadoShouldBeFound(shouldBeFound);
        defaultImpactoPropagadoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImpactoPropagadoShouldBeFound(String filter) throws Exception {
        restImpactoPropagadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impactoPropagado.getId().intValue())))
            .andExpect(jsonPath("$.[*].severidad").value(hasItem(DEFAULT_SEVERIDAD.toString())))
            .andExpect(jsonPath("$.[*].desde").value(hasItem(DEFAULT_DESDE.toString())))
            .andExpect(jsonPath("$.[*].hasta").value(hasItem(DEFAULT_HASTA.toString())));

        // Check, that the count call also returns 1
        restImpactoPropagadoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImpactoPropagadoShouldNotBeFound(String filter) throws Exception {
        restImpactoPropagadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImpactoPropagadoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingImpactoPropagado() throws Exception {
        // Get the impactoPropagado
        restImpactoPropagadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImpactoPropagado() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impactoPropagado
        ImpactoPropagado updatedImpactoPropagado = impactoPropagadoRepository.findById(impactoPropagado.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedImpactoPropagado are not directly saved in db
        em.detach(updatedImpactoPropagado);
        updatedImpactoPropagado.severidad(UPDATED_SEVERIDAD).desde(UPDATED_DESDE).hasta(UPDATED_HASTA);
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(updatedImpactoPropagado);

        restImpactoPropagadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, impactoPropagadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impactoPropagadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ImpactoPropagado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedImpactoPropagadoToMatchAllProperties(updatedImpactoPropagado);
    }

    @Test
    @Transactional
    void putNonExistingImpactoPropagado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impactoPropagado.setId(longCount.incrementAndGet());

        // Create the ImpactoPropagado
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpactoPropagadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, impactoPropagadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impactoPropagadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpactoPropagado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImpactoPropagado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impactoPropagado.setId(longCount.incrementAndGet());

        // Create the ImpactoPropagado
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpactoPropagadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(impactoPropagadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpactoPropagado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImpactoPropagado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impactoPropagado.setId(longCount.incrementAndGet());

        // Create the ImpactoPropagado
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpactoPropagadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(impactoPropagadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpactoPropagado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImpactoPropagadoWithPatch() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impactoPropagado using partial update
        ImpactoPropagado partialUpdatedImpactoPropagado = new ImpactoPropagado();
        partialUpdatedImpactoPropagado.setId(impactoPropagado.getId());

        partialUpdatedImpactoPropagado.desde(UPDATED_DESDE).hasta(UPDATED_HASTA);

        restImpactoPropagadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpactoPropagado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpactoPropagado))
            )
            .andExpect(status().isOk());

        // Validate the ImpactoPropagado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpactoPropagadoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedImpactoPropagado, impactoPropagado),
            getPersistedImpactoPropagado(impactoPropagado)
        );
    }

    @Test
    @Transactional
    void fullUpdateImpactoPropagadoWithPatch() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the impactoPropagado using partial update
        ImpactoPropagado partialUpdatedImpactoPropagado = new ImpactoPropagado();
        partialUpdatedImpactoPropagado.setId(impactoPropagado.getId());

        partialUpdatedImpactoPropagado.severidad(UPDATED_SEVERIDAD).desde(UPDATED_DESDE).hasta(UPDATED_HASTA);

        restImpactoPropagadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImpactoPropagado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImpactoPropagado))
            )
            .andExpect(status().isOk());

        // Validate the ImpactoPropagado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImpactoPropagadoUpdatableFieldsEquals(
            partialUpdatedImpactoPropagado,
            getPersistedImpactoPropagado(partialUpdatedImpactoPropagado)
        );
    }

    @Test
    @Transactional
    void patchNonExistingImpactoPropagado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impactoPropagado.setId(longCount.incrementAndGet());

        // Create the ImpactoPropagado
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpactoPropagadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, impactoPropagadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impactoPropagadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpactoPropagado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImpactoPropagado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impactoPropagado.setId(longCount.incrementAndGet());

        // Create the ImpactoPropagado
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpactoPropagadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(impactoPropagadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImpactoPropagado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImpactoPropagado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        impactoPropagado.setId(longCount.incrementAndGet());

        // Create the ImpactoPropagado
        ImpactoPropagadoDTO impactoPropagadoDTO = impactoPropagadoMapper.toDto(impactoPropagado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImpactoPropagadoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(impactoPropagadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImpactoPropagado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImpactoPropagado() throws Exception {
        // Initialize the database
        insertedImpactoPropagado = impactoPropagadoRepository.saveAndFlush(impactoPropagado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the impactoPropagado
        restImpactoPropagadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, impactoPropagado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return impactoPropagadoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ImpactoPropagado getPersistedImpactoPropagado(ImpactoPropagado impactoPropagado) {
        return impactoPropagadoRepository.findById(impactoPropagado.getId()).orElseThrow();
    }

    protected void assertPersistedImpactoPropagadoToMatchAllProperties(ImpactoPropagado expectedImpactoPropagado) {
        assertImpactoPropagadoAllPropertiesEquals(expectedImpactoPropagado, getPersistedImpactoPropagado(expectedImpactoPropagado));
    }

    protected void assertPersistedImpactoPropagadoToMatchUpdatableProperties(ImpactoPropagado expectedImpactoPropagado) {
        assertImpactoPropagadoAllUpdatablePropertiesEquals(
            expectedImpactoPropagado,
            getPersistedImpactoPropagado(expectedImpactoPropagado)
        );
    }
}
