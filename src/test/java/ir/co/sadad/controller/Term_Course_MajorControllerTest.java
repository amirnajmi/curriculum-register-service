package ir.co.sadad.controller;

import ir.co.sadad.repository.Term_Course_MajorRepository;
import ir.co.sadad.domain.Term_Course_Major;
import ir.co.sadad.domain.Student_Course_Assignment;
import static java.util.Collections.singletonMap;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.*;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasContentType;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasStatus;

/**
 * Test class for the Term_Course_MajorController REST controller.
 *
 */
@RunWith(Arquillian.class)
public class Term_Course_MajorControllerTest extends AbstractTest {

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static Term_Course_Major term_Course_Major;

    @Inject
    private Term_Course_MajorRepository term_Course_MajorRepository;

    private Term_Course_MajorControllerClient client;

    @Deployment
    public static WebArchive createDeployment() {
        return buildArchive()
                .addClasses(
                        AbstractTest.class,
                        Term_Course_Major.class,
                        Student_Course_Assignment.class,
                        Term_Course_MajorRepository.class,
                        Term_Course_MajorController.class,
                        Term_Course_MajorControllerClient.class);
    }

    @Before
    public void buildClient() throws Exception {
        client = buildClient(Term_Course_MajorControllerClient.class);
    }

    @Test
    @InSequence(1)
    public void createTerm_Course_Major() throws Exception {
        int databaseSizeBeforeCreate = term_Course_MajorRepository.findAll().size();

        // Create the Term_Course_Major
        term_Course_Major = new Term_Course_Major();
        term_Course_Major.setCapacity(DEFAULT_CAPACITY);
        Response response = client.createTerm_Course_Major(term_Course_Major);
        assertThat(response, hasStatus(CREATED));
        term_Course_Major = response.readEntity(Term_Course_Major.class);

        // Validate the Term_Course_Major in the database
        List<Term_Course_Major> term_Course_Majors = term_Course_MajorRepository.findAll();
        assertThat(term_Course_Majors.size(), is(databaseSizeBeforeCreate + 1));
        Term_Course_Major testTerm_Course_Major = term_Course_Majors.get(term_Course_Majors.size() - 1);
        assertThat(testTerm_Course_Major.getCapacity(), is(DEFAULT_CAPACITY));
    }

    @Test
    @InSequence(2)
    public void getAllTerm_Course_Majors() throws Exception {
        int databaseSize = term_Course_MajorRepository.findAll().size();

        // Get all the term_Course_Majors
        List<Term_Course_Major> term_Course_Majors = client.getAllTerm_Course_Majors();
        assertThat(term_Course_Majors.size(), is(databaseSize));
    }

    @Test
    @InSequence(3)
    public void getTerm_Course_Major() throws Exception {
        // Get the term_Course_Major
        Response response = client.getTerm_Course_Major(term_Course_Major.getId());
        Term_Course_Major testTerm_Course_Major = response.readEntity(Term_Course_Major.class);
        assertThat(response, hasStatus(OK));
        assertThat(response, hasContentType(MediaType.APPLICATION_JSON_TYPE));
        assertThat(testTerm_Course_Major.getId(), is(term_Course_Major.getId()));
        assertThat(testTerm_Course_Major.getCapacity(), is(DEFAULT_CAPACITY));
    }

    @Test
    @InSequence(4)
    public void getNonExistingTerm_Course_Major() throws Exception {
        // Get the term_Course_Major
        assertWebException(NOT_FOUND, () -> client.getTerm_Course_Major(3L));
    }

    @Test
    @InSequence(5)
    public void updateTerm_Course_Major() throws Exception {
        int databaseSizeBeforeUpdate = term_Course_MajorRepository.findAll().size();

        // Update the term_Course_Major
        Term_Course_Major updatedTerm_Course_Major = new Term_Course_Major();
        updatedTerm_Course_Major.setId(term_Course_Major.getId());
        updatedTerm_Course_Major.setCapacity(UPDATED_CAPACITY);

        Response response = client.updateTerm_Course_Major(updatedTerm_Course_Major);
        assertThat(response, hasStatus(OK));

        // Validate the Term_Course_Major in the database
        List<Term_Course_Major> term_Course_Majors = term_Course_MajorRepository.findAll();
        assertThat(term_Course_Majors.size(), is(databaseSizeBeforeUpdate));
        Term_Course_Major testTerm_Course_Major = term_Course_Majors.get(term_Course_Majors.size() - 1);
        assertThat(testTerm_Course_Major.getCapacity(), is(UPDATED_CAPACITY));
    }

    @Test
    @InSequence(6)
    public void removeTerm_Course_Major() throws Exception {
        int databaseSizeBeforeDelete = term_Course_MajorRepository.findAll().size();

        // Delete the term_Course_Major
        Response response = client.removeTerm_Course_Major(term_Course_Major.getId());
        assertThat(response, hasStatus(OK));

        // Validate the database is empty
        List<Term_Course_Major> term_Course_Majors = term_Course_MajorRepository.findAll();
        assertThat(term_Course_Majors.size(), is(databaseSizeBeforeDelete - 1));
    }

}
