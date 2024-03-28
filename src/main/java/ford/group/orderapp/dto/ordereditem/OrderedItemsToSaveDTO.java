package ford.group.orderapp.dto.ordereditem;

import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.product.ProductDTO;

public record OrderedItemsToSaveDTO(OrderDTO order,
                                    ProductDTO product,
                                    Long requestedAmount,
                                    Float unitPrice) {
}
