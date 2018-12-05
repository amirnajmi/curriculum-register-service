package ir.co.sadad.controller;

import ir.co.sadad.repository.MajorRepository;
import ir.co.sadad.domain.Term_Course_Major;
import ir.co.sadad.domain.Major;
import ir.co.sadad.domain.Student_Course_Assignment;
import ir.co.sadad.domain.Course_Major;
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
 * Test class for the MajorController REST controller.
 *
 */
@RunWith(Arquillian.class)
public class MajorControllerTest extends AbstractTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";

    private static Major major;

    @Inject
    private MajorRepository majorRepository;

    private MajorControllerClient client;

    @Deployment
    public static WebArchive createDeployment() {
        return buildArchive()
                .addClasses(
                        AbstractTest.class,
                        Term_Course_Major.class,
                        Major.class,
                        Student_Course_Assignment.class,
                        Course_Major.class,
                        MajorRepository.class,
                        MajorController.class,
                        MajorControllerClient.class);
    }

    @Before
    public void buildClient() throws Exception {
        client = buildClient(MajorControllerClient.class);
    }

    @Test
    @InSequence(1)
    public void createMajor() throws Exception {
        int databaseSizeBeforeCreate = majorRepository.findAll().size();

        // Create the Major
        major = new Major();
        major.setName(DEFAULT_NAME);
        Response response = client.createMajor(major);
        assertThat(response, hasStatus(CREATED));
        major = response.readEntity(Major.class);

        // Validate the Major in the database
        List<Major> majors = majorRepository.findAll();
        assertThat(majors.size(), is(databaseSizeBeforeCreate + 1));
        Major testMajor = majors.get(majors.size() - 1);
        assertThat(testMajor.getName(), is(DEFAULT_NAME));
    }

    @Test
    @InSequence(2)
    public void getAllMajors() throws Exception {
        int databaseSize = majorRepository.findAll().size();

        // Get all the majors
        List<Major> majors = client.getAllMajors();
        assertThat(majors.size(), is(databaseSize));
    }

    @Test
    @InSequence(3)
    public void getMajor() throws Exception {
        // Get the major
        Response response = client.getMajor(major.getId());
        Major testMajor = response.readEntity(Major.class);
        assertThat(response, hasStatus(OK));
        assertThat(response, hasContentType(MediaType.APPLICATION_JSON_TYPE));
        assertThat(testMajor.getId(), is(major.getId()));
        assertThat(testMajor.getName(), is(DEFAULT_NAME));
    }

    @Test
    @InSequence(4)
    public void getNonExistingMajor() throws Exception {
        // Get the major
        assertWebException(NOT_FOUND, () -> client.getMajor(3L));
    }

    @Test
    @InSequence(5)
    public void updateMajor() throws Exception {
        int databaseSizeBeforeUpdate = majorRepository.findAll().size();

        // Update the major
        Major updatedMajor = new Major();
        updatedMajor.setId(major.getId());
        updatedMajor.setName(UPDATED_NAME);

        Response response = client.updateMajor(updatedMajor);
        assertThat(response, hasStatus(OK));

        // Validate the Major in the database
        List<Major> majors = majorRepository.findAll();
        assertThat(majors.size(), is(databaseSizeBeforeUpdate));
        Major testMajor = majors.get(majors.size() - 1);
        assertThat(testMajor.getName(), is(UPDATED_NAME));
    }

    @Test
    @InSequence(6)
    public void removeMajor() throws Exception {
        int databaseSizeBeforeDelete = majorRepository.findAll().size();

        // Delete the major
        Response response = client.removeMajor(major.getId());
        assertThat(response, hasStatus(OK));

        // Validate the database is empty
        List<Major> majors = majorRepository.findAll();
        assertThat(majors.size(), is(databaseSizeBeforeDelete - 1));
    }

}
