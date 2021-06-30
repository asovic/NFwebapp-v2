package si.asovic.backend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import si.asovic.backend.data.entity.RoleEntity;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	
//	@Query(value="select r from role r where r.id = '1'")
//	List<RoleEntity> userRole();
}