package semester3.project.sanomed.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import semester3.project.sanomed.persistence.Entity.UnavailabilityEntity;

public interface UnavailabilityRepository extends JpaRepository<UnavailabilityEntity, Long> {
}
