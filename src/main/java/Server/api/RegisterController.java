package Server.api;

import Server.model.Publisher;
import Server.model.Publishers;

import com.sun.jdi.connect.Connector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

public class RegisterController {
  private Publishers publishers;

  public RegisterController() {
    this.publishers = Publishers.getInstance();
  }

  @GetMapping("api/v1/register")
  public Response register(@RequestParam String clientName) {
    publishers.registerNewPublisher(clientName);

  return Response.status(Response.Status.OK).entity("Registration successful").build();
  }

  @GetMapping("api/v1/getPublishers")
  public Response getPublisher() {
    List<Publisher> allPublishers = publishers.getAllPublishers();

    List<String> publisherNames = new ArrayList<>();
    for (Publisher publisher : allPublishers) {
      publisherNames.add(publisher.getName());
    }

    String publisherList = String.join("", publisherNames);

    return Response.status(Response.Status.OK).entity("Publishers: " + publisherList).build();
  }
}
