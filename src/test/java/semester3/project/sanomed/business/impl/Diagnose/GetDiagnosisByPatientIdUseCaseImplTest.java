package semester3.project.sanomed.business.impl.Diagnose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.converter.PersonConverter;
import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.Entity.*;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse.DiagnoseData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDiagnosisByPatientIdUseCaseImplTest {
    @Mock
    private DiagnoseRepository diagnoseRepositoryMock;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    private GetDiagnosisByPatientIdUseCaseImpl getDiagnosisByPatientIdUseCaseImpl;


    @Test
    void getDiagnosisByPatientId() {
        PersonEmbeddable oneP = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").phoneNumber("0898939914").birthdate(LocalDate.of(1980,10,1))
                .email( "nrdelkova@gmail.com").build();
        PatientEntity patient = PatientEntity.builder().id(1L).personEmbeddable(oneP).build();
        Patient p = Patient.builder().id(1L).person(PersonConverter.convert(oneP)).build();
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).personEmbeddable(oneP).build();
        Employee e = Employee.builder().id(1L).person(PersonConverter.convert(oneP)).build();

        DiagnoseEntity diagnoseOne = DiagnoseEntity.builder().id(1L).patient(patient).doctor(emp).name("test").details("test").build();
        DiagnoseEntity diagnoseTwo = DiagnoseEntity.builder().id(2L).patient(patient).doctor(emp).name("test").details("test").build();
        when(requestAccessToken.hasRole(RoleEnum.PATIENT.name())).thenReturn(true);
        when(requestAccessToken.getPatientId()).thenReturn(p.getId());
        when(diagnoseRepositoryMock.findByPatient_Id(1L))
                .thenReturn(List.of(diagnoseOne, diagnoseTwo));

        GetDiagnosisResponse actualResult =getDiagnosisByPatientIdUseCaseImpl.getDiagnosisByPatientId(1L);


        DiagnoseData dOne = DiagnoseData.builder().id(1L).name("test").details("test").FirstNamePatient("Ivana").LastNamePatient("Nedelkova").pId(1L).dId(1L).FirstNameDoctor("Ivana")
                .LastNameDoctor("Nedelkova").build();
        DiagnoseData dTwo = DiagnoseData.builder().id(2L).name("test").details("test").FirstNamePatient("Ivana").LastNamePatient("Nedelkova").pId(1L).dId(1L).FirstNameDoctor("Ivana")
                .LastNameDoctor("Nedelkova").build();


        GetDiagnosisResponse expectedResult = GetDiagnosisResponse
                .builder()
                .diagnosis(List.of(dOne, dTwo))
                .build();

        assertEquals(expectedResult,actualResult);

        verify(diagnoseRepositoryMock).findByPatient_Id(1L);
    }

    @Test
    void getDiagnosis_shouldThrowException() throws InvalidDiagnoseException {
        when(diagnoseRepositoryMock.findByPatient_Id(1L))
                .thenReturn(Collections.emptyList());

        InvalidDiagnoseException thrown = assertThrows(InvalidDiagnoseException.class, () ->{
            getDiagnosisByPatientIdUseCaseImpl.getDiagnosisByPatientId(1L);
        }, "INVALID_DIAGNOSE_ID");

        assertEquals("404 NOT_FOUND \"INVALID_DIAGNOSE_ID\"", thrown.getMessage());
    }
}