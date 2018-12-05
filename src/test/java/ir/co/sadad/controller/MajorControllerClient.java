package ir.co.sadad.controller;

import ir.co.sadad.domain.Major;
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
@Path("/api/major")
public interface MajorControllerClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMajor(Major major);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMajor(Major major);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Major> getAllMajors();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getMajor(@PathParam("id") Long id);

    @DELETE
    @Path("/{id}")
    public Response removeMajor(@PathParam("id") Long id);

}
