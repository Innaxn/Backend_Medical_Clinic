package semester3.project.sanomed.business.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.TimeslotsUseCase;
import semester3.project.sanomed.domain.Timeslot;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.Entity.AppointmentEntity;
import semester3.project.sanomed.persistence.UnavailabilityRepository;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class Timeslots implements TimeslotsUseCase {
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Timeslot> createTimeslots(LocalDateTime mondayOfTheWeek) {
        List<Timeslot> ts = new ArrayList<>();
        LocalDate firstDayOfWeek = mondayOfTheWeek.toLocalDate();
        LocalTime time = mondayOfTheWeek.toLocalTime();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                if (time == LocalTime.of(12, 00)) {
                    time = time.plusMinutes(60);
                }
                LocalDateTime start = LocalDateTime.of(firstDayOfWeek.plusDays(i), time);
                Timeslot tsTemp = new Timeslot(start, start.plusMinutes(30));
                ts.add(tsTemp);
                time = time.plusMinutes(30);
            }
            time = LocalTime.of(9, 0);

        }
        return ts;
    }

    @Override
    public List<Timeslot> createTimeslotsForADay(LocalDate day) {
        List<Timeslot> ts = new ArrayList<>();
        LocalDate givenDay = day;
        LocalTime time = LocalTime.of(9,00,00,00);

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 12; j++) {
                if (time == LocalTime.of(12, 00)) {
                    time = time.plusMinutes(60);
                }
                LocalDateTime start = LocalDateTime.of(givenDay.plusDays(i), time);
                Timeslot tsTemp = new Timeslot(start, start.plusMinutes(30));
                ts.add(tsTemp);
                time = time.plusMinutes(30);
            }
            time = LocalTime.of(9, 0);

        }
        return ts;
    }

    @Override
    public List<Timeslot> getLegitTimeslots(Long id, LocalDateTime mondayOfTheWeek) {
        LocalTime time = LocalTime.of(9, 0);
        LocalDateTime start = mondayOfTheWeek;

        LocalDate friday = mondayOfTheWeek.toLocalDate().plusDays(4);
        LocalDateTime end = LocalDateTime.of(friday, time.plusMinutes(360));
        List<Timeslot> ts = createTimeslots(mondayOfTheWeek);
        List<AppointmentEntity> appointments = appointmentRepository.findAppointmentsByWeek(start, end, id);
        List<Timeslot> toRemove = new ArrayList<>();

        for (Timeslot timeslot : ts) {
            for (AppointmentEntity appointmentsEntity : appointments) {
                if (timeslot.getStartTime().isBefore(appointmentsEntity.getDate()) || timeslot.getStartTime().isEqual(appointmentsEntity.getDate())) {
                    if (timeslot.getEndTime().isAfter(appointmentsEntity.getDate())) {
                        toRemove.add(timeslot);
                    }
                }
            }
        }
        ts.removeAll(toRemove);
        return ts;
    }

    @Override
    public List<Timeslot> getLegitTimeslotsForADay(Long id, LocalDate day) {
        LocalTime time = LocalTime.of(9, 0);
        LocalDateTime start = LocalDateTime.of(day, time);

        List<Timeslot> ts = createTimeslotsForADay(day);
        List<AppointmentEntity> appointments = appointmentRepository.findAppointmentEntitiesByDateAndAndDoctorId(start, id);
        List<Timeslot> toRemove = new ArrayList<>();

        for (Timeslot timeslot : ts) {
            for (AppointmentEntity appointmentsEntity : appointments) {
                if (timeslot.getStartTime().isBefore(appointmentsEntity.getDate()) || timeslot.getStartTime().isEqual(appointmentsEntity.getDate())) {
                    if (timeslot.getEndTime().isAfter(appointmentsEntity.getDate())) {
                        toRemove.add(timeslot);
                    }
                }
            }
        }
        ts.removeAll(toRemove);
        return ts;
    }
}
