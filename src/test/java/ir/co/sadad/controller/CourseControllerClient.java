package ir.co.sadad.controller;

import ir.co.sadad.domain.Course;
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
@Path("/api/course")
public interface CourseControllerClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCourse(Course course);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCourse(Course course);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getAllCourses();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getCourse(@PathParam("id") Long id);

    @DELETE
    @Path("/{id}")
    public Response removeCourse(@PathParam("id") Long id);

}
