package ford.group.orderapp.api.v1;


import ford.group.orderapp.dto.payment.PaymentDTO;
import ford.group.orderapp.dto.payment.PaymentToSaveDTO;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderNotFoundException;
import ford.group.orderapp.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {
    PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> get(@PathVariable("id") Long id) {
        try {
            PaymentDTO order = paymentService.findPaymentById(id);
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundException err) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentDTO>> index() {
        List<PaymentDTO> orders = paymentService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentDTO>> getByOrder(@PathVariable("orderId") Long id) {
        List<PaymentDTO> orders = paymentService.findByOrder(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentDTO>> getByRange(@RequestParam(name = "startDate") LocalDate startDate,
                                                       @RequestParam(name = "endDate") LocalDate endDate) {
        List<PaymentDTO> orders = paymentService.findPaymentsByPayedAtBetween(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/")
    public ResponseEntity<PaymentDTO> save(@RequestBody PaymentToSaveDTO payment) {
        PaymentDTO savedOrder = paymentService.savePayment(payment);
        return ResponseEntity.ok(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@PathVariable("id") Long id, @RequestBody PaymentToSaveDTO newPayment) {
        PaymentDTO updatedOrder = paymentService.updatePayment(id, newPayment);
        return ResponseEntity.ok(updatedOrder);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            paymentService.removePayment(id);
            return ResponseEntity.ok("removed");
        } catch (NotAbleToDeleteException err) {
            return ResponseEntity
                    .badRequest()
                    .body("id doesn't exist");
        }
    }
}
