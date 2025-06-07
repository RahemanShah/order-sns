package in.ex.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.ex.Entity.OrderEntity;
import in.ex.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final SnsClient snsClient;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderEntity order) throws JsonProcessingException {
        // Set the order date explicitly
        order.setOrderDate(LocalDateTime.now());

        // Save to DB
        orderRepository.save(order);

        // Convert order to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(order);

        // Publish to SNS topic
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn("arn:aws:sns:us-east-1:000000000000:order-events")
                .message(message)
                .build();

        snsClient.publish(publishRequest);

        return ResponseEntity.ok("Order saved and published successfully");
    }
}
