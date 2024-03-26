package ford.group.orderapp.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderedAt;

    private OrderStatus status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderedItem> orderedItems;

    public Order() {
    }

    public Order(Long id, Client client, LocalDateTime orderedAt, OrderStatus status, List<OrderedItem> orderedItems) {
        this.id = id;
        this.client = client;
        this.orderedAt = orderedAt;
        this.status = status;
        this.orderedItems = orderedItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public static final class OrderBuilder {
        private Long id;
        private Client client;
        private LocalDateTime orderedAt;
        private OrderStatus status;
        private List<OrderedItem> orderedItems;

        private OrderBuilder() {
        }

        public static OrderBuilder anOrder() {
            return new OrderBuilder();
        }

        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public OrderBuilder orderedAt(LocalDateTime orderedAt) {
            this.orderedAt = orderedAt;
            return this;
        }

        public OrderBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderBuilder orderedItems(List<OrderedItem> orderedItems) {
            this.orderedItems = orderedItems;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setId(id);
            order.setClient(client);
            order.setOrderedAt(orderedAt);
            order.setStatus(status);
            order.setOrderedItems(orderedItems);
            return order;
        }
    }
}
