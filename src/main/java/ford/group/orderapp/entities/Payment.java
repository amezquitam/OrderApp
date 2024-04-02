package ford.group.orderapp.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    private Double totalPayment;

    @Temporal(TemporalType.DATE)
    private LocalDate payedAt;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    public Payment() {
    }

    public Payment(Long id, Order order, Double totalPayment, LocalDate payedAt, PaymentMethod paymentMethod) {
        this.id = id;
        this.order = order;
        this.totalPayment = totalPayment;
        this.payedAt = payedAt;
        this.paymentMethod = paymentMethod;
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

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public LocalDate getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(LocalDate payedAt) {
        this.payedAt = payedAt;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public static final class PaymentBuilder {
        private Long id;
        private Order order;
        private Double totalPayment;
        private LocalDate payedAt;
        private PaymentMethod paymentMethod;

        private PaymentBuilder() {
        }

        public static PaymentBuilder aPayment() {
            return new PaymentBuilder();
        }

        public PaymentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public PaymentBuilder totalPayment(Double totalPayment) {
            this.totalPayment = totalPayment;
            return this;
        }

        public PaymentBuilder payedAt(LocalDate payedAt) {
            this.payedAt = payedAt;
            return this;
        }

        public PaymentBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Payment build() {
            Payment payment = new Payment();
            payment.setId(id);
            payment.setOrder(order);
            payment.setTotalPayment(totalPayment);
            payment.setPayedAt(payedAt);
            payment.setPaymentMethod(paymentMethod);
            return payment;
        }
    }
}
