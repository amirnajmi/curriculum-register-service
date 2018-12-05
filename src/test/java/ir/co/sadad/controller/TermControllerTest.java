package ir.co.sadad.controller;

import ir.co.sadad.repository.TermRepository;
import ir.co.sadad.domain.Term_Course_Major;
import ir.co.sadad.domain.Term;
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
 * Test class for the TermController REST controller.
 *
 */
@RunWith(Arquillian.class)
public class TermControllerTest extends AbstractTest {

    private static Term term;

    @Inject
    private TermRepository termRepository;

    private TermControllerClient client;

    @Deployment
    public static WebArchive createDeployment() {
        return buildArchive()
                .addClasses(
                        AbstractTest.class,
                        Term_Course_Major.class,
                        Term.class,
                        Student_Course_Assignment.class,
                        TermRepository.class,
                        TermController.class,
                        TermControllerClient.class);
    }

    @Before
    public void buildClient() throws Exception {
        client = buildClient(TermControllerClient.class);
    }

    @Test
    @InSequence(1)
    public void createTerm() throws Exception {
        int databaseSizeBeforeCreate = termRepository.findAll().size();

        // Create the Term
        term = new Term();
        Response response = client.createTerm(term);
        assertThat(response, hasStatus(CREATED));
        term = response.readEntity(Term.class);

        // Validate the Term in the database
        List<Term> terms = termRepository.findAll();
        assertThat(terms.size(), is(databaseSizeBeforeCreate + 1));
        Term testTerm = terms.get(terms.size() - 1);
    }

    @Test
    @InSequence(2)
    public void getAllTerms() throws Exception {
        int databaseSize = termRepository.findAll().size();

        // Get all the terms
        List<Term> terms = client.getAllTerms();
        assertThat(terms.size(), is(databaseSize));
    }

    @Test
    @InSequence(3)
    public void getTerm() throws Exception {
        // Get the term
        Response response = client.getTerm(term.getId());
        Term testTerm = response.readEntity(Term.class);
        assertThat(response, hasStatus(OK));
        assertThat(response, hasContentType(MediaType.APPLICATION_JSON_TYPE));
        assertThat(testTerm.getId(), is(term.getId()));
    }

    @Test
    @InSequence(4)
    public void getNonExistingTerm() throws Exception {
        // Get the term
        assertWebException(NOT_FOUND, () -> client.getTerm(3L));
    }

    @Test
    @InSequence(5)
    public void updateTerm() throws Exception {
        int databaseSizeBeforeUpdate = termRepository.findAll().size();

        // Update the term
        Term updatedTerm = new Term();
        updatedTerm.setId(term.getId());

        Response response = client.updateTerm(updatedTerm);
        assertThat(response, hasStatus(OK));

        // Validate the Term in the database
        List<Term> terms = termRepository.findAll();
        assertThat(terms.size(), is(databaseSizeBeforeUpdate));
        Term testTerm = terms.get(terms.size() - 1);
    }

    @Test
    @InSequence(6)
    public void removeTerm() throws Exception {
        int databaseSizeBeforeDelete = termRepository.findAll().size();

        // Delete the term
        Response response = client.removeTerm(term.getId());
        assertThat(response, hasStatus(OK));

        // Validate the database is empty
        List<Term> terms = termRepository.findAll();
        assertThat(terms.size(), is(databaseSizeBeforeDelete - 1));
    }

}
