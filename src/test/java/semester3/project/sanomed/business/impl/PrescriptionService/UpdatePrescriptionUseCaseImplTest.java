package semester3.project.sanomed.business.impl.PrescriptionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.UpdatePrescriptionRequest;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.Entity.PrescriptionEntity;
import semester3.project.sanomed.persistence.PrescriptionRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePrescriptionUseCaseImplTest {
    @Mock
    private  PrescriptionRepository prescriptionRepository;
    @Mock
    private  EmployeeRepository employeeRepository;
    @InjectMocks
    UpdatePrescriptionUseCaseImpl updatePrescriptionUseCase;

    @Test
    public void testUpdateObjectFields() {
        PersonEmbeddable p = PersonEmbeddable.builder().firstName("Inna").lastName("Nedelkova").email("nrdelkova@gmail.com").build();
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).personEmbeddable(p).status(Status_Employee.ACTIVE).build();
        UpdatePrescriptionRequest request = UpdatePrescriptionRequest.builder().id(1L).medication("new medication").doctorId(1L)
                .start(LocalDate.of(2022, 12, 21)).build();

        PrescriptionEntity prescription = PrescriptionEntity.builder().id(1L).medication("meds").start(LocalDate.of(2022, 12, 21))
                .doctor(emp).build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));


        updatePrescriptionUseCase.updateObjectFields(request, prescription);

        assertEquals(1, prescription.getId());
        assertEquals("new medication", prescription.getMedication());
        assertEquals(emp, prescription.getDoctor());
    }

    @Test
    public void testUpdateObjectFields_InvalidEmployee() {
        UpdatePrescriptionRequest request = UpdatePrescriptionRequest.builder().id(1L).start(LocalDate.of(2022, 12, 1))
                .end(LocalDate.of(2022, 12, 31)).medication("new meds").doctorId(999L).build();

        PrescriptionEntity prescription = PrescriptionEntity.builder().id(1L).start(LocalDate.of(2021, 12, 1))
                .end(LocalDate.of(2021, 12, 31)).medication("meds")
                .build();

        try {
            updatePrescriptionUseCase.updateObjectFields(request, prescription);
            fail("Expected InvalidEmployeeException to be thrown");
        } catch (InvalidEmployeeException e) {
            assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"",  e.getMessage());
        }
    }

}