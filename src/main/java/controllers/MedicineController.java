package controllers;

import entities.Medicine;
import repositories.MedicineRepository;
import repositories.interfaces.IMedicineRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("medicines")
public class MedicineController {
    @Inject
    private IMedicineRepository iMedicineRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMedicine(Medicine medicine) {
        boolean created;

        try {
            created = iMedicineRepository.addMedicine(medicine);
        } catch (ServerErrorException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }

        if (!created) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Medicine can't be created!").build();
        }

        return Response.status(Response.Status.CREATED).entity("Medicine was created successfully!").build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicine(@PathParam("id") int id) {
        Medicine medicine;
        try {
            medicine = iMedicineRepository.getMedicineById(id);
        } catch (ServerErrorException e) {
            return Response.status(500).entity(e.getMessage()).build();
        }

        if (medicine == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Medicine does not exist!").build();
        }

        return Response.status(Response.Status.OK).entity(medicine).build();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMedicineByName(@PathParam("name") String name) {
        List<Medicine> medicines;

        try {
            medicines = iMedicineRepository.searchMedicineByName(name);
        }
        catch (ServerErrorException e) {
            return Response.status(500).entity(e.getMessage()).build();
        }

        if (medicines.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Medicines don't exist!").build();
        }

        return Response.status(Response.Status.OK).entity(medicines).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMedicine(@PathParam("id") int id) {
        boolean deleted;

        try {
            deleted = iMedicineRepository.deleteMedicineById(id);
        } catch (ServerErrorException e) {
            return Response.status(500).entity(e.getMessage()).build();
        }

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Medicine doesn't exist!").build();
        }

        return Response.status(Response.Status.OK).entity("Medicine was deleted successfully!").build();
    }

    @GET
    @Path("/{manufacturer}")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchMedicineByManufacturer(@PathParam("manufacturer") String manufacturer) {
        String result = MedicineRepository.searchMedByManufacturer(manufacturer).toString();

        return result;
    }

    @GET
    @Path("/{fixprice}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cheaperMedThanX(@PathParam("fixprice") int fixprice) {
        List<Medicine> medicines;

        try {
            medicines = iMedicineRepository.cheaperMedThanX(fixprice);
        }
        catch (ServerErrorException e) {
            return Response.status(500).entity(e.getMessage()).build();
        }

        if (medicines.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Medicines don't exist!").build();
        }

        return Response.status(Response.Status.OK).entity(medicines).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMeds() {
        List<Medicine> medicines;
        try {
            medicines = MedicineRepository.getAllMed();
        } catch (ServerErrorException ex) {
            return Response
                    .status(500).entity(ex.getMessage()).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(medicines)
                .build();
    }

}
