package semester3.project.sanomed.business.impl.PrescriptionService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.prescription.UpdatePrescriptionUseCase;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidPrescriptionException;
import semester3.project.sanomed.domain.request.UpdatePrescriptionRequest;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PrescriptionEntity;
import semester3.project.sanomed.persistence.PrescriptionRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdatePrescriptionUseCaseImpl implements UpdatePrescriptionUseCase {

    private final PrescriptionRepository prescriptionRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void updatePrescription(UpdatePrescriptionRequest request) {
        Optional<PrescriptionEntity> optional = prescriptionRepository.findById(request.getId());
        if (optional.isEmpty()){
            throw new InvalidPrescriptionException();
        }

        PrescriptionEntity prescr = optional.get();
        updateObjectFields(request, prescr);
    }
    public void updateObjectFields(UpdatePrescriptionRequest request, PrescriptionEntity prescription){

        prescription.setId(request.getId());
        prescription.setStart(request.getStart());
        prescription.setEnd(request.getEnd());
        prescription.setMedication(request.getMedication());
        Optional<EmployeeEntity> employeeEntityOpt = employeeRepository.findById(request.getDoctorId());

        if(employeeEntityOpt.isEmpty()){
            throw new InvalidEmployeeException();
        }

        EmployeeEntity employeeEntity = employeeEntityOpt.get();

        prescription.setDoctor(employeeEntity);
        prescriptionRepository.save(prescription);
    }

}
