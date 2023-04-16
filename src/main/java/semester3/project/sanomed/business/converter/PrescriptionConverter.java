package semester3.project.sanomed.business.converter;

import semester3.project.sanomed.domain.Prescription;
import semester3.project.sanomed.persistence.Entity.PrescriptionEntity;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionConverter {

    private PrescriptionConverter() {
    }

    public static Prescription convert(PrescriptionEntity prescription) {
        return Prescription.builder()
                .id(prescription.getId())
                .start(prescription.getStart())
                .end(prescription.getEnd())
                .medication(prescription.getMedication())
                .doctor(EmployeeConverter.convert(prescription.getDoctor()))
                .build();
    }

    public static List<Prescription> convertList(List<PrescriptionEntity> prescriptions) {
        List<Prescription> dtoPrescription = new ArrayList<>();
        for (PrescriptionEntity p : prescriptions){
            dtoPrescription.add(PrescriptionConverter.convert(p));
        }
        return dtoPrescription;
    }
}
