package controller;

import entity.Subject;
import service.SubjectService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/subjects")
public class SubjectController {

    SubjectService subjectService = new SubjectService();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Subject> getListSubject() {
        return subjectService.getListSubject();
    }

}
