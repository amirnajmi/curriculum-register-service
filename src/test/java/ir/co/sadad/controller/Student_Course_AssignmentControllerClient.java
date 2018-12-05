package ir.co.sadad.controller;

import ir.co.sadad.domain.Student_Course_Assignment;
import javax.ws.rs.PathParam;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/api/student-course-assignment")
public interface Student_Course_AssignmentControllerClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent_Course_Assignment(Student_Course_Assignment student_Course_Assignment);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent_Course_Assignment(Student_Course_Assignment student_Course_Assignment);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student_Course_Assignment> getAllStudent_Course_Assignments();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getStudent_Course_Assignment(@PathParam("id") Long id);

    @DELETE
    @Path("/{id}")
    public Response removeStudent_Course_Assignment(@PathParam("id") Long id);

}
