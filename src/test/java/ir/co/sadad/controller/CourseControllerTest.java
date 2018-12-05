package ir.co.sadad.controller;

import ir.co.sadad.repository.CourseRepository;
import ir.co.sadad.domain.Term_Course_Major;
import ir.co.sadad.domain.Student_Course_Assignment;
import ir.co.sadad.domain.Course;
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
 * Test class for the CourseController REST controller.
 *
 */
@RunWith(Arquillian.class)
public class CourseControllerTest extends AbstractTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";

    private static Course course;

    @Inject
    private CourseRepository courseRepository;

    private CourseControllerClient client;

    @Deployment
    public static WebArchive createDeployment() {
        return buildArchive()
                .addClasses(
                        AbstractTest.class,
                        Term_Course_Major.class,
                        Student_Course_Assignment.class,
                        Course.class,
                        Course_Major.class,
                        CourseRepository.class,
                        CourseController.class,
                        CourseControllerClient.class);
    }

    @Before
    public void buildClient() throws Exception {
        client = buildClient(CourseControllerClient.class);
    }

    @Test
    @InSequence(1)
    public void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course
        course = new Course();
        course.setName(DEFAULT_NAME);
        Response response = client.createCourse(course);
        assertThat(response, hasStatus(CREATED));
        course = response.readEntity(Course.class);

        // Validate the Course in the database
        List<Course> courses = courseRepository.findAll();
        assertThat(courses.size(), is(databaseSizeBeforeCreate + 1));
        Course testCourse = courses.get(courses.size() - 1);
        assertThat(testCourse.getName(), is(DEFAULT_NAME));
    }

    @Test
    @InSequence(2)
    public void getAllCourses() throws Exception {
        int databaseSize = courseRepository.findAll().size();

        // Get all the courses
        List<Course> courses = client.getAllCourses();
        assertThat(courses.size(), is(databaseSize));
    }

    @Test
    @InSequence(3)
    public void getCourse() throws Exception {
        // Get the course
        Response response = client.getCourse(course.getId());
        Course testCourse = response.readEntity(Course.class);
        assertThat(response, hasStatus(OK));
        assertThat(response, hasContentType(MediaType.APPLICATION_JSON_TYPE));
        assertThat(testCourse.getId(), is(course.getId()));
        assertThat(testCourse.getName(), is(DEFAULT_NAME));
    }

    @Test
    @InSequence(4)
    public void getNonExistingCourse() throws Exception {
        // Get the course
        assertWebException(NOT_FOUND, () -> client.getCourse(3L));
    }

    @Test
    @InSequence(5)
    public void updateCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        Course updatedCourse = new Course();
        updatedCourse.setId(course.getId());
        updatedCourse.setName(UPDATED_NAME);

        Response response = client.updateCourse(updatedCourse);
        assertThat(response, hasStatus(OK));

        // Validate the Course in the database
        List<Course> courses = courseRepository.findAll();
        assertThat(courses.size(), is(databaseSizeBeforeUpdate));
        Course testCourse = courses.get(courses.size() - 1);
        assertThat(testCourse.getName(), is(UPDATED_NAME));
    }

    @Test
    @InSequence(6)
    public void removeCourse() throws Exception {
        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Delete the course
        Response response = client.removeCourse(course.getId());
        assertThat(response, hasStatus(OK));

        // Validate the database is empty
        List<Course> courses = courseRepository.findAll();
        assertThat(courses.size(), is(databaseSizeBeforeDelete - 1));
    }

}
