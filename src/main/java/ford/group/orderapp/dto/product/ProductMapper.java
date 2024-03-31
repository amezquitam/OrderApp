package ford.group.orderapp.dto.product;

import ford.group.orderapp.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO productToProductDTO(Product product);
    Product productDTOToProduct(ProductDTO productDTO);
    Product productSaveDTOToProduct(ProductToSaveDTO productToSaveDTO);
}
