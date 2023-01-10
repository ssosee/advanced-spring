package spring.advanced.advancedspring.app.v2;

import lombok.RequiredArgsConstructor;
import spring.advanced.advancedspring.app.v1.OrderRepositoryV1;

@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
