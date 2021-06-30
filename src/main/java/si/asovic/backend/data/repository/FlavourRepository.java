package si.asovic.backend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.asovic.backend.data.entity.FlavourEntity;

public interface FlavourRepository extends JpaRepository<FlavourEntity, Long> {
}
