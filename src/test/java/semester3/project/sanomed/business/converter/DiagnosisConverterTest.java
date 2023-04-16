package semester3.project.sanomed.business.converter;

import org.junit.jupiter.api.Test;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DiagnosisConverterTest {

    Date bdOne = new Date(1980, 10, 1);
    Date bdTwo = new Date(1974, 10, 1);
    PersonEmbeddable oneP = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").email( "nrdelkova@gmail.com").phoneNumber("0898939914")
            .birthdate(LocalDate.of(1980,10 ,1)).build();
    PersonEmbeddable twoP = PersonEmbeddable.builder().firstName("Sharenda").lastName("Peeters").email( "sharenda@gmail.com").phoneNumber("0898939915")
            .birthdate(LocalDate.of(1974,10 ,1)).build();

    EmployeeEntity emp = EmployeeEntity.builder()
            .personEmbeddable(oneP)
            .description("description")
            .status(Status_Employee.ACTIVE)
            .build();

    PatientEntity patient= PatientEntity.builder()
            .personEmbeddable(twoP)
            .bsn(123456)
            .healthInsuranceNumber(123456)
            .build();

    Patient p = PatientConverter.convertWithoutDiagnosis(patient);
    Employee e = EmployeeConverter.convert(emp);
    @Test
    void convert() {

        DiagnoseEntity diagnoseEntity = DiagnoseEntity.builder().id(1L).name("name").details("details").doctor(emp).patient(patient).build();
        Diagnose actualObject = Diagnose.builder().id(1L).name("name").details("details").doctor(e).patient(p).build();


        Diagnose expectedObject = DiagnosisConverter.convert(diagnoseEntity);

        assertEquals(expectedObject, actualObject);
    }

    @Test
    void convertList() {
//        DiagnoseEntity diagnoseEntity = DiagnoseEntity.builder().id(1L).name("name").details("details").doctor(emp).patient(patient).build();
//        DiagnoseEntity diagnoseEntity2 = DiagnoseEntity.builder().id(2L).name("name").details("details").doctor(emp).patient(patient).build();
//        Diagnose d = Diagnose.builder().id(1L).name("name").details("details").doctor(e).patient(p).build();
//        Diagnose d2 = Diagnose.builder().id(2L).name("name").details("details").doctor(e).patient(p).build();
//
//        List<Diagnose> actualList = new ArrayList<>();
//        actualList.add(d);
//        actualList.add(d2);
//
//        Optional<Diagnose> actual = Optional.empty();
//        for(Diagnose diag : actualList){
//            actual.stream().toList().add(diag);
//        }
//
//        List<DiagnoseEntity> entities = new ArrayList<>();
//        entities.add(diagnoseEntity);
//        entities.add(diagnoseEntity2);
//
//        Optional<DiagnoseEntity> expect = Optional.empty();
//        for(DiagnoseEntity diag : entities){
//            expect.stream().toList().add(diag);
//        }
//        Optional<Diagnose> expectedList = DiagnosisConverter.convertList(entities);
//
//        assertEquals(expectedList, actual);
    }
}