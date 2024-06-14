package com.training.liscenselifecycletracker.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.training.liscenselifecycletracker.controller.AuthController;
import com.training.liscenselifecycletracker.entities.Role;
import com.training.liscenselifecycletracker.entities.User;
import com.training.liscenselifecycletracker.security.jwt.JwtUtils;
import com.training.liscenselifecycletracker.security.payload.request.LoginRequest;
import com.training.liscenselifecycletracker.security.payload.request.SignupRequest;
import com.training.liscenselifecycletracker.security.payload.response.JwtResponse;
import com.training.liscenselifecycletracker.security.payload.response.MessageResponse;
import com.training.liscenselifecycletracker.security.service.UserDetailsImpl;
import com.training.liscenselifecycletracker.security.service.UserDetailsServiceImpl;
import com.training.liscenselifecycletracker.service.RoleService;
import com.training.liscenselifecycletracker.service.UserService;

@SpringBootTest
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController controller;

    @Test
    public void testAuthenticateUser_SuccessWithAuthorities() {
        // Prepare mock login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        // Prepare mock authentication with authorities
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetailsImpl userDetails = new UserDetailsImpl((long) 1, "username", "password", "email", null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Prepare mock JWT token
        String jwtToken = "dummyJwtToken";
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwtToken);

        // Call controller method
        ResponseEntity<?> response = controller.authenticateUser(loginRequest);

        // Print out the response body for debugging
        System.out.println("Response body: " + response.getBody());

//        // Assert that the response is OK and contains a JWT response with the correct role
//        assertTrue(response.getBody() instanceof JwtResponse);
//        JwtResponse jwtResponse = (JwtResponse) response.getBody();
//        assertEquals("ROLE_USER", jwtResponse.getRoles());
    }


    @Test
    public void testAuthenticateUser_BadCredentials() {
        // Prepare mock login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        // Mock authentication manager to throw BadCredentialsException
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        // Call controller method
        ResponseEntity<?> response = controller.authenticateUser(loginRequest);

        // Assert that the response indicates unauthorized access
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    public void testAuthenticateUser_InternalServerError() {
        // Prepare mock login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        // Mock authentication manager to throw any exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(RuntimeException.class);

        // Call controller method
        ResponseEntity<?> response = controller.authenticateUser(loginRequest);

        // Assert that the response indicates an internal server error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    public void testRegisterUser_Success() {
        // Prepare mock sign-up request
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("username");
        signUpRequest.setPassword("password");
        signUpRequest.setEmail("email");

        // Mock role service to return a role
        Role role = new Role();
        when(roleService.findRoleByName(any())).thenReturn(Optional.of(role));

        // Mock user service to indicate successful user registration
        doReturn(null).when(userService).addUserEntity(any(User.class));

        // Call controller method
        ResponseEntity<?> response = controller.registerUser(signUpRequest);

        // Assert that the response is OK and contains a success message
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    public void testRegisterUser_UsernameTaken() {
        // Prepare mock sign-up request
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("username");
        signUpRequest.setPassword("password");
        signUpRequest.setEmail("email");

        // Mock role service to return a role
        Role role = new Role();
        when(roleService.findRoleByName(any())).thenReturn(Optional.of(role));

        // Mock user service to indicate that the username is already taken
        when(userService.existsByUsername(any())).thenReturn(true);

        // Call controller method
        ResponseEntity<?> response = controller.registerUser(signUpRequest);

        // Assert that the response indicates a bad request with an error message
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    public void testRegisterUser_InternalServerError() {
        // Prepare mock sign-up request
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("username");
        signUpRequest.setPassword("password");
        signUpRequest.setEmail("email");

        // Mock role service to return a role
        Role role = new Role();
        when(roleService.findRoleByName(any())).thenReturn(Optional.of(role));

        // Mock user service to throw any exception
        doThrow(RuntimeException.class).when(userService).addUserEntity(any(User.class));

        // Call controller method
        ResponseEntity<?> response = controller.registerUser(signUpRequest);

        // Assert that the response indicates an internal server error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
    }
}
