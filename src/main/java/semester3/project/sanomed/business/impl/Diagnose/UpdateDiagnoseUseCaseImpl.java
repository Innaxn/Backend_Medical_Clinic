package semester3.project.sanomed.business.impl.Diagnose;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.diagnose.UpdateDiagnoseUseCase;
import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.domain.request.UpdateDiagnoseRequest;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateDiagnoseUseCaseImpl implements UpdateDiagnoseUseCase {

    private final DiagnoseRepository diagnoseRepository;
    private final EmployeeRepository employeeRepository;


    @Override
    public void updateDiagnose(UpdateDiagnoseRequest request) {
        Optional<DiagnoseEntity> optional = diagnoseRepository.findById(request.getId());
        if (optional.isEmpty()){
            throw new InvalidDiagnoseException();
        }

        DiagnoseEntity diagnose = optional.get();
        updateObjectFields(request, diagnose);
    }
    public void updateObjectFields(UpdateDiagnoseRequest request, DiagnoseEntity diagnose){

        Optional<EmployeeEntity> employeeEntityOpt = employeeRepository.findById(request.getDoctorId());

        if(employeeEntityOpt.isEmpty()){
            throw new InvalidEmployeeException();
        }
        EmployeeEntity employeeEntity = employeeEntityOpt.get();

        diagnose.setId(request.getId());
        diagnose.setName(request.getName());
        diagnose.setDetails(request.getDescription());
        diagnose.setPatient(diagnose.getPatient());
        diagnose.setDoctor(employeeEntity);
        diagnoseRepository.save(diagnose);
    }
}
