package ford.group.orderapp.service;

import ford.group.orderapp.dto.payment.PaymentDTO;
import ford.group.orderapp.dto.payment.PaymentMapper;
import ford.group.orderapp.dto.payment.PaymentToSaveDTO;
import ford.group.orderapp.entities.Payment;
import ford.group.orderapp.entities.PaymentMethod;
import ford.group.orderapp.exception.ClientNotFoundException;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderedItemNotFoundExcepction;
import ford.group.orderapp.exception.PaymentNotFoundException;
import ford.group.orderapp.repository.OrderRepository;
import ford.group.orderapp.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService{
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentMapper paymentMapper, PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public PaymentDTO savePayment(PaymentToSaveDTO paymentDTO) {
        Payment payment = paymentMapper.paymentSaveDTOToPaymente(paymentDTO);
        Payment paymentSaved = paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentDTO(paymentSaved);
    }

    @Override
    public PaymentDTO updatePayment(Long id, PaymentToSaveDTO paymentDTO) {
        return paymentRepository.findById(id).map(paymentInDB -> {
            paymentInDB.setOrder(paymentMapper.paymentSaveDTOToPaymente(paymentDTO).getOrder());
            paymentInDB.setTotalPayment(paymentDTO.totalPayment());
            paymentInDB.setPayedAt(paymentDTO.payedAt());
            paymentInDB.setPaymentMethod(PaymentMethod.valueOf(paymentDTO.paymentMethod()));
            Payment paymentSaved = paymentRepository.save(paymentInDB);
            return paymentMapper.paymentToPaymentDTO(paymentSaved);
        }).orElseThrow(() -> new ClientNotFoundException("Pago no encontrado"));
    }

    @Override
    public PaymentDTO findPaymentById(Long id) throws PaymentNotFoundException {
        Payment payment = paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
        return paymentMapper.paymentToPaymentDTO(payment);
    }

    @Override
    public void removePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(NotAbleToDeleteException::new);
        paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentDTO> findPaymentsByPayedAtBetween(LocalDate from, LocalDate to) {
        List<Payment> payments = paymentRepository.findPaymentsByPayedAtBetween(from, to);
        if (payments.isEmpty())
            throw new PaymentNotFoundException("Pagos no encontrados");
        return payments.stream().map(paymentMapper::paymentToPaymentDTO).collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> findPaymentsByOrderAndPaymentMethod(Long orderId, String paymentMethod) {
        List<Payment> payments = paymentRepository.findPaymentsByOrderAndPaymentMethod(orderRepository.findById(orderId).orElseThrow(OrderedItemNotFoundExcepction::new), PaymentMethod.valueOf(paymentMethod));
        if (payments.isEmpty())
            throw new PaymentNotFoundException("Pagos no encontrados");
        return payments.stream().map(paymentMapper::paymentToPaymentDTO).collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> findByOrder(Long id) {
        return paymentRepository.findPaymentsByOrder(id).stream()
                .map(paymentMapper::paymentToPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> findAll() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::paymentToPaymentDTO)
                .collect(Collectors.toList());
    }
}
