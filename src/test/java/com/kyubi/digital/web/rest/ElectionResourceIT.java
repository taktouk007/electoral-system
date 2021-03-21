package com.kyubi.digital.web.rest;

import com.kyubi.digital.ElectoralSystemApp;
import com.kyubi.digital.domain.Election;
import com.kyubi.digital.repository.ElectionRepository;
import com.kyubi.digital.service.ElectionService;
import com.kyubi.digital.service.dto.ElectionDTO;
import com.kyubi.digital.service.mapper.ElectionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyubi.digital.domain.enumeration.Scope;
/**
 * Integration tests for the {@link ElectionResource} REST controller.
 */
@SpringBootTest(classes = ElectoralSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ElectionResourceIT {

    private static final String DEFAULT_TARGET_FUNCTION = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_FUNCTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Scope DEFAULT_SCOPE = Scope.LOCAL;
    private static final Scope UPDATED_SCOPE = Scope.REGIONAL;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private ElectionMapper electionMapper;

    @Autowired
    private ElectionService electionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElectionMockMvc;

    private Election election;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Election createEntity(EntityManager em) {
        Election election = new Election()
            .targetFunction(DEFAULT_TARGET_FUNCTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .scope(DEFAULT_SCOPE)
            .city(DEFAULT_CITY)
            .region(DEFAULT_REGION)
            .country(DEFAULT_COUNTRY);
        return election;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Election createUpdatedEntity(EntityManager em) {
        Election election = new Election()
            .targetFunction(UPDATED_TARGET_FUNCTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .scope(UPDATED_SCOPE)
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .country(UPDATED_COUNTRY);
        return election;
    }

    @BeforeEach
    public void initTest() {
        election = createEntity(em);
    }

    @Test
    @Transactional
    public void createElection() throws Exception {
        int databaseSizeBeforeCreate = electionRepository.findAll().size();
        // Create the Election
        ElectionDTO electionDTO = electionMapper.toDto(election);
        restElectionMockMvc.perform(post("/api/elections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electionDTO)))
            .andExpect(status().isCreated());

        // Validate the Election in the database
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeCreate + 1);
        Election testElection = electionList.get(electionList.size() - 1);
        assertThat(testElection.getTargetFunction()).isEqualTo(DEFAULT_TARGET_FUNCTION);
        assertThat(testElection.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testElection.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testElection.getScope()).isEqualTo(DEFAULT_SCOPE);
        assertThat(testElection.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testElection.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testElection.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createElectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = electionRepository.findAll().size();

        // Create the Election with an existing ID
        election.setId(1L);
        ElectionDTO electionDTO = electionMapper.toDto(election);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElectionMockMvc.perform(post("/api/elections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Election in the database
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTargetFunctionIsRequired() throws Exception {
        int databaseSizeBeforeTest = electionRepository.findAll().size();
        // set the field null
        election.setTargetFunction(null);

        // Create the Election, which fails.
        ElectionDTO electionDTO = electionMapper.toDto(election);


        restElectionMockMvc.perform(post("/api/elections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electionDTO)))
            .andExpect(status().isBadRequest());

        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllElections() throws Exception {
        // Initialize the database
        electionRepository.saveAndFlush(election);

        // Get all the electionList
        restElectionMockMvc.perform(get("/api/elections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(election.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetFunction").value(hasItem(DEFAULT_TARGET_FUNCTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].scope").value(hasItem(DEFAULT_SCOPE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
    
    @Test
    @Transactional
    public void getElection() throws Exception {
        // Initialize the database
        electionRepository.saveAndFlush(election);

        // Get the election
        restElectionMockMvc.perform(get("/api/elections/{id}", election.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(election.getId().intValue()))
            .andExpect(jsonPath("$.targetFunction").value(DEFAULT_TARGET_FUNCTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.scope").value(DEFAULT_SCOPE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }
    @Test
    @Transactional
    public void getNonExistingElection() throws Exception {
        // Get the election
        restElectionMockMvc.perform(get("/api/elections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElection() throws Exception {
        // Initialize the database
        electionRepository.saveAndFlush(election);

        int databaseSizeBeforeUpdate = electionRepository.findAll().size();

        // Update the election
        Election updatedElection = electionRepository.findById(election.getId()).get();
        // Disconnect from session so that the updates on updatedElection are not directly saved in db
        em.detach(updatedElection);
        updatedElection
            .targetFunction(UPDATED_TARGET_FUNCTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .scope(UPDATED_SCOPE)
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .country(UPDATED_COUNTRY);
        ElectionDTO electionDTO = electionMapper.toDto(updatedElection);

        restElectionMockMvc.perform(put("/api/elections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electionDTO)))
            .andExpect(status().isOk());

        // Validate the Election in the database
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeUpdate);
        Election testElection = electionList.get(electionList.size() - 1);
        assertThat(testElection.getTargetFunction()).isEqualTo(UPDATED_TARGET_FUNCTION);
        assertThat(testElection.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testElection.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testElection.getScope()).isEqualTo(UPDATED_SCOPE);
        assertThat(testElection.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testElection.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testElection.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingElection() throws Exception {
        int databaseSizeBeforeUpdate = electionRepository.findAll().size();

        // Create the Election
        ElectionDTO electionDTO = electionMapper.toDto(election);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElectionMockMvc.perform(put("/api/elections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(electionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Election in the database
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElection() throws Exception {
        // Initialize the database
        electionRepository.saveAndFlush(election);

        int databaseSizeBeforeDelete = electionRepository.findAll().size();

        // Delete the election
        restElectionMockMvc.perform(delete("/api/elections/{id}", election.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
