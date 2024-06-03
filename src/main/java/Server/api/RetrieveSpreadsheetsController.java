package Server.api;

import Server.model.Spreadsheet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/spreadsheets")
public class RetrieveSpreadsheetsController {
    private final SpreadsheetManager spreadsheetManager = new SpreadsheetManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpreadsheets() {
        Collection<Spreadsheet> spreadsheets = spreadsheetManager.getAllSpreadsheets();
        return Response.status(Response.Status.OK).entity(spreadsheets).build();
    }
}
