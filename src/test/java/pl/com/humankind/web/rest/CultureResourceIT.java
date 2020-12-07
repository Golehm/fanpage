package pl.com.humankind.web.rest;

import pl.com.humankind.FanpageApp;
import pl.com.humankind.domain.Culture;
import pl.com.humankind.repository.CultureRepository;

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

import pl.com.humankind.domain.enumeration.Era;
import pl.com.humankind.domain.enumeration.Kind;
/**
 * Integration tests for the {@link CultureResource} REST controller.
 */
@SpringBootTest(classes = FanpageApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CultureResourceIT {

    private static final Era DEFAULT_ERA = Era.ANCIENT;
    private static final Era UPDATED_ERA = Era.CLASSICAL;

    private static final Kind DEFAULT_KIND = Kind.EXPANSIONIST;
    private static final Kind UPDATED_KIND = Kind.SCIENTIST;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_QUARTER = "AAAAAAAAAA";
    private static final String UPDATED_QUARTER = "BBBBBBBBBB";

    private static final String DEFAULT_QUARTER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_QUARTER_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCultureMockMvc;

    private Culture culture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Culture createEntity(EntityManager em) {
        Culture culture = new Culture()
            .era(DEFAULT_ERA)
            .kind(DEFAULT_KIND)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .name(DEFAULT_NAME)
            .unit(DEFAULT_UNIT)
            .unitDescription(DEFAULT_UNIT_DESCRIPTION)
            .quarter(DEFAULT_QUARTER)
            .quarterDescription(DEFAULT_QUARTER_DESCRIPTION);
        return culture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Culture createUpdatedEntity(EntityManager em) {
        Culture culture = new Culture()
            .era(UPDATED_ERA)
            .kind(UPDATED_KIND)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .name(UPDATED_NAME)
            .unit(UPDATED_UNIT)
            .unitDescription(UPDATED_UNIT_DESCRIPTION)
            .quarter(UPDATED_QUARTER)
            .quarterDescription(UPDATED_QUARTER_DESCRIPTION);
        return culture;
    }

    @BeforeEach
    public void initTest() {
        culture = createEntity(em);
    }

    @Test
    @Transactional
    public void createCulture() throws Exception {
        int databaseSizeBeforeCreate = cultureRepository.findAll().size();
        // Create the Culture
        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(culture)))
            .andExpect(status().isCreated());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeCreate + 1);
        Culture testCulture = cultureList.get(cultureList.size() - 1);
        assertThat(testCulture.getEra()).isEqualTo(DEFAULT_ERA);
        assertThat(testCulture.getKind()).isEqualTo(DEFAULT_KIND);
        assertThat(testCulture.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCulture.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testCulture.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCulture.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testCulture.getUnitDescription()).isEqualTo(DEFAULT_UNIT_DESCRIPTION);
        assertThat(testCulture.getQuarter()).isEqualTo(DEFAULT_QUARTER);
        assertThat(testCulture.getQuarterDescription()).isEqualTo(DEFAULT_QUARTER_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cultureRepository.findAll().size();

        // Create the Culture with an existing ID
        culture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(culture)))
            .andExpect(status().isBadRequest());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCultures() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList
        restCultureMockMvc.perform(get("/api/cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(culture.getId().intValue())))
            .andExpect(jsonPath("$.[*].era").value(hasItem(DEFAULT_ERA.toString())))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].unitDescription").value(hasItem(DEFAULT_UNIT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quarter").value(hasItem(DEFAULT_QUARTER)))
            .andExpect(jsonPath("$.[*].quarterDescription").value(hasItem(DEFAULT_QUARTER_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get the culture
        restCultureMockMvc.perform(get("/api/cultures/{id}", culture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(culture.getId().intValue()))
            .andExpect(jsonPath("$.era").value(DEFAULT_ERA.toString()))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.unitDescription").value(DEFAULT_UNIT_DESCRIPTION))
            .andExpect(jsonPath("$.quarter").value(DEFAULT_QUARTER))
            .andExpect(jsonPath("$.quarterDescription").value(DEFAULT_QUARTER_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingCulture() throws Exception {
        // Get the culture
        restCultureMockMvc.perform(get("/api/cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        int databaseSizeBeforeUpdate = cultureRepository.findAll().size();

        // Update the culture
        Culture updatedCulture = cultureRepository.findById(culture.getId()).get();
        // Disconnect from session so that the updates on updatedCulture are not directly saved in db
        em.detach(updatedCulture);
        updatedCulture
            .era(UPDATED_ERA)
            .kind(UPDATED_KIND)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .name(UPDATED_NAME)
            .unit(UPDATED_UNIT)
            .unitDescription(UPDATED_UNIT_DESCRIPTION)
            .quarter(UPDATED_QUARTER)
            .quarterDescription(UPDATED_QUARTER_DESCRIPTION);

        restCultureMockMvc.perform(put("/api/cultures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCulture)))
            .andExpect(status().isOk());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeUpdate);
        Culture testCulture = cultureList.get(cultureList.size() - 1);
        assertThat(testCulture.getEra()).isEqualTo(UPDATED_ERA);
        assertThat(testCulture.getKind()).isEqualTo(UPDATED_KIND);
        assertThat(testCulture.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCulture.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testCulture.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCulture.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testCulture.getUnitDescription()).isEqualTo(UPDATED_UNIT_DESCRIPTION);
        assertThat(testCulture.getQuarter()).isEqualTo(UPDATED_QUARTER);
        assertThat(testCulture.getQuarterDescription()).isEqualTo(UPDATED_QUARTER_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCulture() throws Exception {
        int databaseSizeBeforeUpdate = cultureRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultureMockMvc.perform(put("/api/cultures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(culture)))
            .andExpect(status().isBadRequest());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        int databaseSizeBeforeDelete = cultureRepository.findAll().size();

        // Delete the culture
        restCultureMockMvc.perform(delete("/api/cultures/{id}", culture.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
