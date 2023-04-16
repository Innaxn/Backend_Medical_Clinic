package semester3.project.sanomed.business.impl.Diagnose;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.diagnose.CreateDiagnoseUseCase;
import semester3.project.sanomed.business.validations.EmployeeIdValidator;
import semester3.project.sanomed.business.validations.PatientIdValidator;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.domain.request.CreateDiagnosisRequest;
import semester3.project.sanomed.domain.response.CreateDiagnoseResponse;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.PatientRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateDiagnoseUseCaseImpl implements CreateDiagnoseUseCase {

private final DiagnoseRepository diagnoseRepository;
private final PatientRepository patientRepository;
private final EmployeeRepository employeeRepository;
private final EmployeeIdValidator employeeIdValidator;
private final PatientIdValidator patientIdValidator;


    @Override
    public CreateDiagnoseResponse createDiagnose(CreateDiagnosisRequest request) {

        employeeIdValidator.validateId(request.getDoctorId());
        patientIdValidator.validateId(request.getPatientId());

        DiagnoseEntity savedDiagnose = saveNewDiagnose(request);

        return CreateDiagnoseResponse.builder()
               .id(savedDiagnose.getId())
                .build();
    }


    private DiagnoseEntity saveNewDiagnose(CreateDiagnosisRequest request) {
        //TODO-FIX
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(request.getDoctorId());
        Optional<PatientEntity> patientEntity = patientRepository.findById(request.getPatientId());

        if(!employeeEntity.isPresent()){
            throw new InvalidEmployeeException();
        }

        if(!patientEntity.isPresent()){
            throw new InvalidPatientException();
        }


        PatientEntity patient = patientEntity.get();
        EmployeeEntity emp = employeeEntity.get();

        DiagnoseEntity newDiagnosis = DiagnoseEntity.builder()
                .name(request.getName())
                .details(request.getDetails())
                .patient(patient)
//                .prescriptions(request.getPrescriptions())
                .doctor(emp)
                .build();
        return diagnoseRepository.save(newDiagnosis);
    }
}
