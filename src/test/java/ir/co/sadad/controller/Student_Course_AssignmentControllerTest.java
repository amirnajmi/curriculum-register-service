package ir.co.sadad.controller;

import ir.co.sadad.repository.Student_Course_AssignmentRepository;
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
 * Test class for the Student_Course_AssignmentController REST controller.
 *
 */
@RunWith(Arquillian.class)
public class Student_Course_AssignmentControllerTest extends AbstractTest {

    private static final String DEFAULT_STUDENT_ID = "A";
    private static final String UPDATED_STUDENT_ID = "B";

    private static Student_Course_Assignment student_Course_Assignment;

    @Inject
    private Student_Course_AssignmentRepository student_Course_AssignmentRepository;

    private Student_Course_AssignmentControllerClient client;

    @Deployment
    public static WebArchive createDeployment() {
        return buildArchive()
                .addClasses(
                        AbstractTest.class,
                        Student_Course_Assignment.class,
                        Student_Course_AssignmentRepository.class,
                        Student_Course_AssignmentController.class,
                        Student_Course_AssignmentControllerClient.class);
    }

    @Before
    public void buildClient() throws Exception {
        client = buildClient(Student_Course_AssignmentControllerClient.class);
    }

    @Test
    @InSequence(1)
    public void createStudent_Course_Assignment() throws Exception {
        int databaseSizeBeforeCreate = student_Course_AssignmentRepository.findAll().size();

        // Create the Student_Course_Assignment
        student_Course_Assignment = new Student_Course_Assignment();
        student_Course_Assignment.setStudentId(DEFAULT_STUDENT_ID);
        Response response = client.createStudent_Course_Assignment(student_Course_Assignment);
        assertThat(response, hasStatus(CREATED));
        student_Course_Assignment = response.readEntity(Student_Course_Assignment.class);

        // Validate the Student_Course_Assignment in the database
        List<Student_Course_Assignment> student_Course_Assignments = student_Course_AssignmentRepository.findAll();
        assertThat(student_Course_Assignments.size(), is(databaseSizeBeforeCreate + 1));
        Student_Course_Assignment testStudent_Course_Assignment = student_Course_Assignments.get(student_Course_Assignments.size() - 1);
        assertThat(testStudent_Course_Assignment.getStudentId(), is(DEFAULT_STUDENT_ID));
    }

    @Test
    @InSequence(2)
    public void getAllStudent_Course_Assignments() throws Exception {
        int databaseSize = student_Course_AssignmentRepository.findAll().size();

        // Get all the student_Course_Assignments
        List<Student_Course_Assignment> student_Course_Assignments = client.getAllStudent_Course_Assignments();
        assertThat(student_Course_Assignments.size(), is(databaseSize));
    }

    @Test
    @InSequence(3)
    public void getStudent_Course_Assignment() throws Exception {
        // Get the student_Course_Assignment
        Response response = client.getStudent_Course_Assignment(student_Course_Assignment.getId());
        Student_Course_Assignment testStudent_Course_Assignment = response.readEntity(Student_Course_Assignment.class);
        assertThat(response, hasStatus(OK));
        assertThat(response, hasContentType(MediaType.APPLICATION_JSON_TYPE));
        assertThat(testStudent_Course_Assignment.getId(), is(student_Course_Assignment.getId()));
        assertThat(testStudent_Course_Assignment.getStudentId(), is(DEFAULT_STUDENT_ID));
    }

    @Test
    @InSequence(4)
    public void getNonExistingStudent_Course_Assignment() throws Exception {
        // Get the student_Course_Assignment
        assertWebException(NOT_FOUND, () -> client.getStudent_Course_Assignment(3L));
    }

    @Test
    @InSequence(5)
    public void updateStudent_Course_Assignment() throws Exception {
        int databaseSizeBeforeUpdate = student_Course_AssignmentRepository.findAll().size();

        // Update the student_Course_Assignment
        Student_Course_Assignment updatedStudent_Course_Assignment = new Student_Course_Assignment();
        updatedStudent_Course_Assignment.setId(student_Course_Assignment.getId());
        updatedStudent_Course_Assignment.setStudentId(UPDATED_STUDENT_ID);

        Response response = client.updateStudent_Course_Assignment(updatedStudent_Course_Assignment);
        assertThat(response, hasStatus(OK));

        // Validate the Student_Course_Assignment in the database
        List<Student_Course_Assignment> student_Course_Assignments = student_Course_AssignmentRepository.findAll();
        assertThat(student_Course_Assignments.size(), is(databaseSizeBeforeUpdate));
        Student_Course_Assignment testStudent_Course_Assignment = student_Course_Assignments.get(student_Course_Assignments.size() - 1);
        assertThat(testStudent_Course_Assignment.getStudentId(), is(UPDATED_STUDENT_ID));
    }

    @Test
    @InSequence(6)
    public void removeStudent_Course_Assignment() throws Exception {
        int databaseSizeBeforeDelete = student_Course_AssignmentRepository.findAll().size();

        // Delete the student_Course_Assignment
        Response response = client.removeStudent_Course_Assignment(student_Course_Assignment.getId());
        assertThat(response, hasStatus(OK));

        // Validate the database is empty
        List<Student_Course_Assignment> student_Course_Assignments = student_Course_AssignmentRepository.findAll();
        assertThat(student_Course_Assignments.size(), is(databaseSizeBeforeDelete - 1));
    }

}
