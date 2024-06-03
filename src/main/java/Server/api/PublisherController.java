package Server.api;

import Server.model.Publisher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/publisher")
public class PublisherController {
    private final PublisherDataService publisherDataService = new PublisherDataService();

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublisherData(@PathParam("username") String username) {
        Publisher publisher = publisherDataService.getPublisherByUsername(username);
        if (publisher != null) {
            return Response.status(Response.Status.OK).entity(publisher).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Publisher not found").build();
        }
    }
}
