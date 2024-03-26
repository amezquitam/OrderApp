package ford.group.orderapp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "shipping_details")
public class ShippingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    private String address;

    private String deliverer;

    private String trackingNumber;

    public ShippingDetail() {
    }

    public ShippingDetail(Long id, Order order, String address, String deliverer, String trackingNumber) {
        this.id = id;
        this.order = order;
        this.address = address;
        this.deliverer = deliverer;
        this.trackingNumber = trackingNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(String deliverer) {
        this.deliverer = deliverer;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public static ShippingDetailBuilder builder() {
        return new ShippingDetailBuilder();
    }

    public static final class ShippingDetailBuilder {
        private Long id;
        private Order order;
        private String address;
        private String deliverer;
        private String trackingNumber;

        private ShippingDetailBuilder() {
        }

        public static ShippingDetailBuilder aShippingDetail() {
            return new ShippingDetailBuilder();
        }

        public ShippingDetailBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ShippingDetailBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public ShippingDetailBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ShippingDetailBuilder deliverer(String deliverer) {
            this.deliverer = deliverer;
            return this;
        }

        public ShippingDetailBuilder trackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public ShippingDetail build() {
            ShippingDetail shippingDetail = new ShippingDetail();
            shippingDetail.setId(id);
            shippingDetail.setOrder(order);
            shippingDetail.setAddress(address);
            shippingDetail.setDeliverer(deliverer);
            shippingDetail.setTrackingNumber(trackingNumber);
            return shippingDetail;
        }
    }
}
