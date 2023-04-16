package semester3.project.sanomed.business.impl.appointment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.appointment.UpdateAppointmentUseCase;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.domain.request.UpdateAppointmentRequest;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.AppointmentEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.PatientRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateAppointmentUseCaseImpl implements UpdateAppointmentUseCase {
    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final PatientRepository patientRepository;

    @Override
    public void updateAppointment(UpdateAppointmentRequest request) {
        Optional<AppointmentEntity> optional = appointmentRepository.findById(request.getId());
        if (optional.isEmpty()){
            throw new InvalidAppointmentException();
        }

        AppointmentEntity appointmentEntity = optional.get();
        updateObjectFields(request, appointmentEntity);
    }

    public void updateObjectFields(UpdateAppointmentRequest request, AppointmentEntity appointment){

        appointment.setId(request.getId());
        appointment.setDate(request.getStartDate());
        appointment.setPurpose(request.getPurpose());

        Optional<EmployeeEntity> employeeEntityOpt = employeeRepository.findById(request.getDocId());
        if(employeeEntityOpt.isEmpty()){
            throw new InvalidEmployeeException();
        }
        EmployeeEntity employeeEntity = employeeEntityOpt.get();
        appointment.setDoctor(employeeEntity);

        Optional<PatientEntity> patientEntityOptional = patientRepository.findById(request.getPatientid());
        if(patientEntityOptional.isEmpty()){
            throw new InvalidPatientException();
        }
        PatientEntity patientEntity = patientEntityOptional.get();
        appointment.setPatient(patientEntity);
        appointmentRepository.save(appointment);
    }
}
