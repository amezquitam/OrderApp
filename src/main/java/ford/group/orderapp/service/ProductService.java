package ford.group.orderapp.service;

import ford.group.orderapp.dto.product.ProductDTO;
import ford.group.orderapp.dto.product.ProductToSaveDTO;
import ford.group.orderapp.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    ProductDTO saveProduct(ProductToSaveDTO productToSaveDTO);
    ProductDTO updateProduct(Long id, ProductToSaveDTO productToSaveDTO);
    ProductDTO findProductById(Long id) throws ProductNotFoundException;
    void removeProduct(Long id);
    public List<ProductDTO> findProductsBySearchTerm(String term);
    public List<ProductDTO> findProductsInStock();
    public List<ProductDTO> findProductsByMaxPriceAndMaxStock(Double maxPrice, Integer maxStock);

}
