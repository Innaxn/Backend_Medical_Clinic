package semester3.project.sanomed.business;

import semester3.project.sanomed.domain.Timeslot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TimeslotsUseCase {
    List<Timeslot> createTimeslots(LocalDateTime mondayOfTheWeek);
    List<Timeslot> createTimeslotsForADay(LocalDate mondayOfTheWeek);

    List<Timeslot> getLegitTimeslots(Long id, LocalDateTime mondayOfTheWeek);
    List<Timeslot> getLegitTimeslotsForADay(Long id, LocalDate mondayOfTheWeek);
}
