package semester3.project.sanomed.business.impl.appointment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.appointment.CreateAppointmentUseCase;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.business.validations.EmployeeIdValidator;
import semester3.project.sanomed.business.validations.PatientIdValidator;
import semester3.project.sanomed.domain.request.CreateAppointmentRequest;
import semester3.project.sanomed.domain.response.CreateAppointmentResponse;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.*;
import semester3.project.sanomed.persistence.PatientRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {

    private AppointmentRepository appointmentRepository;
    private EmployeeRepository employeeRepository;
    private PatientRepository patientRepository;

    private EmployeeIdValidator employeeIdValidator;
    private PatientIdValidator patientIdValidator;


    @Transactional
    @Override
    public CreateAppointmentResponse createAppointment(CreateAppointmentRequest request) {
        employeeIdValidator.validateId(request.getDoctorId());
        patientIdValidator.validateId(request.getPatientId());

        AppointmentEntity appointment = saveNewAppointment(request);
        return CreateAppointmentResponse.builder()
                .id(appointment.getId())
                .build();
    }

    private AppointmentEntity saveNewAppointment(CreateAppointmentRequest request) {

        Optional<EmployeeEntity> employeeOptional = employeeRepository.findById(request.getDoctorId());
        Optional<PatientEntity> patientOptional = patientRepository.findById(request.getPatientId());


        if(employeeOptional.isEmpty() ){
            throw new InvalidEmployeeException();
        }

        if(patientOptional.isEmpty() ){
            throw new InvalidPatientException();
        }

        EmployeeEntity employeeEntity = employeeOptional.get();
        PatientEntity patientEntity = patientOptional.get();

        AppointmentEntity newAppointment = AppointmentEntity.builder()
                .doctor(employeeEntity)
                .patient(patientEntity)
                .date(request.getStartTime())
                .purpose(request.getPurpose())
                .build();
        return appointmentRepository.save(newAppointment);

    }
}
