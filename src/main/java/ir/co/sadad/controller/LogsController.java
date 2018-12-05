package ir.co.sadad.controller;

import ir.co.sadad.controller.vm.LoggerVM;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Controller for view and managing Log Level at runtime.
 */
@Path("/api/logs")
public class LogsController {

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public List<LoggerVM> getList() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        return context.getLoggerList()
                .stream()
                .map(LoggerVM::new)
                .collect(Collectors.toList());
    }

    @PUT
    @Timed
    public void changeLevel(LoggerVM jsonLogger) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(jsonLogger.getName()).setLevel(Level.valueOf(jsonLogger.getLevel()));
    }
}
