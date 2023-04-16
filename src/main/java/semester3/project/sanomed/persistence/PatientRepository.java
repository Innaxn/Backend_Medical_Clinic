package semester3.project.sanomed.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import semester3.project.sanomed.persistence.Entity.PatientEntity;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    @Query("SELECT p FROM PatientEntity p WHERE CAST(p.bsn AS string)  LIKE %:bsnValue%")
    List<PatientEntity> findByBsnContaining(String bsnValue);

    boolean existsByBsn(Integer bsn);
    boolean existsByHealthInsuranceNumber(Integer health_ins_num);

    @Query(value = "SELECT p.id, p.first_name_person, p.last_name_person, COUNT(d.id) as count\n" +
            "FROM patient p\n" +
            "LEFT JOIN diagnose d ON p.id = d.patient_id \n" +
            "GROUP BY p.id,p.first_name_person, p.last_name_person \n" +
            "ORDER BY count DESC\n" +
            "LIMIT 5;",
            nativeQuery = true)
    List<Object[]> getTheTopFiveByMostDiagnoseCount();

}
