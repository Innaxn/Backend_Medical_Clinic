package semester3.project.sanomed.business.converter;

import semester3.project.sanomed.domain.Diagnose;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse.DiagnoseData;

import java.util.List;
import java.util.Optional;


public class DiagnosisConverter {

    private DiagnosisConverter(){}
    public static Diagnose convert(DiagnoseEntity diagnosis){
        return  Diagnose.builder()
                .id(diagnosis.getId())
                .name(diagnosis.getName())
                .details(diagnosis.getDetails())
//                .prescriptions(PrescriptionConverter.convertList(diagnosis.getPrescriptions()))
                .patient(PatientConverter.convertWithoutDiagnosis(diagnosis.getPatient()))
                .doctor(EmployeeConverter.convert(diagnosis.getDoctor()))
                .build();
    }

    public static DiagnoseData convertDataForDisplaying(DiagnoseEntity diagnoseEntity) {
        return  GetDiagnosisResponse.DiagnoseData.builder()
                .id(diagnoseEntity.getId())
                .name(diagnoseEntity.getName())
                .details(diagnoseEntity.getDetails())
                .pId(diagnoseEntity.getPatient().getId())
                .FirstNamePatient(diagnoseEntity.getPatient().getPersonEmbeddable().getFirstName())
                .LastNamePatient(diagnoseEntity.getPatient().getPersonEmbeddable().getLastName())
                .bsn(diagnoseEntity.getPatient().getBsn())
                .healthInsuranceNumber(diagnoseEntity.getPatient().getHealthInsuranceNumber())
                .dId(diagnoseEntity.getDoctor().getId())
                .FirstNameDoctor(diagnoseEntity.getDoctor().getPersonEmbeddable().getFirstName())
                .LastNameDoctor(diagnoseEntity.getDoctor().getPersonEmbeddable().getLastName())
                .build();
    }
}
