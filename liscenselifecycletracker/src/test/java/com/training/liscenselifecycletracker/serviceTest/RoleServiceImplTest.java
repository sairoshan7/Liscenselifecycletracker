package com.training.liscenselifecycletracker.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.training.liscenselifecycletracker.entities.ERole;
import com.training.liscenselifecycletracker.entities.Role;
import com.training.liscenselifecycletracker.repositories.RoleRepository;
import com.training.liscenselifecycletracker.service.RoleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void testFindRoleByName_RoleExists() {
        // Creating a dummy role
        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        // Mocking the behavior of the repository method
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));

        // Calling the service method
        Optional<Role> result = roleService.findRoleByName(ERole.ROLE_USER);

        // Asserting the result
        assertTrue(result.isPresent());
        assertEquals(role, result.get());
    }

    @Test
    public void testFindRoleByName_RoleDoesNotExist() {
        // Mocking the behavior of the repository method
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.empty());

        // Calling the service method
        Optional<Role> result = roleService.findRoleByName(ERole.ROLE_USER);

        // Asserting the result
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindRoleById_RoleExists() {
        // Creating a dummy role
        Role role = new Role();
        role.setId(1);
        role.setName(ERole.ROLE_USER);

        // Mocking the behavior of the repository method
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        // Calling the service method
        Optional<Role> result = roleService.findRoleById(1);

        // Asserting the result
        assertTrue(result.isPresent());
        assertEquals(role, result.get());
    }

    @Test
    public void testFindRoleById_RoleDoesNotExist() {
        // Mocking the behavior of the repository method
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        // Calling the service method
        Optional<Role> result = roleService.findRoleById(1);

        // Asserting the result
        assertFalse(result.isPresent());
    }
}
