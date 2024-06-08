package database;
import Client.Model.Utils.DatabaseUtil;
import Server.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


// Having problems with accessing the proper driver, will look into on Monday
class RegisterUserToDatabase {

    @BeforeEach
    void setUp() throws SQLException {
        clearDatabase();
    }

    @Test
    void testRegisterNewUser() {
        User user = new User("username1", "password123", "user1@example.com");

        assertDoesNotThrow(() -> {
            user.registerUserSpreadsheet("username1", "password123", "user1@example.com") ;
        });

        User retrievedUser = user.getUserInfo("username1");
        assertNotNull(retrievedUser);
        assertEquals("username1", retrievedUser.getUsername());
        assertEquals("password123", retrievedUser.getPassword());
        assertEquals("user1@example.com", retrievedUser.getEmail());
    }

    @Test
    void testRegisterExistingUser() {
        User user = new User("username2", "password123", "user2@example.com");

        // Register the user for the first time
        user.registerUserSpreadsheet("username2", "password123", "user2@example.com") ;

        // Attempt to register the same user again
        assertThrows(IllegalArgumentException.class, () -> {
            user.registerUserSpreadsheet("username2", "password123", "user2@example.com") ;
        });

        User retrievedUser = user.getUserInfo("username2");
        assertNotNull(retrievedUser);
        assertEquals("username2", retrievedUser.getUsername());
    }

    private void clearDatabase() throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users")) {
            preparedStatement.executeUpdate();
        }
    }
}