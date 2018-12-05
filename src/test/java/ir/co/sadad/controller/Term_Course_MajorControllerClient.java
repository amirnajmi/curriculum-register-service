package ir.co.sadad.controller;

import ir.co.sadad.domain.Term_Course_Major;
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
@Path("/api/term-course-major")
public interface Term_Course_MajorControllerClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTerm_Course_Major(Term_Course_Major term_Course_Major);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTerm_Course_Major(Term_Course_Major term_Course_Major);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Term_Course_Major> getAllTerm_Course_Majors();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getTerm_Course_Major(@PathParam("id") Long id);

    @DELETE
    @Path("/{id}")
    public Response removeTerm_Course_Major(@PathParam("id") Long id);

}
