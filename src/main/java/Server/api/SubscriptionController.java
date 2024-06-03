package Server.api;

import Server.model.Spreadsheet;
import Server.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/subscription")
public class SubscriptionController {
    private final SubscriptionManager subscriptionManager = new SubscriptionManager();
    private final SpreadsheetManager spreadsheetManager = new SpreadsheetManager();
    private final UserRegistrationService userRegistrationService = new UserRegistrationService();

    @POST
    @Path("/subscribe")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response subscribe(@QueryParam("username") String username, @QueryParam("spreadsheetName") String spreadsheetName) {
        User user = userRegistrationService.getUser(username);
        Spreadsheet spreadsheet = spreadsheetManager.getSpreadsheet(spreadsheetName);
        if (user == null || spreadsheet == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user or spreadsheet").build();
        }
        subscriptionManager.unsubscribe(spreadsheet, user);
        return Response.status(Response.Status.OK).entity("Unsubscribed successfully").build();
    }

    @GET
    @Path("/subscribers/{spreadsheetName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscribers(@PathParam("spreadsheetName") String spreadsheetName) {
        Spreadsheet spreadsheet = spreadsheetManager.getSpreadsheet(spreadsheetName);
        if (spreadsheet == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Spreadsheet not found").build();
        }
        return Response.status(Response.Status.OK).entity(subscriptionManager.getSubscribers(spreadsheet)).build();
    }
}
