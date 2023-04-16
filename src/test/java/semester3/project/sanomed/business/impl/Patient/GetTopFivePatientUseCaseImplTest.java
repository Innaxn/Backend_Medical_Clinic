package semester3.project.sanomed.business.impl.Patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.domain.response.GeneralStatisticsResponse;
import semester3.project.sanomed.persistence.PatientRepository;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTopFivePatientUseCaseImplTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    GetTopFivePatientUseCaseImpl getTopFivePatientUseCase;

    @Test
    void getStatistics() {
        Object[] result1 = {1, "Ivana", "Nedelkova", BigInteger.valueOf(10)};
        Object[] result2 = {2, "Dara", "Nedelkova", BigInteger.valueOf(5)};
        Object[] result3 = {3, "Shar", "Peeters", BigInteger.valueOf(8)};
        Object[] result4 = {4, "Alex", "Vasileva", BigInteger.valueOf(7)};
        Object[] result5 = {5, "Ana", "Hopper", BigInteger.valueOf(9)};
        List<Object[]> results = Arrays.asList(result1, result2, result3, result4,result5);

        when(patientRepository.getTheTopFiveByMostDiagnoseCount()).thenReturn(results);

        GeneralStatisticsResponse expected1 = GeneralStatisticsResponse.builder()
                .id(1L)
                .firstName("Ivana")
                .lastName("Nedelkova")
                .count(10)
                .build();

        GeneralStatisticsResponse expected2 = GeneralStatisticsResponse.builder()
                .id(2L)
                .firstName("Dara")
                .lastName("Nedelkova")
                .count(5)
                .build();

        GeneralStatisticsResponse expected3 = GeneralStatisticsResponse.builder()
                .id(3L)
                .firstName("Shar")
                .lastName("Peeters")
                .count(8)
                .build();
        GeneralStatisticsResponse expected4 = GeneralStatisticsResponse.builder()
                .id(4L)
                .firstName("Alex")
                .lastName("Vasileva")
                .count(7)
                .build();
        GeneralStatisticsResponse expected5 = GeneralStatisticsResponse.builder()
                .id(5L)
                .firstName("Ana")
                .lastName("Hopper")
                .count(9)
                .build();

        List<GeneralStatisticsResponse> expected = Arrays.asList(expected1, expected2, expected3, expected4, expected5);

        List<GeneralStatisticsResponse> actual = getTopFivePatientUseCase.getStatistics();

        assertEquals(expected, actual);
    }
}