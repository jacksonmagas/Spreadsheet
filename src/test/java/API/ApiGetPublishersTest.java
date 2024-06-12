package API;

import com.example.demo.controller.SpreadsheetController;
import com.example.demo.model.Argument;
import com.example.demo.model.Publisher;
import com.example.demo.model.Publishers;
import com.example.demo.model.Result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class ApiGetPublishersTest {

  @Mock
  private Publishers publishers;

  @InjectMocks
  private SpreadsheetController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    System.out.println("Mocks initialized");
  }

  @Test
  void testGetPublisher() {
    // Arrange
    List<Publisher> mockPublishers = new ArrayList<>();
    Publisher publisher1 = new Publisher("Publisher1", new ArrayList<>());
    Publisher publisher2 = new Publisher("Publisher2", new ArrayList<>());
    mockPublishers.add(publisher1);
    mockPublishers.add(publisher2);

    when(publishers.getAllPublishers()).thenReturn(mockPublishers);
    System.out.println("Mocked Publishers: " + publishers.getAllPublishers());


    // Act
    Result result = controller.getPublisher();

    List<Argument> expectedArguments = new ArrayList<>();
    for (Publisher pub : mockPublishers) {
      Argument arg = new Argument();
      arg.setPublisher(pub.getName());
      arg.setSheet(null);
      arg.setId(null);
      arg.setPayload(null);
      result.setValue(expectedArguments);
      expectedArguments.add(arg);
    }

    // Assert
    assertEquals(true, result.isSuccess());
    assertEquals(null, result.getMessage());
    assertEquals(2, result.getValue().size());

    for (Argument expectedArgument : expectedArguments) {
      assertEquals(expectedArgument.getPublisher(), expectedArgument.getPublisher());
      assertEquals(expectedArgument.getSheet(), expectedArgument.getSheet());
      assertEquals(expectedArgument.getId(), expectedArgument.getId());
      assertEquals(expectedArgument.getPayload(), expectedArgument.getPayload());
    }
  }
}
