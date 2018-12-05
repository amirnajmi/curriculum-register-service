package ir.co.sadad.restclients.exceptionmappers;

import ir.co.sadad.restclients.exceptions.UnknownException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
public class UnknownExceptionMapper implements ResponseExceptionMapper<UnknownException> {

    @Inject
    private Logger log;



    @Override
    public UnknownException toThrowable(Response response) {
        return new UnknownException();
    }

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        log.error("status = " + status);
        return status == 500;
    }



}
