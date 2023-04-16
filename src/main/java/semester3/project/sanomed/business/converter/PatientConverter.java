package semester3.project.sanomed.business.converter;

import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.persistence.Entity.PatientEntity;

public class PatientConverter {
    private PatientConverter() {
    }

    public static Patient convert(PatientEntity patient) {
        return Patient.builder()
                .id(patient.getId())
                .person(PersonConverter.convert(patient.getPersonEmbeddable()))
                .bsn(patient.getBsn())
                .healthInsuranceNumber(patient.getHealthInsuranceNumber())
                //.diagnose(DiagnosisConverter.convertList(patient.getDiagnosis()))
                .build();
    }

    public static Patient convertWithoutDiagnosis(PatientEntity patient) {
        return Patient.builder()
                .id(patient.getId())
                .person(PersonConverter.convert(patient.getPersonEmbeddable()))
                .bsn(patient.getBsn())
                .healthInsuranceNumber(patient.getHealthInsuranceNumber())
                .build();
    }
}
