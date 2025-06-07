package in.ex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ex.Entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

}
