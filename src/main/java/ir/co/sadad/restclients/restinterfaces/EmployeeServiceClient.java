package ir.co.sadad.restclients.restinterfaces;


import ir.co.sadad.restclients.domain.Student;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Dependent
@RegisterRestClient
@RegisterProvider(UnknownError.class)
@Path("/api/employee")
public interface EmployeeServiceClient {


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") Long id);

}
