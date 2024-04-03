package ford.group.orderapp.api.v1;


import ford.group.orderapp.dto.shippingdetail.ShippingDetailDTO;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailToSaveDTO;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderedItemNotFoundException;
import ford.group.orderapp.service.ShippingDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shipping")
public class ShippingDetailController {
    ShippingDetailService shippingDetailService;

    public ShippingDetailController(ShippingDetailService shippingDetailService) {
        this.shippingDetailService = shippingDetailService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingDetailDTO> get(@PathVariable("id") Long id) {
        try {
            ShippingDetailDTO order = shippingDetailService.findShippingDetailById(id);
            return ResponseEntity.ok(order);
        } catch (OrderedItemNotFoundException err) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ShippingDetailDTO>> index() {
        List<ShippingDetailDTO> orders = shippingDetailService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ShippingDetailDTO> getByOrder(@PathVariable("orderId") Long id) {
        ShippingDetailDTO orders = shippingDetailService.findShippingDetailByOrder(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/carrier")
    public ResponseEntity<List<ShippingDetailDTO>> getByProduct(@RequestParam(name = "name") String name) {
        List<ShippingDetailDTO> orders = shippingDetailService.findShippingDetailsByDeliverer(name);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/")
    public ResponseEntity<ShippingDetailDTO> save(@RequestBody ShippingDetailToSaveDTO shippingDetail) {
        ShippingDetailDTO savedItem = shippingDetailService.saveShippingDetail(shippingDetail);
        return ResponseEntity.ok(savedItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingDetailDTO> update(@PathVariable("id") Long id, @RequestBody ShippingDetailToSaveDTO shippingDetail) {
        ShippingDetailDTO updatedItem = shippingDetailService.updateShippingDetail(id, shippingDetail);
        return ResponseEntity.ok(updatedItem);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            shippingDetailService.removeShippingDetail(id);
            return ResponseEntity.ok("removed");
        } catch (NotAbleToDeleteException err) {
            return ResponseEntity
                    .badRequest()
                    .body("id doesn't exist");
        }
    }
}
