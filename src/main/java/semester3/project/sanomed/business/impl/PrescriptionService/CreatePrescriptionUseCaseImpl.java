package semester3.project.sanomed.business.impl.PrescriptionService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.prescription.CreatePrescriptionUseCase;
import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.validations.DiagnoseIdValidator;
import semester3.project.sanomed.business.validations.EmployeeIdValidator;
import semester3.project.sanomed.domain.request.CreatePrescriptionRequest;
import semester3.project.sanomed.domain.response.CreatePrescriptionResponse;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PrescriptionEntity;
import semester3.project.sanomed.persistence.PrescriptionRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreatePrescriptionUseCaseImpl implements CreatePrescriptionUseCase {

    private  EmployeeRepository employeeRepository;
    private DiagnoseRepository diagnoseRepository;
    private  PrescriptionRepository prescriptionRepository;
    private EmployeeIdValidator employeeIdValidator;
    //private DatePrescriptionValidator datePrescriptionValidator;
    private DiagnoseIdValidator diagnoseIdValidator;

    private PrescriptionEntity saveNewPrescription(CreatePrescriptionRequest request) {

        Optional<EmployeeEntity> employeeOptional = employeeRepository.findById(request.getDoctorId());
        Optional<DiagnoseEntity> diagnoseOptional = diagnoseRepository.findById(request.getDiagnoseId());

        if(employeeOptional.isEmpty() ){
            throw new InvalidEmployeeException();
        }

        if(diagnoseOptional.isEmpty() ){
            throw new InvalidDiagnoseException();
        }

        EmployeeEntity employeeEntity = employeeOptional.get();
        DiagnoseEntity diagnoseEntity = diagnoseOptional.get();

        PrescriptionEntity newPrescription = PrescriptionEntity.builder()
                .start(request.getStart())
                .end(request.getEnd())
                .medication(request.getMedication())
                .doctor(employeeEntity)
                .diagnoseEntity(diagnoseEntity)
                .build();
        return prescriptionRepository.save(newPrescription);

    }

    @Override
    public CreatePrescriptionResponse createPrescription(CreatePrescriptionRequest request) {

        employeeIdValidator.validateId(request.getDoctorId());
        diagnoseIdValidator.validateId(request.getDiagnoseId());
        //datePrescriptionValidator.validateDate(request.getStart());

        PrescriptionEntity prescription = saveNewPrescription(request);
        return CreatePrescriptionResponse.builder()
                .id(prescription.getId())
                .build();
    }
}
