/*
package Server.api;

import Server.model.Publisher;
import Server.model.Spreadsheet;
import Server.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/spreadsheet")
public class CreateSpreadsheetController {
    private CreateSpreadsheet createSpreadsheetService;

    public CreateSpreadsheetController() {
        SpreadsheetManager spreadsheetManager = new SpreadsheetManager();
        PublisherDataService publisherDataService = new PublisherDataService();
        UserRegistrationService userRegistrationService = new UserRegistrationService();
        this.createSpreadsheetService = new CreateSpreadsheet(spreadsheetManager, publisherDataService, userRegistrationService);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSpreadsheet(@QueryParam("username") String username, @QueryParam("name") String name,
                                      @QueryParam("rows") int rows, @QueryParam("cols") int cols, List<User> users) {
        try {
            Spreadsheet spreadsheet = createSpreadsheetService.createSpreadsheet(username, users, name, rows, cols);
            return Response.status(Response.Status.CREATED).entity(spreadsheet).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
*/