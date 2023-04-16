package semester3.project.sanomed.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import semester3.project.sanomed.persistence.Entity.AppointmentEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query("select a from AppointmentEntity a where a.date between :start and :lastDay and a.doctor.id=:id")
    List<AppointmentEntity> findAppointmentsByWeek(LocalDateTime start, LocalDateTime lastDay, Long id); //otdatado data

    List<AppointmentEntity> findAppointmentEntitiesByDoctor_IdAndDateAfter(long docId, LocalDateTime today);
    List<AppointmentEntity> findAppointmentEntitiesByPatient_IdAndDateAfter(long docId, LocalDateTime today);
    List<AppointmentEntity> findAppointmentEntitiesByDateAndAndDoctorId(LocalDateTime today, long docId);
}
