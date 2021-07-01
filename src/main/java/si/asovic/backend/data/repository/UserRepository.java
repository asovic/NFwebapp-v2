package si.asovic.backend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.asovic.backend.data.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{
	UserEntity findByUsername(String username);
}
