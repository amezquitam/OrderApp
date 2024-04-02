package ford.group.orderapp.service;

import ford.group.orderapp.dto.order.OrderMapperImpl;
import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemMapper;
import ford.group.orderapp.dto.ordereditem.OrderedItemMapperImpl;
import ford.group.orderapp.dto.ordereditem.OrderedItemToSaveDTO;
import ford.group.orderapp.dto.product.ProductMapperImpl;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderedItem;
import ford.group.orderapp.entities.Product;
import ford.group.orderapp.repository.OrderRepository;
import ford.group.orderapp.repository.OrderedItemRepository;
import ford.group.orderapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderedItemServiceImplTest {
    @Mock
    private OrderedItemRepository orderedItemRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;

    private OrderedItemServiceImpl orderedItemService;

    @BeforeEach
    void setUp() {
        OrderedItemMapper orderedItemMapper = new OrderedItemMapperImpl();
        orderedItemService = new OrderedItemServiceImpl(orderedItemMapper, orderedItemRepository, orderRepository, productRepository);
    }

    @Test
    void saveOrderedItem() {
        OrderedItemToSaveDTO orderedItemToSaveDTO = new OrderedItemToSaveDTO(null, null, 3L, 12.0);
        OrderedItem orderedItem = new OrderedItem(1L, null, null, 3L, 12.0);
        OrderedItemDTO expectedDTO = new OrderedItemDTO(1L, null, null, 3L, 12.0);

        when(orderedItemRepository.save(any())).thenReturn(orderedItem);

        OrderedItemDTO result = orderedItemService.saveOrderedItem(orderedItemToSaveDTO);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    void updateOrderedItem() {
        Long id = 1L;
        OrderedItemToSaveDTO orderedItemToSaveDTO = new OrderedItemToSaveDTO(null, null, 3L, 12.0);
        OrderedItem orderedItem = new OrderedItem(1L, null, null, 3L, 12.0);
        orderedItem.setId(id);
        OrderedItemDTO expectedDTO = new OrderedItemDTO(1L, null, null, 3L, 12.0);

        when(orderedItemRepository.findById(id)).thenReturn(Optional.of(orderedItem));
        when(orderedItemRepository.save(orderedItem)).thenReturn(orderedItem);

        OrderedItemDTO result = orderedItemService.updateOrderedItem(id, orderedItemToSaveDTO);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    void findOrderedItemById() {
        Long id = 1L;
        OrderedItem orderedItem = new OrderedItem(1L, null, null, 3L, 12.0);
        orderedItem.setId(id);
        OrderedItemDTO expectedDTO = new OrderedItemDTO(1L, null, null, 3L, 12.0);

        when(orderedItemRepository.findById(id)).thenReturn(Optional.of(orderedItem));

        OrderedItemDTO result = orderedItemService.findOrderedItemById(id);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    void removeOrderedItem() {
        Long id = 1L;
        OrderedItem orderedItem = new OrderedItem(id, null, null, 3L, 12.0);
        orderedItem.setId(id);

        when(orderedItemRepository.findById(id)).thenReturn(Optional.of(orderedItem));

        assertDoesNotThrow(() -> orderedItemService.removeOrderedItem(id));
    }

    @Test
    void findOrderedItemByOrder() {
        OrderMapperImpl orderMapper = new OrderMapperImpl();

        Long orderId = 1L;
        Order order = Order.builder().id(orderId).build();
        OrderedItem orderedItem = new OrderedItem(1L, order, null, 3L, 12.0);
        OrderedItemDTO expectedDTO = new OrderedItemDTO(1L, orderMapper.orderToOrderDTO(order), null, 3L, 12.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderedItemRepository.findOrderedItemsByOrder(order)).thenReturn(Collections.singletonList(orderedItem));

        List<OrderedItemDTO> result = orderedItemService.findOrderedItemByOrder(orderId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedDTO, result.get(0));
    }

    @Test
    void findOrderedItemByProduct() {
        ProductMapperImpl productMapper = new ProductMapperImpl();

        Long productId = 1L;
        Product product = Product.builder().id(productId).build();

        OrderedItem orderedItem = new OrderedItem(1L, null, null, 3L, 12.0);
        orderedItem.setProduct(product);

        OrderedItemDTO expectedDTO = new OrderedItemDTO(1L, null, productMapper.productToProductDTO(product), 3L, 12.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderedItemRepository.findOrderedItemsByProduct(product)).thenReturn(Collections.singletonList(orderedItem));

        List<OrderedItemDTO> result = orderedItemService.findOrderedItemByProduct(productId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedDTO, result.get(0));
    }

    @Test
    void totalSumOfSalesByProduct() {
        Long productId = 1L;
        Double totalSum = 100.0;

        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));
        when(orderedItemRepository.totalSumOfSalesByProduct(any(Product.class))).thenReturn(totalSum);

        Double result = orderedItemService.totalSumOfSalesByProduct(productId);

        assertNotNull(result);
        assertEquals(totalSum, result);
    }

    @Test
    void findAll() {
        OrderedItem orderedItem = new OrderedItem(1L, null, null, 3L, 12.0);
        orderedItem.setId(1L);
        OrderedItemDTO expectedDTO = new OrderedItemDTO(1L, null, null, 3L, 12.0);

        when(orderedItemRepository.findAll()).thenReturn(Collections.singletonList(orderedItem));

        List<OrderedItemDTO> result = orderedItemService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedDTO, result.get(0));
    }
}
