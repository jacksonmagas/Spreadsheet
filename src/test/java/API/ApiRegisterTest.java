package API;

import com.example.huskysheet.controller.SpreadsheetController;
import com.example.huskysheet.model.Publishers;
import com.example.huskysheet.model.Result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiRegisterTest {

    @Mock
    private Publishers publishers;

    @InjectMocks
    private SpreadsheetController spreadsheetController;

    @Test
    void testRegister() {
        // Mock the security context to simulate an authenticated user
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testClient");


        // Call the controller method
        Result response = spreadsheetController.register();

        // Check the response
      assertTrue(response.isSuccess());
      assertNull(response.getMessage());
        assertEquals(0, response.getValue().size());
    }
}
