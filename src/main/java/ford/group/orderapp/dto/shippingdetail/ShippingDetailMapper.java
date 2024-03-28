package ford.group.orderapp.dto.shippingdetail;

import ford.group.orderapp.entities.ShippingDetail;
import org.mapstruct.Mapper;

@Mapper
public interface ShippingDetailMapper {
    ShippingDetailDTO shippingDetailToShippingDetailDTO(ShippingDetail shippingDetail);
    ShippingDetail shippingDetailDTOToShippingDetail(ShippingDetailDTO shippingDetailDTO);
}
