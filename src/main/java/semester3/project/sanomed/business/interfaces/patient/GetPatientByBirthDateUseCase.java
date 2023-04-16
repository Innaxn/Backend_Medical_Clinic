package semester3.project.sanomed.business.interfaces.patient;

import semester3.project.sanomed.domain.response.GetPatientsResponse;

import java.util.Date;

public interface GetPatientByBirthDateUseCase {
    GetPatientsResponse getPatientsByBd(Date birthdate);
}
