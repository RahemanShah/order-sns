package in.ex.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private String orderId;

    private String userId;
    private String productId;
    private int quantity;
    private double amount;
    private LocalDateTime orderDate;
}

