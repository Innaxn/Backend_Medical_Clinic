package semester3.project.sanomed.business.interfaces.patient;

import semester3.project.sanomed.domain.response.GeneralStatisticsResponse;

import java.util.List;

public interface GetTopFivePatientUseCase {
    List<GeneralStatisticsResponse> getStatistics();
}
