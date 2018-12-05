package ir.co.sadad.controller;

import ir.co.sadad.domain.Student_Course_Assignment;
import ir.co.sadad.repository.Student_Course_AssignmentRepository;
import ir.co.sadad.controller.util.HeaderUtil;

import ir.co.sadad.restclients.domain.Employee;
import ir.co.sadad.restclients.restinterfaces.EmployeeServiceClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 * REST controller for managing Student_Course_Assignment.
 */
@Path("/api/student-course-assignment")
//@RolesAllowed(USER)
public class Student_Course_AssignmentController {

    @Inject
    private Logger log;

    @Inject
    private Student_Course_AssignmentRepository student_Course_AssignmentRepository;

    @Inject
    @RestClient
    private EmployeeServiceClient userServiceClient;

    private static final String ENTITY_NAME = "curricolumnRegisterServiceStudentCourseAssignment";

    /**
     * POST : Create a new student_Course_Assignment.
     *
     * @param student_Course_Assignment the student_Course_Assignment to create
     * @return the Response with status 201 (Created) and with body the new
     * student_Course_Assignment, or with status 400 (Bad Request) if the
     * student_Course_Assignment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "create a new student_Course_Assignment", description = "Create a new student_Course_Assignment")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent_Course_Assignment(Student_Course_Assignment student_Course_Assignment) throws URISyntaxException {
        log.debug("REST request to save Student_Course_Assignment : {}", student_Course_Assignment);
        student_Course_AssignmentRepository.create(student_Course_Assignment);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/student-course-assignment/" + student_Course_Assignment.getId())),
                ENTITY_NAME, student_Course_Assignment.getId().toString())
                .entity(student_Course_Assignment).build();
    }

    /**
     * PUT : Updates an existing student_Course_Assignment.
     *
     * @param student_Course_Assignment the student_Course_Assignment to update
     * @return the Response with status 200 (OK) and with body the updated
     * student_Course_Assignment, or with status 400 (Bad Request) if the
     * student_Course_Assignment is not valid, or with status 500 (Internal
     * Server Error) if the student_Course_Assignment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "update student_Course_Assignment", description = "Updates an existing student_Course_Assignment")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent_Course_Assignment(Student_Course_Assignment student_Course_Assignment) throws URISyntaxException {
        log.debug("REST request to update Student_Course_Assignment : {}", student_Course_Assignment);
        student_Course_AssignmentRepository.edit(student_Course_Assignment);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, student_Course_Assignment.getId().toString())
                .entity(student_Course_Assignment).build();
    }

    /**
     * GET : get all the student_Course_Assignments.
     *
     * @return the Response with status 200 (OK) and the list of
     * student_Course_Assignments in body
     *
     */
    @Timed
    @Operation(summary = "get all the student_Course_Assignments")
    @APIResponse(responseCode = "200", description = "OK")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Student_Course_Assignment> getAllStudent_Course_Assignments() {
        log.debug("REST request to get all Student_Course_Assignments");
        List<Student_Course_Assignment> student_Course_Assignments = student_Course_AssignmentRepository.findAll();
        return student_Course_Assignments;
    }

    /**
     * GET /:id : get the "id" student_Course_Assignment.
     *
     * @param id the id of the student_Course_Assignment to retrieve
     * @return the Response with status 200 (OK) and with body the
     * student_Course_Assignment, or with status 404 (Not Found)
     */
    @Timed
    @Operation(summary = "get the student_Course_Assignment")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent_Course_Assignment(@PathParam("id") Long id) {
        log.debug("REST request to get Student_Course_Assignment : {}", id);
        Student_Course_Assignment student_Course_Assignment = student_Course_AssignmentRepository.find(id);
        return Optional.ofNullable(student_Course_Assignment)
                .map(result -> Response.status(Response.Status.OK).entity(student_Course_Assignment).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" student_Course_Assignment.
     *
     * @param id the id of the student_Course_Assignment to delete
     * @return the Response with status 200 (OK)
     */
    @Timed
    @Operation(summary = "remove the student_Course_Assignment")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @DELETE
    @Path("/{id}")
    public Response removeStudent_Course_Assignment(@PathParam("id") Long id) {
        log.debug("REST request to delete Student_Course_Assignment : {}", id);
        student_Course_AssignmentRepository.remove(student_Course_AssignmentRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, id.toString()).build();
    }


    @Timed
    @Operation(summary = "get student from student service")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("getemployee/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("id") Long id){

     try{
//        EmployeeServiceClient employeeServiceClient = RestClientBuilder.newBuilder().baseUrl(new URL("http://192.168.43.50:8082/user-service/resources/api/employee/12"))
//                .register(UnknownError.class).build(EmployeeServiceClient.class);
         return Response.ok(((Employee) userServiceClient.getEmployee(id).readEntity(Employee.class))).build();


    } catch (ProcessingException ex) {
    } catch (UnknownError e) {
        System.err.println("The given URL is unreachable.");
    }
//    catch (MalformedURLException e) {
//        System.err.println("The given URL is not formatted correctly.");
//    }

return null;


    }

}
