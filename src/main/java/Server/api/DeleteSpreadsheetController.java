/*
package Server.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/spreadsheet")
public class DeleteSpreadsheetController {
    private DeleteSpreadsheet deleteSpreadsheetService;

    public DeleteSpreadsheetController() {
        SpreadsheetManager spreadsheetManager = new SpreadsheetManager();
        this.deleteSpreadsheetService = new DeleteSpreadsheet(spreadsheetManager);
    }

    @DELETE
    @Path("/delete/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSpreadsheet(@PathParam("name") String name) {
        try {
            deleteSpreadsheetService.deleteSpreadsheet(name);
            return Response.status(Response.Status.OK).entity("Spreadsheet deleted successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
*/