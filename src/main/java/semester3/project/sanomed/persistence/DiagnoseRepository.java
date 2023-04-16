package semester3.project.sanomed.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;

import java.util.List;

public interface DiagnoseRepository extends JpaRepository<DiagnoseEntity, Long> {
    List<DiagnoseEntity> findByPatient_Id(long id);
}
