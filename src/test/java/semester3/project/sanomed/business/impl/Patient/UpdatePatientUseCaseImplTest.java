package semester3.project.sanomed.business.impl.Patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import semester3.project.sanomed.business.AccessTokenEncoder;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.business.impl.LoginUseCaseImpl;
import semester3.project.sanomed.business.validations.EmailValidator;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.UpdateBasicPatientRequest;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.Entity.RoleEnum;
import semester3.project.sanomed.persistence.Entity.UserEntity;
import semester3.project.sanomed.persistence.PatientRepository;
import semester3.project.sanomed.persistence.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePatientUseCaseImplTest {
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private AccessToken requestAccessToken;

    @InjectMocks
    UpdatePatientUseCaseImpl updatePatientUseCase;


    @Test
    public void testUpdatePatient() {
        long patientId = 1L;
        UpdateBasicPatientRequest request = UpdateBasicPatientRequest.builder()
                .id(patientId)
                .firstName("Ivana")
                .lastName("Nedelkova")
                .email("email@email.com")
                .phone("1234567890")
                .healthInsNum(123456789)
                .build();
        PatientEntity patientEntity = PatientEntity.builder()
                .id(patientId)
                .personEmbeddable(new PersonEmbeddable("Ivana", "Nedelkova", "email@email.com", "0987654321", LocalDate.now()))
                .healthInsuranceNumber(987654321)
                .build();
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("email@email.com")
                .patient(patientEntity)
                .build();
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patientEntity));
        when(userRepository.findByPatient_Id(patientId)).thenReturn(Optional.of(userEntity));
        when(requestAccessToken.hasRole(RoleEnum.PATIENT.name())).thenReturn(true);
        when(requestAccessToken.getPatientId()).thenReturn(patientId);

        updatePatientUseCase.updatePatient(request);

        verify(patientRepository).findById(patientId);
        verify(userRepository).findByPatient_Id(patientId);
        verify(requestAccessToken).hasRole(RoleEnum.PATIENT.name());
        verify(requestAccessToken).getPatientId();

        assertEquals(request.getFirstName(), patientEntity.getPersonEmbeddable().getFirstName());
        assertEquals(request.getLastName(), patientEntity.getPersonEmbeddable().getLastName());
        assertEquals(request.getEmail(), patientEntity.getPersonEmbeddable().getEmail());
        assertEquals(request.getPhone(), patientEntity.getPersonEmbeddable().getPhoneNumber());
        assertEquals(request.getHealthInsNum(), patientEntity.getHealthInsuranceNumber());
        assertEquals(request.getEmail(), userEntity.getEmail());
    }

    @Test
    public void testUpdatePatient_unhappyFlow() {
        UpdateBasicPatientRequest request = UpdateBasicPatientRequest.builder()
                .id(1L)
                .firstName("Ivana")
                .lastName("Nedelkova")
                .email("email@email.com")
                .phone("1234567890")
                .healthInsNum(123456789)
                .build();

        when(patientRepository.findById(request.getId()))
                .thenReturn(Optional.empty());

        InvalidPatientException thrown = assertThrows(
                InvalidPatientException.class,
                () -> updatePatientUseCase.updatePatient(request)
        );

        assertEquals("404 NOT_FOUND \"INVALID_PATIENT_ID\"", thrown.getMessage());
        verify(patientRepository).findById(request.getId());
    }
}