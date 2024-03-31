package ford.group.orderapp.service;

import ford.group.orderapp.dto.shippingdetail.ShippingDetailDTO;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailMapper;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailToSaveDTO;
import ford.group.orderapp.entities.OrderStatus;
import ford.group.orderapp.entities.ShippingDetail;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderNotFoundException;
import ford.group.orderapp.exception.ShippingDetailNotFoundException;
import ford.group.orderapp.repository.OrderRepository;
import ford.group.orderapp.repository.ShippingDetailRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShippingDetailServiceImpl implements ShippingDetailService{
    private final ShippingDetailRepository shippingDetailRepository;
    private final ShippingDetailMapper shippingDetailMapper;
    private final OrderRepository orderRepository;

    public ShippingDetailServiceImpl(ShippingDetailRepository shippingDetailRepository, ShippingDetailMapper shippingDetailMapper, OrderRepository orderRepository) {
        this.shippingDetailRepository = shippingDetailRepository;
        this.shippingDetailMapper = shippingDetailMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public ShippingDetailDTO saveShippingDetail(ShippingDetailToSaveDTO shippingDetailDTO) {
        ShippingDetail shippingDetail = shippingDetailMapper.shippingDetailSaveDTOToShippingDetail(shippingDetailDTO);
        ShippingDetail shippingDetailSaved = shippingDetailRepository.save(shippingDetail);
        return shippingDetailMapper.shippingDetailToShippingDetailDTO(shippingDetailSaved);
    }

    @Override
    public ShippingDetailDTO updateShippingDetail(Long id, ShippingDetailToSaveDTO shippingDetailDTO) {
        return shippingDetailRepository.findById(id).map(shippingDetailInDB -> {
            shippingDetailInDB.setAddress(shippingDetailDTO.address());
            shippingDetailInDB.setOrder(shippingDetailMapper.shippingDetailSaveDTOToShippingDetail(shippingDetailDTO).getOrder());
            shippingDetailInDB.setDeliverer(shippingDetailDTO.deliverer());
            shippingDetailInDB.setTrackingNumber(shippingDetailDTO.trackingNumber());
            ShippingDetail shippingDetailSaved = shippingDetailRepository.save(shippingDetailInDB);
            return shippingDetailMapper.shippingDetailToShippingDetailDTO(shippingDetailSaved);
        }).orElseThrow(() -> new ShippingDetailNotFoundException("Detalles de envio no encontrado"));
    }

    @Override
    public ShippingDetailDTO findShippingDetailById(Long id) throws ShippingDetailNotFoundException {
        ShippingDetail shippingDetail = shippingDetailRepository.findById(id).orElseThrow(ShippingDetailNotFoundException::new);
        return shippingDetailMapper.shippingDetailToShippingDetailDTO(shippingDetail);
    }

    @Override
    public void removeShippingDetail(Long id) {
        ShippingDetail shippingDetail = shippingDetailRepository.findById(id).orElseThrow(NotAbleToDeleteException::new);
        shippingDetailRepository.delete(shippingDetail);
    }

    @Override
    public ShippingDetailDTO findShippingDetailByOrder(Long orderId) {
        ShippingDetail shippingDetail = shippingDetailRepository.findShippingDetailByOrder(orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new));
        if(Objects.isNull(shippingDetail))
            throw new ShippingDetailNotFoundException("Detalles de envio no encontrado");
        return shippingDetailMapper.shippingDetailToShippingDetailDTO(shippingDetail);
    }

    @Override
    public List<ShippingDetailDTO> findShippingDetailsByDeliverer(String deliverer) {
        List<ShippingDetail> shippingDetails = shippingDetailRepository.findShippingDetailsByDeliverer(deliverer);
        if (shippingDetails.isEmpty())
            throw new ShippingDetailNotFoundException("Detalles de envios no encontrados");
        return shippingDetails.stream().map(shippingDetailMapper::shippingDetailToShippingDetailDTO).collect(Collectors.toList());
    }

    @Override
    public List<ShippingDetailDTO> findShippingDetailsByOrderStatus(OrderStatus orderStatus) {
        List<ShippingDetail> shippingDetails = shippingDetailRepository.findShippingDetailsByOrderStatus(orderStatus);
        if (shippingDetails.isEmpty())
            throw new ShippingDetailNotFoundException("Detalles de envios no encontrados");
        return shippingDetails.stream().map(shippingDetailMapper::shippingDetailToShippingDetailDTO).collect(Collectors.toList());
    }
}
