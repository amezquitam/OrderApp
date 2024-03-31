package ford.group.orderapp.dto.shippingdetail;

import ford.group.orderapp.entities.ShippingDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingDetailMapper {
    ShippingDetailDTO shippingDetailToShippingDetailDTO(ShippingDetail shippingDetail);
    ShippingDetail shippingDetailDTOToShippingDetail(ShippingDetailDTO shippingDetailDTO);
    ShippingDetail shippingDetailSaveDTOToShippingDetail(ShippingDetailToSaveDTO shippingDetailToSaveDTO);
}
