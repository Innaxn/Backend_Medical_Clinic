package semester3.project.sanomed.business.interfaces.employee;

import semester3.project.sanomed.domain.response.GeneralStatisticsResponse;

import java.util.List;

public interface GetTopFiveEmployeesForTheMonthUseCase {
    List<GeneralStatisticsResponse> getStatistics();
}
