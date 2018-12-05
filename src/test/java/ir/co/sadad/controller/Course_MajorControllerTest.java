package ir.co.sadad.controller;

import ir.co.sadad.repository.Course_MajorRepository;
import ir.co.sadad.domain.Term_Course_Major;
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
 * Test class for the Course_MajorController REST controller.
 *
 */
@RunWith(Arquillian.class)
public class Course_MajorControllerTest extends AbstractTest {

    private static final Integer DEFAULT_FACTOR = 1;
    private static final Integer UPDATED_FACTOR = 2;
    private static final String DEFAULT_TEACHER_ID = "A";
    private static final String UPDATED_TEACHER_ID = "B";

    private static Course_Major course_Major;

    @Inject
    private Course_MajorRepository course_MajorRepository;

    private Course_MajorControllerClient client;

    @Deployment
    public static WebArchive createDeployment() {
        return buildArchive()
                .addClasses(
                        AbstractTest.class,
                        Term_Course_Major.class,
                        Student_Course_Assignment.class,
                        Course_Major.class,
                        Course_MajorRepository.class,
                        Course_MajorController.class,
                        Course_MajorControllerClient.class);
    }

    @Before
    public void buildClient() throws Exception {
        client = buildClient(Course_MajorControllerClient.class);
    }

    @Test
    @InSequence(1)
    public void createCourse_Major() throws Exception {
        int databaseSizeBeforeCreate = course_MajorRepository.findAll().size();

        // Create the Course_Major
        course_Major = new Course_Major();
        course_Major.setFactor(DEFAULT_FACTOR);
        course_Major.setTeacherId(DEFAULT_TEACHER_ID);
        Response response = client.createCourse_Major(course_Major);
        assertThat(response, hasStatus(CREATED));
        course_Major = response.readEntity(Course_Major.class);

        // Validate the Course_Major in the database
        List<Course_Major> course_Majors = course_MajorRepository.findAll();
        assertThat(course_Majors.size(), is(databaseSizeBeforeCreate + 1));
        Course_Major testCourse_Major = course_Majors.get(course_Majors.size() - 1);
        assertThat(testCourse_Major.getFactor(), is(DEFAULT_FACTOR));
        assertThat(testCourse_Major.getTeacherId(), is(DEFAULT_TEACHER_ID));
    }

    @Test
    @InSequence(2)
    public void getAllCourse_Majors() throws Exception {
        int databaseSize = course_MajorRepository.findAll().size();

        // Get all the course_Majors
        List<Course_Major> course_Majors = client.getAllCourse_Majors();
        assertThat(course_Majors.size(), is(databaseSize));
    }

    @Test
    @InSequence(3)
    public void getCourse_Major() throws Exception {
        // Get the course_Major
        Response response = client.getCourse_Major(course_Major.getId());
        Course_Major testCourse_Major = response.readEntity(Course_Major.class);
        assertThat(response, hasStatus(OK));
        assertThat(response, hasContentType(MediaType.APPLICATION_JSON_TYPE));
        assertThat(testCourse_Major.getId(), is(course_Major.getId()));
        assertThat(testCourse_Major.getFactor(), is(DEFAULT_FACTOR));
        assertThat(testCourse_Major.getTeacherId(), is(DEFAULT_TEACHER_ID));
    }

    @Test
    @InSequence(4)
    public void getNonExistingCourse_Major() throws Exception {
        // Get the course_Major
        assertWebException(NOT_FOUND, () -> client.getCourse_Major(3L));
    }

    @Test
    @InSequence(5)
    public void updateCourse_Major() throws Exception {
        int databaseSizeBeforeUpdate = course_MajorRepository.findAll().size();

        // Update the course_Major
        Course_Major updatedCourse_Major = new Course_Major();
        updatedCourse_Major.setId(course_Major.getId());
        updatedCourse_Major.setFactor(UPDATED_FACTOR);
        updatedCourse_Major.setTeacherId(UPDATED_TEACHER_ID);

        Response response = client.updateCourse_Major(updatedCourse_Major);
        assertThat(response, hasStatus(OK));

        // Validate the Course_Major in the database
        List<Course_Major> course_Majors = course_MajorRepository.findAll();
        assertThat(course_Majors.size(), is(databaseSizeBeforeUpdate));
        Course_Major testCourse_Major = course_Majors.get(course_Majors.size() - 1);
        assertThat(testCourse_Major.getFactor(), is(UPDATED_FACTOR));
        assertThat(testCourse_Major.getTeacherId(), is(UPDATED_TEACHER_ID));
    }

    @Test
    @InSequence(6)
    public void removeCourse_Major() throws Exception {
        int databaseSizeBeforeDelete = course_MajorRepository.findAll().size();

        // Delete the course_Major
        Response response = client.removeCourse_Major(course_Major.getId());
        assertThat(response, hasStatus(OK));

        // Validate the database is empty
        List<Course_Major> course_Majors = course_MajorRepository.findAll();
        assertThat(course_Majors.size(), is(databaseSizeBeforeDelete - 1));
    }

}
