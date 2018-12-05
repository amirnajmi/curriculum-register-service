package ir.co.sadad.controller;

import ir.co.sadad.domain.Term;
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
@Path("/api/term")
public interface TermControllerClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTerm(Term term);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTerm(Term term);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Term> getAllTerms();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getTerm(@PathParam("id") Long id);

    @DELETE
    @Path("/{id}")
    public Response removeTerm(@PathParam("id") Long id);

}
