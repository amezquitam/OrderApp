package ford.group.orderapp.exception;

public class ShippingDetailNotFoundException extends ResourceNotFoundException {
    public ShippingDetailNotFoundException() {
    }

    public ShippingDetailNotFoundException(String message) {
        super(message);
    }

    public ShippingDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShippingDetailNotFoundException(Throwable cause) {
        super(cause);
    }

    public ShippingDetailNotFoundException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace) {
        super(message, cause, enableSupression, writableStackTrace);
    }
}
