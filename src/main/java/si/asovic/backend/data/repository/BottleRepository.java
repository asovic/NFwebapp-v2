package si.asovic.backend.data.repository;

import org.springframework.cglib.proxy.LazyLoader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import si.asovic.NFConfig;
import si.asovic.backend.data.entity.BottleEntity;
import si.asovic.backend.data.entity.FlavourEntity;

import java.util.List;

public interface BottleRepository extends JpaRepository<BottleEntity, Long> {

    List<BottleEntity> findByOrderid(Long orderid);
	
//	@Query("select b " +
//			"from bottle b inner join order_test ot on b.orderid = ot.id " +
//			"where b.orderid = :order_id and ot.username = :username")
//	List<BottleEntity> findByOrderid(Long order_id, String username);
//
//	@Query("select b from bottle b where b.orderid =:oid")
//	List<BottleEntity> findByOid(Long oid);
}
