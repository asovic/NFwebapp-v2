package si.asovic.backend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.asovic.backend.data.entity.ShoppingCartEntity;

import java.util.List;

public interface CartRepository extends JpaRepository<ShoppingCartEntity, Long> {

    List<ShoppingCartEntity> findByUsername(String username);
}
