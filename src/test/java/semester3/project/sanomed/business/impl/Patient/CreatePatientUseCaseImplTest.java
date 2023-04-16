package semester3.project.sanomed.business.impl.Patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import semester3.project.sanomed.business.validations.BsnValidator;
import semester3.project.sanomed.business.validations.EmailValidator;
import semester3.project.sanomed.business.validations.HealthInsNumValidator;
import semester3.project.sanomed.domain.request.CreatePatientRequest;
import semester3.project.sanomed.domain.response.CreatePatientResponse;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.PatientRepository;
import semester3.project.sanomed.persistence.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePatientUseCaseImplTest {
    @Mock
    BsnValidator bsnValidatorMock;
    @Mock
    HealthInsNumValidator healthInsNumValidatorMock;
    @Mock
    UserRepository userRepository;
    @Mock
    EmailValidator emailValidatorMock;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    PatientRepository patientRepositoryMock;
    @InjectMocks
    CreatePatientUseCaseImpl createPatientUseCase;

    @Test
    void createPatient() {
        PersonEmbeddable p = PersonEmbeddable.builder().firstName("Inna").lastName("Nedelkova").email("nrdelkova@gmail.com").build();
        PatientEntity patient = PatientEntity.builder().personEmbeddable(p).bsn(5422).healthInsuranceNumber(5422).build();
        PatientEntity expected = PatientEntity.builder().id(1L).personEmbeddable(p).bsn(5422).healthInsuranceNumber(5422).build();

        CreatePatientRequest request = CreatePatientRequest.builder().firstName("Inna").lastName("Nedelkova").email("nrdelkova@gmail.com").bsn(5422).healthInsuranceNumber(5422).build();
        when(patientRepositoryMock.save(patient)).thenReturn(expected);

        CreatePatientResponse response = createPatientUseCase.createPatient(request);

        assertEquals(expected.getId(), response.getId());
        verify(emailValidatorMock, times(1)).validate(p.getEmail());
        verify(healthInsNumValidatorMock, times(1)).validateHealthInsNum(patient.getHealthInsuranceNumber());
        verify(bsnValidatorMock, times(1)).validateBsn(patient.getBsn());
        verify(patientRepositoryMock).save(patient);
    }
}