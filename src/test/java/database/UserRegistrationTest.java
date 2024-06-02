package database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Server.model.User;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRegistrationTest {

    private Map<String, User> users = new HashMap<>();



        @Test
        public void testRegisterNewUser() {


            boolean registered = users.registerUser("username", "password123", "kmwink21@gmail.com");

            assertTrue(registered, "New user should be registered successfully");
            assertNotNull(users.getUser("testuser"), "Registered user should exist in the database");
        }


        @Test
        public void testRegisterUserWithExistingUsername() {

            User existingUser = new User("existinguser", "existingpassword");
            users.registerUser(existingUser);


            User newUser = new User("existinguser", "newpassword123", "fakeEmail");
            boolean registered = users.registerUser(newUser);

            assertFalse(registered, "User with existing username should not be registered again");
        }



        @Test
        public void testRegisterUserWithInvalidUsername() {
            User invalidUser = new User("", "password123");
            boolean registered = database.registerUser(invalidUser);

            assertFalse(registered, "User with invalid username should not be registered");
        }

        @Test
        public void testRegisterUserWithWeakPassword() {

            User weakPasswordUser = new User("weakuser", "123");
            boolean registered = database.registerUser(weakPasswordUser);

            assertFalse(registered, "User with weak password should not be registered");
        }



        @Test
        public void testUserExists() {

            database.registerNewUser("existinguser", "existingpassword");

            assertTrue(database.userExists("existinguser"));
        }

        @Test
        public void testUserNotExists() {

            assertFalse(database.userExists("nonexistentuser"));
        }
    }




}
