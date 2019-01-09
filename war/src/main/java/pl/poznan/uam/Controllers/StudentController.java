package pl.poznan.uam.Controllers;

import pl.poznan.uam.DAO.PersonDAO;
import pl.poznan.uam.DTOs.PersonShortDTO;
import pl.poznan.uam.DTOs.StudentDTO;
import pl.poznan.uam.Utils.PersonToEntity;
import pl.poznan.uam.entities.PersonEntity;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("student")
public class StudentController {

    @EJB
    private PersonDAO personDAO;

    @GET
    @Produces("application/json; charset=UTF-8")
    public Response getAll() {
        List<StudentDTO> studentList = personDAO.getAllByPosition("student").stream().map(StudentDTO::new).collect(Collectors.toList());
        return Response.status(200).entity(studentList).build();
    }

    @POST
    @Path("add")
    @Consumes("application/json; charset=UTF-8")
    @Produces("application/json; charset=UTF-8")
    public Response addStudent(StudentDTO studentDTO) {
        PersonEntity personEnt = personDAO.addPerson(PersonToEntity.studentToEntity(studentDTO));
        StudentDTO finalStudentDTO = new StudentDTO(personEnt);
        return Response.status(201).entity(finalStudentDTO).build();
    }

    //TODO Delete and Put
    @DELETE
    @Path("{id}")
    @Consumes("application/json; charset=UTF-8")
    @Produces("application/json; charset=UTF-8")
    public Response removeBook(@PathParam("id") long id) {
        personDAO.remove(id);
        return Response.status(204).build();
    }

}
