package ir.co.sadad.controller;

import ir.co.sadad.domain.Term_Course_Major;
import ir.co.sadad.repository.Term_Course_MajorRepository;
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
 * REST controller for managing Term_Course_Major.
 */
@Path("/api/term-course-major")
@RolesAllowed(USER)
public class Term_Course_MajorController {

    @Inject
    private Logger log;

    @Inject
    private Term_Course_MajorRepository term_Course_MajorRepository;

    private static final String ENTITY_NAME = "curricolumnRegisterServiceTermCourseMajor";

    /**
     * POST : Create a new term_Course_Major.
     *
     * @param term_Course_Major the term_Course_Major to create
     * @return the Response with status 201 (Created) and with body the new
     * term_Course_Major, or with status 400 (Bad Request) if the
     * term_Course_Major has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "create a new term_Course_Major", description = "Create a new term_Course_Major")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTerm_Course_Major(Term_Course_Major term_Course_Major) throws URISyntaxException {
        log.debug("REST request to save Term_Course_Major : {}", term_Course_Major);
        term_Course_MajorRepository.create(term_Course_Major);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/term-course-major/" + term_Course_Major.getId())),
                ENTITY_NAME, term_Course_Major.getId().toString())
                .entity(term_Course_Major).build();
    }

    /**
     * PUT : Updates an existing term_Course_Major.
     *
     * @param term_Course_Major the term_Course_Major to update
     * @return the Response with status 200 (OK) and with body the updated
     * term_Course_Major, or with status 400 (Bad Request) if the
     * term_Course_Major is not valid, or with status 500 (Internal Server
     * Error) if the term_Course_Major couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "update term_Course_Major", description = "Updates an existing term_Course_Major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTerm_Course_Major(Term_Course_Major term_Course_Major) throws URISyntaxException {
        log.debug("REST request to update Term_Course_Major : {}", term_Course_Major);
        term_Course_MajorRepository.edit(term_Course_Major);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, term_Course_Major.getId().toString())
                .entity(term_Course_Major).build();
    }

    /**
     * GET : get all the term_Course_Majors.
     *
     * @return the Response with status 200 (OK) and the list of
     * term_Course_Majors in body
     *
     */
    @Timed
    @Operation(summary = "get all the term_Course_Majors")
    @APIResponse(responseCode = "200", description = "OK")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Term_Course_Major> getAllTerm_Course_Majors() {
        log.debug("REST request to get all Term_Course_Majors");
        List<Term_Course_Major> term_Course_Majors = term_Course_MajorRepository.findAll();
        return term_Course_Majors;
    }

    /**
     * GET /:id : get the "id" term_Course_Major.
     *
     * @param id the id of the term_Course_Major to retrieve
     * @return the Response with status 200 (OK) and with body the
     * term_Course_Major, or with status 404 (Not Found)
     */
    @Timed
    @Operation(summary = "get the term_Course_Major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTerm_Course_Major(@PathParam("id") Long id) {
        log.debug("REST request to get Term_Course_Major : {}", id);
        Term_Course_Major term_Course_Major = term_Course_MajorRepository.find(id);
        return Optional.ofNullable(term_Course_Major)
                .map(result -> Response.status(Response.Status.OK).entity(term_Course_Major).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" term_Course_Major.
     *
     * @param id the id of the term_Course_Major to delete
     * @return the Response with status 200 (OK)
     */
    @Timed
    @Operation(summary = "remove the term_Course_Major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @DELETE
    @Path("/{id}")
    public Response removeTerm_Course_Major(@PathParam("id") Long id) {
        log.debug("REST request to delete Term_Course_Major : {}", id);
        term_Course_MajorRepository.remove(term_Course_MajorRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, id.toString()).build();
    }

}
