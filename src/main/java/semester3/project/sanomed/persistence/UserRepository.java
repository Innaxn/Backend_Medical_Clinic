package semester3.project.sanomed.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import semester3.project.sanomed.persistence.Entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByPatient_Id(long id);
    Optional<UserEntity> findByEmp_Id(long id);

}
