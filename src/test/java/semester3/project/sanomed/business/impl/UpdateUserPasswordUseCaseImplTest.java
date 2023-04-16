package semester3.project.sanomed.business.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import semester3.project.sanomed.business.exceptions.InvalidUserException;
import semester3.project.sanomed.business.validations.PasswordValidator;
import semester3.project.sanomed.domain.request.UpdateUserPaswordRequest;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.Entity.RoleEnum;
import semester3.project.sanomed.persistence.Entity.UserEntity;
import semester3.project.sanomed.persistence.Entity.UserRoleEntity;
import semester3.project.sanomed.persistence.UnavailabilityRepository;
import semester3.project.sanomed.persistence.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserPasswordUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordValidator passwordValidator;
    @Mock
    private  PasswordEncoder passwordEncoder;

    @InjectMocks
    UpdateUserPasswordUseCaseImpl updateUserPasswordUseCase;

    @Test
    public void updatePassword_correctCurrentPassword_passwordUpdated() {
        UserRoleEntity role = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.DOCTOR)
                .build();
        Set<UserRoleEntity> set = new HashSet<>();
        set.add(role);

        UpdateUserPaswordRequest request = new UpdateUserPaswordRequest(1L, "correctPassword", "newPassword",  RoleEnum.PATIENT);
        UserEntity user = UserEntity.builder().id(1L).email("test@example.com").password("correctPassword").userRoles(set).build();

        when(userRepository.findByPatient_Id(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        updateUserPasswordUseCase.updatePassword(request);

        verify(passwordValidator).comparePasswords("correctPassword", "correctPassword");
        verify(userRepository).save(user);
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    public void updateObjectFields_validInput_fieldsUpdated() {
        UserRoleEntity role = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.DOCTOR)
                .build();
        Set<UserRoleEntity> set = new HashSet<>();
        set.add(role);
        UpdateUserPaswordRequest request = new UpdateUserPaswordRequest(1L,  "correctPassword", "newPassword", RoleEnum.PATIENT);
        UserEntity user = UserEntity.builder().id(1L).email("test@example.com").password("correctPassword").userRoles(set).build();

        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        updateUserPasswordUseCase.updateObjectFields(request, user);

        verify(userRepository).save(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    public void testGetUser_userNotFound_exceptionThrown() {
        long id = 1;
        RoleEnum role = RoleEnum.PATIENT;

        when(userRepository.findByPatient_Id(id)).thenReturn(Optional.empty());

        try {
            updateUserPasswordUseCase.getUser(id, role);
            fail("Expected InvalidUserException to be thrown");
        } catch (InvalidUserException e) {
            // Expected exception
        }
    }


}