package semester3.project.sanomed.business.converter;

import semester3.project.sanomed.domain.Appointment;
import semester3.project.sanomed.persistence.Entity.AppointmentEntity;

public class AppointmentConverter {
    private AppointmentConverter() {
    }

    public static Appointment convert(AppointmentEntity appointmentEntity) {
        return Appointment.builder()
                .id(appointmentEntity.getId())
                .doctor(EmployeeConverter.convert(appointmentEntity.getDoctor()))
                .start(appointmentEntity.getDate())
                .patient(PatientConverter.convertWithoutDiagnosis(appointmentEntity.getPatient()))
                .purpose(appointmentEntity.getPurpose())
                .build();
    }
}
