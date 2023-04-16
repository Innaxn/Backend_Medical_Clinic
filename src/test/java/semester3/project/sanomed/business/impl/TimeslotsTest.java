package semester3.project.sanomed.business.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.Timeslot;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.Entity.AppointmentEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.UnavailabilityRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeslotsTest {
    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    UnavailabilityRepository unavailabilityRepository;

    @InjectMocks
    Timeslots timeslots;

    LocalDate now = LocalDate.now();
    LocalDate firstDayOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalTime time = LocalTime.of(9, 0);
    LocalDateTime start = LocalDateTime.of(firstDayOfWeek, time);

    @Test
    void createTimeslots() {
        List<Timeslot> actual = timeslots.createTimeslots(start);
        List<Timeslot> expected = timeslots.createTimeslots(start);

        assertEquals(60, actual.size());
        assertEquals(expected, actual);
    }

    @Test
     void testGetLegitTimeslots() {
        EmployeeEntity emp = EmployeeEntity.builder().build();
        PatientEntity patient = PatientEntity.builder().build();
        long id = 1;
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalTime time = LocalTime.of(9, 0);
        LocalDateTime start = LocalDateTime.of(firstDayOfWeek, time);
        LocalDate friday = firstDayOfWeek.plusDays(4);
        LocalDateTime end = LocalDateTime.of(friday, time.plusMinutes(360));
        List<AppointmentEntity> appointments = Arrays.asList(
                new AppointmentEntity(1L,patient,emp, start.plusMinutes(30), "purpose"),
                new AppointmentEntity(2L, patient,emp, start.plusMinutes(90), "purpose")
        );

        when(appointmentRepository.findAppointmentsByWeek(start, end, id)).thenReturn(appointments);


        List<Timeslot> timeslotsList = timeslots.getLegitTimeslots(id, start);

        assertEquals(5 * 12 - 2, timeslotsList.size());
        verify(appointmentRepository).findAppointmentsByWeek(start, end , id);

    }

    @Test
    void testCreateTimeslotsForADay() {
        LocalDate now = LocalDate.now();
        List<Timeslot> actual = timeslots.createTimeslotsForADay(now);
        List<Timeslot> expected = timeslots.createTimeslotsForADay(now);

        assertEquals(12, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetLegitTimeslotsForADay() {
        LocalDate day = LocalDate.of(2023, 1, 15);
        List<AppointmentEntity> appointments = new ArrayList<>();
        LocalDateTime appointmentTime = LocalDateTime.of(day, LocalTime.of(10, 0));
        EmployeeEntity employeeEntity  = EmployeeEntity.builder().id(1L).build();
        AppointmentEntity appointmentEntity = AppointmentEntity.builder().id(1L).doctor(employeeEntity).date(appointmentTime).build();
        appointments.add(appointmentEntity);
        when(appointmentRepository.findAppointmentEntitiesByDateAndAndDoctorId(LocalDateTime.of(day, LocalTime.of(9, 0)), employeeEntity.getId()))
                .thenReturn(appointments);
        List<Timeslot> legitTimeslots = timeslots.getLegitTimeslotsForADay(employeeEntity.getId(), day);

        assertNotNull(legitTimeslots);
        assertEquals(11, legitTimeslots.size());
        assertFalse(legitTimeslots.stream().anyMatch(t -> t.getStartTime().equals(appointmentTime)));
        verify(appointmentRepository).findAppointmentEntitiesByDateAndAndDoctorId(LocalDateTime.of(day, LocalTime.of(9, 0)), employeeEntity.getId());
    }


}