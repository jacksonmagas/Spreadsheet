package server;

import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publishers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpreadsheetControllerREGISTERTest {

    @Mock
    private Publishers publishers;

    @InjectMocks
    private SpreadsheetController spreadsheetController;

    @Test
    void testRegister() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testClient");


        doNothing().when(publishers).registerNewPublisher("testClient");

        // Call the controller method
        ResponseEntity<String> response = spreadsheetController.register();

        // Verify that the Publishers.registerNewPublisher() method was called with the correct argument
        verify(publishers, times(1)).registerNewPublisher("testClient");

        // Check the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registration successful", response.getBody());
    }
}
