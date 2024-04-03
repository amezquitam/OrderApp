package ford.group.orderapp.service;

import ford.group.orderapp.dto.shippingdetail.ShippingDetailDTO;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailToSaveDTO;
import ford.group.orderapp.exception.ShippingDetailNotFoundException;

import java.util.List;

public interface ShippingDetailService {
    ShippingDetailDTO saveShippingDetail(ShippingDetailToSaveDTO shippingDetailDTO);
    ShippingDetailDTO updateShippingDetail(Long id, ShippingDetailToSaveDTO shippingDetailDTO);
    ShippingDetailDTO findShippingDetailById(Long id) throws ShippingDetailNotFoundException;
    void removeShippingDetail(Long id);
    ShippingDetailDTO findShippingDetailByOrder(Long orderId);
    List<ShippingDetailDTO> findShippingDetailsByDeliverer(String deliverer);
    List<ShippingDetailDTO> findShippingDetailsByOrderStatus(String status);

    List<ShippingDetailDTO> findAll();
}
