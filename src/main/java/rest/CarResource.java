package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.GenericExceptionMapper;
import facade.ApiFacade;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Fen
 */
@Path("car")
public class CarResource {

    @Context
    private UriInfo context;
    
    private final GenericExceptionMapper GEM = new GenericExceptionMapper();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private ApiFacade facade = new ApiFacade();

    /**
     * Creates a new instance of CarResource
     */
    public CarResource() {
    }

    /**
     * Retrieves representation of an instance of rest.CarResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/availablecars/{week}/{address}")
    public Response getAvailableCars(@PathParam("week") String week, @PathParam("address") String address) {
        try {       
            //facade.getAllCarData(week, address);
            //System.out.println(cars);
            return Response.ok().entity(facade.getAllCarData(week, address).replace("]\n[", ",")).build();
        } catch (Exception ex) {
            return GEM.toResponse(ex);

        }
    }

    /**
     * PUT method for updating or creating an instance of CarResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
