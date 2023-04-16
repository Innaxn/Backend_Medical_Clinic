package semester3.project.sanomed.business.impl.Patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.domain.response.GetPatientsResponse;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.PatientRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPatientsUseCaseImplTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    GetPatientsUseCaseImpl getPatientsUseCase;

    @Test
    void getPatients() {
        PersonEmbeddable personEmbeddable = PersonEmbeddable.builder().firstName("Shar").lastName("Peeters").build();
        Person person = Person.builder().firstName("Shar").lastName("Peeters").build();

        PatientEntity patientEntity = PatientEntity.builder().id(1L).bsn(1234).healthInsuranceNumber(1234).personEmbeddable(personEmbeddable).build();
        Patient patient = Patient.builder().id(1L).bsn(1234).healthInsuranceNumber(1234).person(person).build();

        when(patientRepository.findAll())
                .thenReturn(List.of(patientEntity));

        GetPatientsResponse actual = getPatientsUseCase.getPatients();
        GetPatientsResponse expected = GetPatientsResponse.builder().patients(List.of(patient)).build();

        assertEquals(expected, actual);
        verify(patientRepository).findAll();
    }
}