package server;

import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publishers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SpreadsheetControllerGETPUBLISHERTest {

    private SpreadsheetController spreadsheetController;

    @Mock
    private Publishers publishers;

    @BeforeEach
    void setUp() {
        publishers = mock(Publishers.class);
        spreadsheetController = new SpreadsheetController();
    }

    @Test
    void testCreateSpreadsheet() {
        // Mocking the behavior of Publishers.registerNewPublisher()
        doNothing().when(publishers).registerNewPublisher(anyString());

        // Call the controller method
        ResponseEntity<String> response = spreadsheetController.register();

        // Verify that the Publishers.registerNewPublisher() method was called
        verify(publishers, times(1)).registerNewPublisher(anyString());

        // Check the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registration successful", response.getBody());
    }
}
