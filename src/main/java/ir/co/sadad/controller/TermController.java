package ir.co.sadad.controller;

import ir.co.sadad.domain.Term;
import ir.co.sadad.repository.TermRepository;
import ir.co.sadad.controller.util.HeaderUtil;
import static ir.co.sadad.security.AuthoritiesConstants.USER;
import org.slf4j.Logger;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 * REST controller for managing Term.
 */
@Path("/api/term")
@RolesAllowed(USER)
public class TermController {

    @Inject
    private Logger log;

    @Inject
    private TermRepository termRepository;

    private static final String ENTITY_NAME = "curricolumnRegisterServiceTerm";

    /**
     * POST : Create a new term.
     *
     * @param term the term to create
     * @return the Response with status 201 (Created) and with body the new
     * term, or with status 400 (Bad Request) if the term has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "create a new term", description = "Create a new term")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTerm(Term term) throws URISyntaxException {
        log.debug("REST request to save Term : {}", term);
        termRepository.create(term);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/term/" + term.getId())),
                ENTITY_NAME, term.getId().toString())
                .entity(term).build();
    }

    /**
     * PUT : Updates an existing term.
     *
     * @param term the term to update
     * @return the Response with status 200 (OK) and with body the updated term,
     * or with status 400 (Bad Request) if the term is not valid, or with status
     * 500 (Internal Server Error) if the term couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "update term", description = "Updates an existing term")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTerm(Term term) throws URISyntaxException {
        log.debug("REST request to update Term : {}", term);
        termRepository.edit(term);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, term.getId().toString())
                .entity(term).build();
    }

    /**
     * GET : get all the terms.
     *
     * @return the Response with status 200 (OK) and the list of terms in body
     *
     */
    @Timed
    @Operation(summary = "get all the terms")
    @APIResponse(responseCode = "200", description = "OK")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Term> getAllTerms() {
        log.debug("REST request to get all Terms");
        List<Term> terms = termRepository.findAll();
        return terms;
    }

    /**
     * GET /:id : get the "id" term.
     *
     * @param id the id of the term to retrieve
     * @return the Response with status 200 (OK) and with body the term, or with
     * status 404 (Not Found)
     */
    @Timed
    @Operation(summary = "get the term")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTerm(@PathParam("id") Long id) {
        log.debug("REST request to get Term : {}", id);
        Term term = termRepository.find(id);
        return Optional.ofNullable(term)
                .map(result -> Response.status(Response.Status.OK).entity(term).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" term.
     *
     * @param id the id of the term to delete
     * @return the Response with status 200 (OK)
     */
    @Timed
    @Operation(summary = "remove the term")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @DELETE
    @Path("/{id}")
    public Response removeTerm(@PathParam("id") Long id) {
        log.debug("REST request to delete Term : {}", id);
        termRepository.remove(termRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, id.toString()).build();
    }

}
