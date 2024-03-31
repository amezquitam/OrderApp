package ford.group.orderapp.service;

import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemToSaveDTO;
import ford.group.orderapp.exception.OrderedItemNotFoundExcepction;

import java.util.List;

public interface OrderedItemService {
    OrderedItemDTO saveOrderedItem(OrderedItemToSaveDTO orderedItemDTO);
    OrderedItemDTO updateOrderedItem(Long id, OrderedItemToSaveDTO orderedItemDTO);
    OrderedItemDTO findOrderedItemById(Long id) throws OrderedItemNotFoundExcepction;
    void removeOrderedItem(Long id);
    List<OrderedItemDTO> findOrderedItemByOrder(Long orderId);
    List<OrderedItemDTO> findOrderedItemByProduct(Long productId);
    Double totalSumOfSalesByProduct(Long productId);
}
