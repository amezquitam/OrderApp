package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderNotFoundException;
import ford.group.orderapp.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable("id") Long id) {
        try {
            OrderDTO order = orderService.findOrderById(id);
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundException err) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> index() {
        List<OrderDTO> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getByCustomer(@PathVariable("customerId") Long id) {
        List<OrderDTO> orders = orderService.findOrdersByClient(id);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDTO>> getByRange(@RequestParam(name = "startDate") LocalDate startDate,
                                                     @RequestParam(name = "endDate") LocalDate endDate) {
        List<OrderDTO> orders = orderService.findOrdersBetweenTwoDates(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/")
    public ResponseEntity<OrderDTO> save(@RequestBody OrderToSaveDTO orderToSave) {
        OrderDTO savedOrder = orderService.saveOrder(orderToSave);
        return ResponseEntity.ok(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable("id") Long id, @RequestBody OrderToSaveDTO newOrder) {
        OrderDTO updatedOrder = orderService.updateOrder(id, newOrder);
        return ResponseEntity.ok(updatedOrder);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            orderService.removeOrder(id);
            return ResponseEntity.ok("removed");
        } catch (NotAbleToDeleteException err) {
            return ResponseEntity
                    .badRequest()
                    .body("id doesn't exist");
        }
    }

}
