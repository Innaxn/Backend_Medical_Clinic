package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.employee.GetTopFiveEmployeesForTheMonthUseCase;
import semester3.project.sanomed.domain.response.GeneralStatisticsResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GetTopFiveEmployeesForTheMonthUseCaseImpl implements GetTopFiveEmployeesForTheMonthUseCase {
    private EmployeeRepository employeeRepository;

    @Override
    public List<GeneralStatisticsResponse> getStatistics() {
        List<Object[]> results = employeeRepository.getTheTopFiveMostByAppointmentAndTheMonth();
        List<GeneralStatisticsResponse> employeeStatisticsList = new ArrayList<>();

        for (Object[] result : results) {
            Number id = (Number) result[0];
            String firstName = (String) result[1];
            String lastName =(String) result[2];
            Integer appointmentCount = ((BigInteger) result[3]).intValue();
            GeneralStatisticsResponse employeeStat = GeneralStatisticsResponse.builder().id(id.longValue()).firstName(firstName).lastName(lastName)
                    .count(appointmentCount).build();
            employeeStatisticsList.add(employeeStat);

        }

        return employeeStatisticsList;
    }
}
