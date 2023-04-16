package semester3.project.sanomed.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByPersonEmbeddable_LastNameContaining (String lastName);
    @Query("select e from UserRoleEntity r join r.user u join u.emp e where r.role=:roleEnum and e.status=:status_employee and e.id=:id")
    Optional<EmployeeEntity> findEmployeeByRoleAndStatusAndId(long id, RoleEnum roleEnum, Status_Employee status_employee);
    @Query("select e from UserRoleEntity r join r.user u join u.emp e where r.role=:role and e.personEmbeddable.lastName LIKE %:lastName% and e.status=:statusemp")
    List<EmployeeEntity> findByDoctorByLastName (String lastName, RoleEnum role, Status_Employee statusemp);
    @Query("select e from UserRoleEntity r join r.user u join u.emp e where r.role=:roleEnum")
    List<EmployeeEntity> findEmployeeByRole(RoleEnum roleEnum);
    @Query("select e from UserRoleEntity r join r.user u join u.emp e where r.role=:roleEnum and e.status=:status_employee")
    List<EmployeeEntity> findEmployeeByRoleAndStatus(RoleEnum roleEnum, Status_Employee status_employee);
    @Query(value = "SELECT * FROM employee ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<EmployeeEntity> showFive();
   @Query(value = "SELECT e.id, e.first_name_person, e.last_name_person, COUNT(a.id) as appointment_count " +
           "FROM Employee e " +
           "LEFT JOIN Appointment a ON e.id = a.id_doctor " +
           "WHERE MONTH(a.date_appointment) = MONTH(CURRENT_DATE()) " +
           "AND YEAR(a.date_appointment) = YEAR(CURRENT_DATE()) " +
           "GROUP BY e.id, e.first_name_person, e.last_name_person " +
           "ORDER BY appointment_count DESC LIMIT 5",
           nativeQuery = true)
    List<Object[]> getTheTopFiveMostByAppointmentAndTheMonth();
}
