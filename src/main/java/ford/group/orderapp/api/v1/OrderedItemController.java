package ford.group.orderapp.api.v1;


import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemToSaveDTO;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderedItemNotFoundExcepction;
import ford.group.orderapp.service.OrderedItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order-items")
public class OrderedItemController {
    OrderedItemService orderedItemService;

    public OrderedItemController(OrderedItemService orderedItemService) {
        this.orderedItemService = orderedItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderedItemDTO> get(@PathVariable("id") Long id) {
        try {
            OrderedItemDTO order = orderedItemService.findOrderedItemById(id);
            return ResponseEntity.ok(order);
        } catch (OrderedItemNotFoundExcepction err) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderedItemDTO>> index() {
        List<OrderedItemDTO> orders = orderedItemService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderedItemDTO>> getByOrder(@PathVariable("orderId") Long id) {
        List<OrderedItemDTO> orders = orderedItemService.findOrderedItemByOrder(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderedItemDTO>> getByProduct(@PathVariable("productId") Long id) {
        List<OrderedItemDTO> orders = orderedItemService.findOrderedItemByProduct(id);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/")
    public ResponseEntity<OrderedItemDTO> save(@RequestBody OrderedItemToSaveDTO orderedItem) {
        OrderedItemDTO savedItem = orderedItemService.saveOrderedItem(orderedItem);
        return ResponseEntity.ok(savedItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderedItemDTO> update(@PathVariable("id") Long id, @RequestBody OrderedItemToSaveDTO newItem) {
        OrderedItemDTO updatedItem = orderedItemService.updateOrderedItem(id, newItem);
        return ResponseEntity.ok(updatedItem);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            orderedItemService.removeOrderedItem(id);
            return ResponseEntity.ok("removed");
        } catch (NotAbleToDeleteException err) {
            return ResponseEntity
                    .badRequest()
                    .body("id doesn't exist");
        }
    }
}
