package semester3.project.sanomed.business.validations;

import semester3.project.sanomed.business.exceptions.InvalidPatientException;

public interface PatientIdValidator {
    void validateId(Long patientId)throws InvalidPatientException;
}
