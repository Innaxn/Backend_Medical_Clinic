package semester3.project.sanomed.business.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import semester3.project.sanomed.business.AccessTokenEncoder;
import semester3.project.sanomed.business.exceptions.InvalidUserException;
import semester3.project.sanomed.domain.request.LoginRequest;
import semester3.project.sanomed.domain.response.LoginResponse;
import semester3.project.sanomed.persistence.Entity.*;
import semester3.project.sanomed.persistence.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    LoginUseCaseImpl loginUseCase;

    @Test
    void loginWithValidCredentials() {
        UserRoleEntity role = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.DOCTOR)
                .build();
        Set<UserRoleEntity> set = new HashSet<>();
        set.add(role);
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("email@gmail.com")
                .password("encodedpwd")
                .userRoles(set)
                .build();
        when(userRepository.findByEmail("email@gmail.com")).thenReturn(userEntity);
        when(passwordEncoder.matches("rawpwd", "encodedpwd")).thenReturn(true);
        when(accessTokenEncoder.encode(any())).thenReturn("accessToken");

        LoginRequest loginRequest = LoginRequest.builder()
                .email("email@gmail.com")
                .password("rawpwd")
                .build();

        LoginResponse actualResponse = loginUseCase.login(loginRequest);
        LoginResponse expectedResponse = LoginResponse.builder()
                .accessToken("accessToken")
                .build();

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findByEmail("email@gmail.com");
    }

    @Test
    public void testLoginWithInvalidUser() {
        LoginRequest loginRequest = new LoginRequest("user@gmail.com", "password");

        when(userRepository.findByEmail("user@gmail.com")).thenReturn(null);


        assertThrows(InvalidUserException.class, () -> loginUseCase.login(loginRequest));
    }

    @Test
    public void testMatchesPassword() {
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);

        boolean result = passwordEncoder.matches("rawPassword", "encodedPassword");

        assertTrue(result);
    }

}