package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.product.ProductDTO;
import ford.group.orderapp.dto.product.ProductToSaveDTO;
import ford.group.orderapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGet() throws Exception {
        // Arrange
        Long productId = 1L;
        ProductDTO expectedProduct = new ProductDTO(1L, "Keyboard", 25.0, 15);
        when(productService.findProductById(productId)).thenReturn(expectedProduct);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedProduct.id()));

        verify(productService, times(1)).findProductById(productId);
    }

    @Test
    public void testSearch() throws Exception {
        // Arrange
        String searchTerm = "boar";
        List<ProductDTO> expectedProducts = List.of(
                new ProductDTO(2L, "Keyboard", 25.0, 15)
        );
        when(productService.findProductsBySearchTerm(searchTerm)).thenReturn(expectedProducts);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/search")
                        .param("searchTerm", searchTerm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(productService, times(1)).findProductsBySearchTerm(searchTerm);
    }

    @Test
    public void testInStock() throws Exception {
        // Arrange
        List<ProductDTO> expectedProducts = List.of(
                new ProductDTO(1L, "Keyboard", 25.0, 15)
        );

        when(productService.findProductsInStock()).thenReturn(expectedProducts);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/in-stock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(productService, times(1)).findProductsInStock();
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        ProductToSaveDTO productToSave = new ProductToSaveDTO("Keyboard", 25.0, 15);
        ProductDTO expectedSavedProduct = new ProductDTO(1L, "Keyboard", 25.0, 15);
        when(productService.saveProduct(productToSave)).thenReturn(expectedSavedProduct);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Keyboard\", \"price\": 25, \"stock\": 15}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedSavedProduct.id()));

        verify(productService, times(1)).saveProduct(productToSave);
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        Long productId = 1L;
        ProductToSaveDTO updatedProduct = new ProductToSaveDTO("Keyboard", 25.0, 15);
        ProductDTO expectedUpdatedProduct = new ProductDTO(1L, "Keyboard", 25.0, 15);

        when(productService.updateProduct(productId, updatedProduct)).thenReturn(expectedUpdatedProduct);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Keyboard\", \"price\": 25, \"stock\": 15}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedUpdatedProduct.id()));

        verify(productService, times(1)).updateProduct(productId, updatedProduct);
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        Long productId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        verify(productService, times(1)).removeProduct(productId);
    }
}
