package pl.com.humankind.web.rest;

import pl.com.humankind.domain.Culture;
import pl.com.humankind.repository.CultureRepository;
import pl.com.humankind.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.com.humankind.domain.Culture}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CultureResource {

    private final Logger log = LoggerFactory.getLogger(CultureResource.class);

    private static final String ENTITY_NAME = "culture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CultureRepository cultureRepository;

    public CultureResource(CultureRepository cultureRepository) {
        this.cultureRepository = cultureRepository;
    }

    /**
     * {@code POST  /cultures} : Create a new culture.
     *
     * @param culture the culture to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new culture, or with status {@code 400 (Bad Request)} if the culture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cultures")
    public ResponseEntity<Culture> createCulture(@RequestBody Culture culture) throws URISyntaxException {
        log.debug("REST request to save Culture : {}", culture);
        if (culture.getId() != null) {
            throw new BadRequestAlertException("A new culture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Culture result = cultureRepository.save(culture);
        return ResponseEntity.created(new URI("/api/cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cultures} : Updates an existing culture.
     *
     * @param culture the culture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated culture,
     * or with status {@code 400 (Bad Request)} if the culture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the culture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cultures")
    public ResponseEntity<Culture> updateCulture(@RequestBody Culture culture) throws URISyntaxException {
        log.debug("REST request to update Culture : {}", culture);
        if (culture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Culture result = cultureRepository.save(culture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, culture.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cultures} : get all the cultures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cultures in body.
     */
    @GetMapping("/cultures")
    public List<Culture> getAllCultures() {
        log.debug("REST request to get all Cultures");
        return cultureRepository.findAll();
    }

    /**
     * {@code GET  /cultures/:id} : get the "id" culture.
     *
     * @param id the id of the culture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the culture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cultures/{id}")
    public ResponseEntity<Culture> getCulture(@PathVariable Long id) {
        log.debug("REST request to get Culture : {}", id);
        Optional<Culture> culture = cultureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(culture);
    }

    /**
     * {@code DELETE  /cultures/:id} : delete the "id" culture.
     *
     * @param id the id of the culture to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cultures/{id}")
    public ResponseEntity<Void> deleteCulture(@PathVariable Long id) {
        log.debug("REST request to delete Culture : {}", id);
        cultureRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
