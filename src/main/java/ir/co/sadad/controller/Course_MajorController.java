package ir.co.sadad.controller;

import ir.co.sadad.domain.Course_Major;
import ir.co.sadad.repository.Course_MajorRepository;
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
 * REST controller for managing Course_Major.
 */
@Path("/api/course-major")
@RolesAllowed(USER)
public class Course_MajorController {

    @Inject
    private Logger log;

    @Inject
    private Course_MajorRepository course_MajorRepository;

    private static final String ENTITY_NAME = "curricolumnRegisterServiceCourseMajor";

    /**
     * POST : Create a new course_Major.
     *
     * @param course_Major the course_Major to create
     * @return the Response with status 201 (Created) and with body the new
     * course_Major, or with status 400 (Bad Request) if the course_Major has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "create a new course_Major", description = "Create a new course_Major")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCourse_Major(Course_Major course_Major) throws URISyntaxException {
        log.debug("REST request to save Course_Major : {}", course_Major);
        course_MajorRepository.create(course_Major);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/course-major/" + course_Major.getId())),
                ENTITY_NAME, course_Major.getId().toString())
                .entity(course_Major).build();
    }

    /**
     * PUT : Updates an existing course_Major.
     *
     * @param course_Major the course_Major to update
     * @return the Response with status 200 (OK) and with body the updated
     * course_Major, or with status 400 (Bad Request) if the course_Major is not
     * valid, or with status 500 (Internal Server Error) if the course_Major
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "update course_Major", description = "Updates an existing course_Major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCourse_Major(Course_Major course_Major) throws URISyntaxException {
        log.debug("REST request to update Course_Major : {}", course_Major);
        course_MajorRepository.edit(course_Major);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, course_Major.getId().toString())
                .entity(course_Major).build();
    }

    /**
     * GET : get all the course_Majors.
     *
     * @return the Response with status 200 (OK) and the list of course_Majors
     * in body
     *
     */
    @Timed
    @Operation(summary = "get all the course_Majors")
    @APIResponse(responseCode = "200", description = "OK")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Course_Major> getAllCourse_Majors() {
        log.debug("REST request to get all Course_Majors");
        List<Course_Major> course_Majors = course_MajorRepository.findAll();
        return course_Majors;
    }

    /**
     * GET /:id : get the "id" course_Major.
     *
     * @param id the id of the course_Major to retrieve
     * @return the Response with status 200 (OK) and with body the course_Major,
     * or with status 404 (Not Found)
     */
    @Timed
    @Operation(summary = "get the course_Major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourse_Major(@PathParam("id") Long id) {
        log.debug("REST request to get Course_Major : {}", id);
        Course_Major course_Major = course_MajorRepository.find(id);
        return Optional.ofNullable(course_Major)
                .map(result -> Response.status(Response.Status.OK).entity(course_Major).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" course_Major.
     *
     * @param id the id of the course_Major to delete
     * @return the Response with status 200 (OK)
     */
    @Timed
    @Operation(summary = "remove the course_Major")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @DELETE
    @Path("/{id}")
    public Response removeCourse_Major(@PathParam("id") Long id) {
        log.debug("REST request to delete Course_Major : {}", id);
        course_MajorRepository.remove(course_MajorRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, id.toString()).build();
    }

}
