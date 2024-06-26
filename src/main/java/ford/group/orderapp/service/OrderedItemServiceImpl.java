package ford.group.orderapp.service;

import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemMapper;
import ford.group.orderapp.dto.ordereditem.OrderedItemToSaveDTO;
import ford.group.orderapp.entities.OrderedItem;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderNotFoundException;
import ford.group.orderapp.exception.OrderedItemNotFoundException;

import ford.group.orderapp.exception.ProductNotFoundException;
import ford.group.orderapp.repository.OrderRepository;
import ford.group.orderapp.repository.OrderedItemRepository;
import ford.group.orderapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedItemServiceImpl implements OrderedItemService{
    private final OrderedItemMapper orderedItemMapper;
    private final OrderedItemRepository orderedItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderedItemServiceImpl(OrderedItemMapper orderedItemMapper, OrderedItemRepository orderedItemRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderedItemMapper = orderedItemMapper;
        this.orderedItemRepository = orderedItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderedItemDTO saveOrderedItem(OrderedItemToSaveDTO orderedItemDTO) {
        OrderedItem orderedItem = orderedItemMapper.orderedItemSaveDTOToOrderedItem(orderedItemDTO);
        OrderedItem orderedItemSaved = orderedItemRepository.save(orderedItem);
        return orderedItemMapper.orderedItemToOrderedItemDTO(orderedItemSaved);
    }

    @Override
    public OrderedItemDTO updateOrderedItem(Long id, OrderedItemToSaveDTO orderedItemDTO) {
        return orderedItemRepository.findById(id).map(orderedItemInDB -> {
            orderedItemInDB.setOrder(orderedItemMapper.orderedItemSaveDTOToOrderedItem(orderedItemDTO).getOrder());
            orderedItemInDB.setProduct(orderedItemMapper.orderedItemSaveDTOToOrderedItem(orderedItemDTO).getProduct());
            orderedItemInDB.setUnitPrice(orderedItemDTO.unitPrice());
            OrderedItem orderedItemSaved = orderedItemRepository.save(orderedItemInDB);
            return orderedItemMapper.orderedItemToOrderedItemDTO(orderedItemSaved);
        }).orElseThrow(() -> new OrderedItemNotFoundException("Item de pedido no encontrado"));
    }

    @Override
    public OrderedItemDTO findOrderedItemById(Long id) throws OrderedItemNotFoundException {
        OrderedItem orderedItem = orderedItemRepository.findById(id).orElseThrow(OrderedItemNotFoundException::new);
        return orderedItemMapper.orderedItemToOrderedItemDTO(orderedItem);
    }

    @Override
    public void removeOrderedItem(Long id) {
        OrderedItem orderedItem = orderedItemRepository.findById(id).orElseThrow(NotAbleToDeleteException::new);
        orderedItemRepository.delete(orderedItem);
    }

    @Override
    public List<OrderedItemDTO> findOrderedItemByOrder(Long orderId) {
        List<OrderedItem> orderedItems = orderedItemRepository.findOrderedItemsByOrder(orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new));
        return orderedItems.stream().map(orderedItemMapper::orderedItemToOrderedItemDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderedItemDTO> findOrderedItemByProduct(Long productId) {
        List<OrderedItem> orderedItems = orderedItemRepository.findOrderedItemsByProduct(productRepository.findById(productId).orElseThrow(ProductNotFoundException::new));
        return orderedItems.stream().map(orderedItemMapper::orderedItemToOrderedItemDTO).collect(Collectors.toList());
    }

    @Override
    public Double totalSumOfSalesByProduct(Long productId) {
        return orderedItemRepository.totalSumOfSalesByProduct(productRepository.findById(productId).orElseThrow(ProductNotFoundException::new));
    }

    @Override
    public List<OrderedItemDTO> findAll() {
        return orderedItemRepository.findAll().stream()
                .map(orderedItemMapper::orderedItemToOrderedItemDTO)
                .collect(Collectors.toList());
    }
}
