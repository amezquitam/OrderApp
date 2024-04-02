package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.product.ProductDTO;
import ford.group.orderapp.dto.product.ProductToSaveDTO;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.ProductNotFoundException;
import ford.group.orderapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable("id") Long id) {
        try {
            ProductDTO product = productService.findProductById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException err) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> search(@RequestParam(name = "searchTerm") String searchTerm) {
        List<ProductDTO> products = productService.findProductsBySearchTerm(searchTerm);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<List<ProductDTO>> inStock() {
        List<ProductDTO> products = productService.findProductsInStock();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductToSaveDTO product) {
        // validations...

        // save
        ProductDTO savedProduct = productService.saveProduct(product);

        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") Long id, @RequestBody ProductToSaveDTO updatedProduct) {
        ProductDTO newProduct = productService.updateProduct(id, updatedProduct);

        return ResponseEntity.ok(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            productService.removeProduct(id);
            return ResponseEntity.ok("Success");
        } catch (NotAbleToDeleteException err) {
            return ResponseEntity.badRequest().body("Id provided doesn't found");
        }
    }

}
