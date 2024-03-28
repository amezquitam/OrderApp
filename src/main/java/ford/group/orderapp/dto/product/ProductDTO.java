package ford.group.orderapp.dto.product;

public record ProductDTO(
        Long id,
        String name,
        Double price,
        Integer stock
) {
}
