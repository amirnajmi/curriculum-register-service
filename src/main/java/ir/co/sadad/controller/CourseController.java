package ir.co.sadad.controller;

import ir.co.sadad.domain.Course;
import ir.co.sadad.repository.CourseRepository;
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
 * REST controller for managing Course.
 */
@Path("/api/course")
@RolesAllowed(USER)
public class CourseController {

    @Inject
    private Logger log;

    @Inject
    private CourseRepository courseRepository;

    private static final String ENTITY_NAME = "curricolumnRegisterServiceCourse";

    /**
     * POST : Create a new course.
     *
     * @param course the course to create
     * @return the Response with status 201 (Created) and with body the new
     * course, or with status 400 (Bad Request) if the course has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "create a new course", description = "Create a new course")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCourse(Course course) throws URISyntaxException {
        log.debug("REST request to save Course : {}", course);
        courseRepository.create(course);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/course/" + course.getId())),
                ENTITY_NAME, course.getId().toString())
                .entity(course).build();
    }

    /**
     * PUT : Updates an existing course.
     *
     * @param course the course to update
     * @return the Response with status 200 (OK) and with body the updated
     * course, or with status 400 (Bad Request) if the course is not valid, or
     * with status 500 (Internal Server Error) if the course couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "update course", description = "Updates an existing course")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCourse(Course course) throws URISyntaxException {
        log.debug("REST request to update Course : {}", course);
        courseRepository.edit(course);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, course.getId().toString())
                .entity(course).build();
    }

    /**
     * GET : get all the courses.
     *
     * @return the Response with status 200 (OK) and the list of courses in body
     *
     */
    @Timed
    @Operation(summary = "get all the courses")
    @APIResponse(responseCode = "200", description = "OK")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Course> getAllCourses() {
        log.debug("REST request to get all Courses");
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    /**
     * GET /:id : get the "id" course.
     *
     * @param id the id of the course to retrieve
     * @return the Response with status 200 (OK) and with body the course, or
     * with status 404 (Not Found)
     */
    @Timed
    @Operation(summary = "get the course")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourse(@PathParam("id") Long id) {
        log.debug("REST request to get Course : {}", id);
        Course course = courseRepository.find(id);
        return Optional.ofNullable(course)
                .map(result -> Response.status(Response.Status.OK).entity(course).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" course.
     *
     * @param id the id of the course to delete
     * @return the Response with status 200 (OK)
     */
    @Timed
    @Operation(summary = "remove the course")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @DELETE
    @Path("/{id}")
    public Response removeCourse(@PathParam("id") Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseRepository.remove(courseRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, id.toString()).build();
    }

}
