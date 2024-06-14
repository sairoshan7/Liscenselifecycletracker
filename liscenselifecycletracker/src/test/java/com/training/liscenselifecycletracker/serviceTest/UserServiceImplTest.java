package com.training.liscenselifecycletracker.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.training.liscenselifecycletracker.entities.ERole;
import com.training.liscenselifecycletracker.entities.Role;
import com.training.liscenselifecycletracker.entities.User;
import com.training.liscenselifecycletracker.exceptions.UserNotFoundException;
import com.training.liscenselifecycletracker.repositories.UserRepository;
import com.training.liscenselifecycletracker.service.UserServiceImpl;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for updateRole method when user exists
    @Test
    public void testUpdateRole_UserExists() throws UserNotFoundException {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        Role role = new Role();
        role.setId(1); // Change to Integer

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        String result = userService.updateRole(userId, role);

        verify(userRepository, times(1)).save(user);
        assertEquals("Role Updated Successfully!!!", result);
    }

    // Test for updateRole method when user does not exist
    @Test
    public void testUpdateRole_UserNotFound() {
        Long userId = 1L;
        Role role = new Role();
        role.setId(1); // Change to Integer

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateRole(userId, role);
        });
    }

    // Add similar test methods for addUserEntity, findByUsername, existsByUsername, findByRole
    // Test for addUserEntity method
    @Test
    public void testAddUserEntity() {
        User user = new User();
        user.setUserId(1L); // Change to Long

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUserEntity(user);

        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);
    }

    // Test for findByUsername method when user exists
    @Test
    public void testFindByUsername_UserExists() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername(username);

        assertEquals(Optional.of(user), result);
    }

    // Test for findByUsername method when user does not exist
    @Test
    public void testFindByUsername_UserNotFound() {
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername(username);

        assertEquals(Optional.empty(), result);
    }

    // Test for existsByUsername method when user exists
    @Test
    public void testExistsByUsername_UserExists() {
        String username = "testUser";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        Boolean result = userService.existsByUsername(username);

        assertEquals(true, result);
    }

    // Test for existsByUsername method when user does not exist
    @Test
    public void testExistsByUsername_UserNotFound() {
        String username = "testUser";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        Boolean result = userService.existsByUsername(username);

        assertEquals(false, result);
    }

    // Test for findByRole method when user exists
    @Test
    public void testFindByRole_UserExists() {
        ERole role = ERole.ROLE_USER;
        User user = new User();
        user.setRole(new Role(4,role));

        when(userRepository.findByRole(role)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByRole(role);

        assertEquals(Optional.of(user), result);
    }

    // Test for findByRole method when user does not exist
    @Test
    public void testFindByRole_UserNotFound() {
        ERole role = ERole.ROLE_USER;

        when(userRepository.findByRole(role)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByRole(role);

        assertEquals(Optional.empty(), result);
    }
}
