package ir.co.sadad.controller;

import ir.co.sadad.domain.Major;
import ir.co.sadad.repository.MajorRepository;
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
 * REST controller for managing Major.
 */
@Path("/api/major")
@RolesAllowed(USER)
public class MajorController {

    @Inject
    private Logger log;

    @Inject
    private MajorRepository majorRepository;

    private static final String ENTITY_NAME = "curricolumnRegisterServiceMajor";

    /**
     * POST : Create a new major.
     *
     * @param major the major to create
     * @return the Response with status 201 (Created) and with body the new
     * major, or with status 400 (Bad Request) if the major has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "create a new major", description = "Create a new major")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMajor(Major major) throws URISyntaxException {
        log.debug("REST request to save Major : {}", major);
        majorRepository.create(major);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/major/" + major.getId())),
                ENTITY_NAME, major.getId().toString())
                .entity(major).build();
    }

    /**
     * PUT : Updates an existing major.
     *
     * @param major the major to update
     * @return the Response with status 200 (OK) and with body the updated
     * major, or with status 400 (Bad Request) if the major is not valid, or
     * with status 500 (Internal Server Error) if the major couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "update major", description = "Updates an existing major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMajor(Major major) throws URISyntaxException {
        log.debug("REST request to update Major : {}", major);
        majorRepository.edit(major);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, major.getId().toString())
                .entity(major).build();
    }

    /**
     * GET : get all the majors.
     *
     * @return the Response with status 200 (OK) and the list of majors in body
     *
     */
    @Timed
    @Operation(summary = "get all the majors")
    @APIResponse(responseCode = "200", description = "OK")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Major> getAllMajors() {
        log.debug("REST request to get all Majors");
        List<Major> majors = majorRepository.findAll();
        return majors;
    }

    /**
     * GET /:id : get the "id" major.
     *
     * @param id the id of the major to retrieve
     * @return the Response with status 200 (OK) and with body the major, or
     * with status 404 (Not Found)
     */
    @Timed
    @Operation(summary = "get the major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMajor(@PathParam("id") Long id) {
        log.debug("REST request to get Major : {}", id);
        Major major = majorRepository.find(id);
        return Optional.ofNullable(major)
                .map(result -> Response.status(Response.Status.OK).entity(major).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" major.
     *
     * @param id the id of the major to delete
     * @return the Response with status 200 (OK)
     */
    @Timed
    @Operation(summary = "remove the major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @DELETE
    @Path("/{id}")
    public Response removeMajor(@PathParam("id") Long id) {
        log.debug("REST request to delete Major : {}", id);
        majorRepository.remove(majorRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, id.toString()).build();
    }

}
