package si.asovic.backend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.asovic.backend.data.entity.UserLoginsEntity;

public interface UserLoginsRepository extends JpaRepository<UserLoginsEntity, Long> {
}
