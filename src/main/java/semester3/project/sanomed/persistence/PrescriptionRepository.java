package semester3.project.sanomed.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import semester3.project.sanomed.persistence.Entity.PrescriptionEntity;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {
    List<PrescriptionEntity> findByDiagnoseEntity_Id(long id);
}
