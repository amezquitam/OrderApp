package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientToSaveDTO;
import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.dto.product.ProductDTO;
import ford.group.orderapp.dto.product.ProductMapper;
import ford.group.orderapp.dto.product.ProductMapperImpl;
import ford.group.orderapp.dto.product.ProductToSaveDTO;
import ford.group.orderapp.entities.Product;
import ford.group.orderapp.exception.ClientNotFoundException;
import ford.group.orderapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;
    ProductServiceImpl productService;
    Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, new ProductMapperImpl());

        product = Product.builder()
                .id(16L)
                .name("product test")
                .price(1500.0)
                .stock(10)
                .build();
    }

    @Test
    void saveProduct() {
        given(productRepository.save(any())).willReturn(product);

        ProductToSaveDTO productToSave = new ProductToSaveDTO(
                "product test",
                1500.0,
                10
        );

        ProductDTO savedProduct = productService.saveProduct(productToSave);

        assertThat(savedProduct.id()).isNotNull();
        assertThat(savedProduct.id()).isEqualTo(16);
        assertThat(savedProduct.price()).isEqualTo(1500.0);
        assertThat(savedProduct.stock()).isEqualTo(10);
        assertThat(savedProduct.name()).isEqualTo("product test");
    }

    @Test
    void updateProduct() {
        Long productId = 16L;

        given(productRepository.save(any())).willReturn(product);
        given(productRepository.findById(any()))
                .willReturn(Optional.empty());

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        ProductToSaveDTO productToSave = new ProductToSaveDTO(
                "product test updated",
                3500.0,
                20
        );

        ProductDTO updatedProduct = productService.updateProduct(productId,productToSave);

        assertThat(updatedProduct.name()).isEqualTo("product test updated");
        assertThat(updatedProduct.price()).isEqualTo(3500);
        assertThat(updatedProduct.stock()).isEqualTo(20);
    }

    @Test
    void findProductById() {
        Long productId = 16L;

        given(productRepository.findById(any()))
                .willReturn(Optional.empty());

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        ProductDTO product = productService.findProductById(productId);

        assertThat(product).isNotNull();
        assertThat(product.id()).isEqualTo(16);
    }

    @Test
    void removeProduct() {
        Long productId = 48L;

        willDoNothing().given(productRepository).delete(any());
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.removeProduct(productId);

        verify(productRepository, times(1)).delete(any());
    }

    @Test
    void findProductsBySearchTerm() {
        String term = "product";
        given(productRepository.findProductsByNameContains(term))
                .willReturn(List.of(product));

        var products = productService.findProductsBySearchTerm(term);

        assertThat(products).isNotEmpty();

        assertThat(products.get(0).id()).isEqualTo(16);
    }

    @Test
    void findProductsInStock() {
        given(productRepository.findProductsByStockGreaterThan(0))
                .willReturn(List.of(product));

        var products = productService.findProductsInStock();

        assertThat(products).isNotEmpty();
        assertThat(products.get(0).stock()).isGreaterThan(0);
    }

    @Test
    void findProductsByMaxPriceAndMaxStock() {
    }
}