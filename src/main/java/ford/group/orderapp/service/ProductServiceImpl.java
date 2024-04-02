package ford.group.orderapp.service;

import ford.group.orderapp.dto.product.ProductDTO;
import ford.group.orderapp.dto.product.ProductMapper;
import ford.group.orderapp.dto.product.ProductToSaveDTO;
import ford.group.orderapp.entities.Product;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.ProductNotFoundException;
import ford.group.orderapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO saveProduct(ProductToSaveDTO productDTO) {
        Product product = productMapper.productSaveDTOToProduct(productDTO);
        Product productSaved = productRepository.save(product);
        return productMapper.productToProductDTO(productSaved);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductToSaveDTO productDTO) {
        return productRepository.findById(id).map(productInDB -> {
            productInDB.setName(productDTO.name());
            productInDB.setPrice(productDTO.price());
            productInDB.setStock(productDTO.stock());
            Product productSaved = productRepository.save(productInDB);
            return productMapper.productToProductDTO(productSaved);
        }).orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductDTO findProductById(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return productMapper.productToProductDTO(product);
    }

    @Override
    public void removeProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NotAbleToDeleteException::new);
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> findProductsBySearchTerm(String term) {
        List<Product> products = productRepository.findProductsByNameContains(term);
        return products.stream().map(productMapper::productToProductDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findProductsInStock() {
        List<Product> products = productRepository.findProductsByStockGreaterThan(0);
        return products.stream().map(productMapper::productToProductDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findProductsByMaxPriceAndMaxStock(Double maxPrice, Integer maxStock) {
        List<Product> products = productRepository.findProductByPriceLessThanAndStockLessThan(maxPrice,maxStock);
        return products.stream().map(productMapper::productToProductDTO).collect(Collectors.toList());
    }
}
