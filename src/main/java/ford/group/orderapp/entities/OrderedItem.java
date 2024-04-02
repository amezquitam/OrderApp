package ford.group.orderapp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ordered_items")
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    private Long requestedAmount;

    private Double unitPrice;

    public OrderedItem() {
    }

    public OrderedItem(Long id, Order order, Product product, Long requestedAmount, Double unitPrice) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.requestedAmount = requestedAmount;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Long requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static OrderedItemBuilder builder() {
        return new OrderedItemBuilder();
    }

    public static final class OrderedItemBuilder {
        private Long id;
        private Order order;
        private Product product;
        private Long requestedAmount;
        private Double unitPrice;

        private OrderedItemBuilder() {
        }

        public static OrderedItemBuilder anOrderedItem() {
            return new OrderedItemBuilder();
        }

        public OrderedItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderedItemBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderedItemBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public OrderedItemBuilder requestedAmount(Long requestedAmount) {
            this.requestedAmount = requestedAmount;
            return this;
        }

        public OrderedItemBuilder unitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public OrderedItem build() {
            OrderedItem orderedItem = new OrderedItem();
            orderedItem.setId(id);
            orderedItem.setOrder(order);
            orderedItem.setProduct(product);
            orderedItem.setRequestedAmount(requestedAmount);
            orderedItem.setUnitPrice(unitPrice);
            return orderedItem;
        }
    }
}
