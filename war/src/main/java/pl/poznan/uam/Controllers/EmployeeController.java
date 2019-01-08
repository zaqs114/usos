package pl.poznan.uam.Controllers;

import pl.poznan.uam.DAO.PersonDAO;
import pl.poznan.uam.DTOs.EmployeeDTO;
import pl.poznan.uam.DTOs.PersonDTO;
import pl.poznan.uam.DTOs.PersonShortDTO;
import pl.poznan.uam.DTOs.StudentDTO;
import pl.poznan.uam.Utils.PersonToEntity;
import pl.poznan.uam.entities.PersonEntity;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("employee")
public class EmployeeController {

    @EJB
    private PersonDAO personDAO;

    @GET
    @Produces("application/json; charset=UTF-8")
    public Response getAll() {
        List<EmployeeDTO> employeeList = personDAO.getPeopleWhoAreNotStudents().stream().map(EmployeeDTO::new).collect(Collectors.toList());
        return Response.status(200).entity(employeeList).build();
    }

    @POST
    @Path("addEmployee")
    @Consumes("application/json; charset=UTF-8")
    @Produces("application/json; charset=UTF-8")
    public Response addEmployee(EmployeeDTO employeeDTO) {
        PersonEntity personEnt = personDAO.addPerson(PersonToEntity.employeeToEntity(employeeDTO));
        EmployeeDTO finalEmployeeDTO = new EmployeeDTO(personEnt);
        return Response.status(201).entity(finalEmployeeDTO).build();
    }
}
