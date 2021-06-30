package si.asovic.backend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import si.asovic.backend.data.entity.OrderEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	List<OrderEntity> findByUsername(String username);
//
//	@Query(value="select o from order_test o where o.status = '0'")
//	List<OrderEntity> findUnfilled();
//
//	@Transactional
//	@Modifying
//	@Query(value="update order_test o set o.status = '1' where o.id = :oid")
//	void markAsFilled(Long oid);
//
//	//query za brisanje
//	@Transactional
//	@Modifying
//	@Query(value = "delete from order_test o where o.id = :oid and o.username = :username")
//	void deleteByIdAndUsername(Long oid, String username);
}