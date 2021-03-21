package com.kyubi.digital.web.rest;

import com.kyubi.digital.ElectoralSystemApp;
import com.kyubi.digital.domain.UserApp;
import com.kyubi.digital.repository.UserAppRepository;
import com.kyubi.digital.service.UserAppService;
import com.kyubi.digital.service.dto.UserAppDTO;
import com.kyubi.digital.service.mapper.UserAppMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserAppResource} REST controller.
 */
@SpringBootTest(classes = ElectoralSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserAppResourceIT {

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private UserAppMapper userAppMapper;

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAppMockMvc;

    private UserApp userApp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApp createEntity(EntityManager em) {
        UserApp userApp = new UserApp()
            .city(DEFAULT_CITY)
            .region(DEFAULT_REGION)
            .country(DEFAULT_COUNTRY);
        return userApp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApp createUpdatedEntity(EntityManager em) {
        UserApp userApp = new UserApp()
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .country(UPDATED_COUNTRY);
        return userApp;
    }

    @BeforeEach
    public void initTest() {
        userApp = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserApp() throws Exception {
        int databaseSizeBeforeCreate = userAppRepository.findAll().size();
        // Create the UserApp
        UserAppDTO userAppDTO = userAppMapper.toDto(userApp);
        restUserAppMockMvc.perform(post("/api/user-apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAppDTO)))
            .andExpect(status().isCreated());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeCreate + 1);
        UserApp testUserApp = userAppList.get(userAppList.size() - 1);
        assertThat(testUserApp.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testUserApp.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testUserApp.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createUserAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAppRepository.findAll().size();

        // Create the UserApp with an existing ID
        userApp.setId(1L);
        UserAppDTO userAppDTO = userAppMapper.toDto(userApp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAppMockMvc.perform(post("/api/user-apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAppDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserApps() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        // Get all the userAppList
        restUserAppMockMvc.perform(get("/api/user-apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
    
    @Test
    @Transactional
    public void getUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        // Get the userApp
        restUserAppMockMvc.perform(get("/api/user-apps/{id}", userApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userApp.getId().intValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }
    @Test
    @Transactional
    public void getNonExistingUserApp() throws Exception {
        // Get the userApp
        restUserAppMockMvc.perform(get("/api/user-apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        int databaseSizeBeforeUpdate = userAppRepository.findAll().size();

        // Update the userApp
        UserApp updatedUserApp = userAppRepository.findById(userApp.getId()).get();
        // Disconnect from session so that the updates on updatedUserApp are not directly saved in db
        em.detach(updatedUserApp);
        updatedUserApp
            .city(UPDATED_CITY)
            .region(UPDATED_REGION)
            .country(UPDATED_COUNTRY);
        UserAppDTO userAppDTO = userAppMapper.toDto(updatedUserApp);

        restUserAppMockMvc.perform(put("/api/user-apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAppDTO)))
            .andExpect(status().isOk());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeUpdate);
        UserApp testUserApp = userAppList.get(userAppList.size() - 1);
        assertThat(testUserApp.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testUserApp.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testUserApp.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserApp() throws Exception {
        int databaseSizeBeforeUpdate = userAppRepository.findAll().size();

        // Create the UserApp
        UserAppDTO userAppDTO = userAppMapper.toDto(userApp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAppMockMvc.perform(put("/api/user-apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAppDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        int databaseSizeBeforeDelete = userAppRepository.findAll().size();

        // Delete the userApp
        restUserAppMockMvc.perform(delete("/api/user-apps/{id}", userApp.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
