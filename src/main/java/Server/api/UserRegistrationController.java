/*
package Server.api;

import Server.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserRegistrationController {
    private UserRegistrationService userRegistrationService = new UserRegistrationService();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) {
        boolean isRegistered = userRegistrationService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
        if (isRegistered) {
            return Response.status(Response.Status.CREATED).entity("User registered successfully").build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) {
        User loggedInUser = userRegistrationService.login(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            return Response.status(Response.Status.OK).entity(loggedInUser).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
    }

    @POST
    @Path("/registeredAsPublisher")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerAsPublisher(@QueryParam("username") String username) {
        boolean isRegistered = userRegistrationService.registerAsPublisher(username);
        if (isRegistered) {
            return Response.status(Response.Status.OK).entity("User registered as publisher successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("User does not exist or is already a publisher").build();
        }
    }
}
*/