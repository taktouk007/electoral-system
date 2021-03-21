package com.kyubi.digital.web.rest;

import com.kyubi.digital.ElectoralSystemApp;
import com.kyubi.digital.domain.Figure;
import com.kyubi.digital.repository.FigureRepository;
import com.kyubi.digital.service.FigureService;
import com.kyubi.digital.service.dto.FigureDTO;
import com.kyubi.digital.service.mapper.FigureMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FigureResource} REST controller.
 */
@SpringBootTest(classes = ElectoralSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FigureResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_LINKED_IN = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    @Autowired
    private FigureRepository figureRepository;

    @Autowired
    private FigureMapper figureMapper;

    @Autowired
    private FigureService figureService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFigureMockMvc;

    private Figure figure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Figure createEntity(EntityManager em) {
        Figure figure = new Figure()
            .fullName(DEFAULT_FULL_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .linkedIn(DEFAULT_LINKED_IN)
            .facebook(DEFAULT_FACEBOOK)
            .twitter(DEFAULT_TWITTER);
        return figure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Figure createUpdatedEntity(EntityManager em) {
        Figure figure = new Figure()
            .fullName(UPDATED_FULL_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .linkedIn(UPDATED_LINKED_IN)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER);
        return figure;
    }

    @BeforeEach
    public void initTest() {
        figure = createEntity(em);
    }

    @Test
    @Transactional
    public void createFigure() throws Exception {
        int databaseSizeBeforeCreate = figureRepository.findAll().size();
        // Create the Figure
        FigureDTO figureDTO = figureMapper.toDto(figure);
        restFigureMockMvc.perform(post("/api/figures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(figureDTO)))
            .andExpect(status().isCreated());

        // Validate the Figure in the database
        List<Figure> figureList = figureRepository.findAll();
        assertThat(figureList).hasSize(databaseSizeBeforeCreate + 1);
        Figure testFigure = figureList.get(figureList.size() - 1);
        assertThat(testFigure.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testFigure.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testFigure.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testFigure.getLinkedIn()).isEqualTo(DEFAULT_LINKED_IN);
        assertThat(testFigure.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testFigure.getTwitter()).isEqualTo(DEFAULT_TWITTER);
    }

    @Test
    @Transactional
    public void createFigureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = figureRepository.findAll().size();

        // Create the Figure with an existing ID
        figure.setId(1L);
        FigureDTO figureDTO = figureMapper.toDto(figure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFigureMockMvc.perform(post("/api/figures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(figureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Figure in the database
        List<Figure> figureList = figureRepository.findAll();
        assertThat(figureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = figureRepository.findAll().size();
        // set the field null
        figure.setFullName(null);

        // Create the Figure, which fails.
        FigureDTO figureDTO = figureMapper.toDto(figure);


        restFigureMockMvc.perform(post("/api/figures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(figureDTO)))
            .andExpect(status().isBadRequest());

        List<Figure> figureList = figureRepository.findAll();
        assertThat(figureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFigures() throws Exception {
        // Initialize the database
        figureRepository.saveAndFlush(figure);

        // Get all the figureList
        restFigureMockMvc.perform(get("/api/figures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(figure.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].linkedIn").value(hasItem(DEFAULT_LINKED_IN)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)));
    }
    
    @Test
    @Transactional
    public void getFigure() throws Exception {
        // Initialize the database
        figureRepository.saveAndFlush(figure);

        // Get the figure
        restFigureMockMvc.perform(get("/api/figures/{id}", figure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(figure.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.linkedIn").value(DEFAULT_LINKED_IN))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER));
    }
    @Test
    @Transactional
    public void getNonExistingFigure() throws Exception {
        // Get the figure
        restFigureMockMvc.perform(get("/api/figures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFigure() throws Exception {
        // Initialize the database
        figureRepository.saveAndFlush(figure);

        int databaseSizeBeforeUpdate = figureRepository.findAll().size();

        // Update the figure
        Figure updatedFigure = figureRepository.findById(figure.getId()).get();
        // Disconnect from session so that the updates on updatedFigure are not directly saved in db
        em.detach(updatedFigure);
        updatedFigure
            .fullName(UPDATED_FULL_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .linkedIn(UPDATED_LINKED_IN)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER);
        FigureDTO figureDTO = figureMapper.toDto(updatedFigure);

        restFigureMockMvc.perform(put("/api/figures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(figureDTO)))
            .andExpect(status().isOk());

        // Validate the Figure in the database
        List<Figure> figureList = figureRepository.findAll();
        assertThat(figureList).hasSize(databaseSizeBeforeUpdate);
        Figure testFigure = figureList.get(figureList.size() - 1);
        assertThat(testFigure.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testFigure.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testFigure.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testFigure.getLinkedIn()).isEqualTo(UPDATED_LINKED_IN);
        assertThat(testFigure.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testFigure.getTwitter()).isEqualTo(UPDATED_TWITTER);
    }

    @Test
    @Transactional
    public void updateNonExistingFigure() throws Exception {
        int databaseSizeBeforeUpdate = figureRepository.findAll().size();

        // Create the Figure
        FigureDTO figureDTO = figureMapper.toDto(figure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFigureMockMvc.perform(put("/api/figures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(figureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Figure in the database
        List<Figure> figureList = figureRepository.findAll();
        assertThat(figureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFigure() throws Exception {
        // Initialize the database
        figureRepository.saveAndFlush(figure);

        int databaseSizeBeforeDelete = figureRepository.findAll().size();

        // Delete the figure
        restFigureMockMvc.perform(delete("/api/figures/{id}", figure.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Figure> figureList = figureRepository.findAll();
        assertThat(figureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
