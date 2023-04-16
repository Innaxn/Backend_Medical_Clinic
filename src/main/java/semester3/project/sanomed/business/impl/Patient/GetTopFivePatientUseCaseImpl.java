package semester3.project.sanomed.business.impl.Patient;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.patient.GetTopFivePatientUseCase;
import semester3.project.sanomed.domain.response.GeneralStatisticsResponse;
import semester3.project.sanomed.persistence.PatientRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetTopFivePatientUseCaseImpl implements GetTopFivePatientUseCase {
    private PatientRepository patientRepository;

    @Override
    public List<GeneralStatisticsResponse> getStatistics() {
        List<Object[]> results = patientRepository.getTheTopFiveByMostDiagnoseCount();
        List<GeneralStatisticsResponse> patientsStatisticsList = new ArrayList<>();

        for (Object[] result : results) {
            Number id = (Number) result[0];
            String firstName = (String) result[1];
            String lastName =(String) result[2];
            Integer appointmentCount = ((BigInteger) result[3]).intValue();
            GeneralStatisticsResponse employeeStat = GeneralStatisticsResponse.builder().id(id.longValue()).firstName(firstName).lastName(lastName)
                    .count(appointmentCount).build();
            patientsStatisticsList.add(employeeStat);

        }

        return patientsStatisticsList;
    }

}
