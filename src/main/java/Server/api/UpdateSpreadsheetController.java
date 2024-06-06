/*
package Server.api;

import Server.model.Spreadsheet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/spreadsheet")
public class UpdateSpreadsheetController {
    private UpdateSpreadsheet updateSpreadsheetService;

    public UpdateSpreadsheetController() {
        SpreadsheetManager spreadsheetManager = new SpreadsheetManager();
        this.updateSpreadsheetService = new UpdateSpreadsheet(spreadsheetManager);
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSpreadsheet(@QueryParam("name") String name, @QueryParam("row") int row,
                                      @QueryParam("col") int col, @QueryParam("value") String value,
                                      @QueryParam("version") int version) {
        try {
            updateSpreadsheetService.editCells(name, row, col, value, version);
            return Response.status(Response.Status.OK).entity("Spreadsheet updated successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
*/