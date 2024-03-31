package ford.group.orderapp.exception;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(){
    }
    public PaymentNotFoundException(String message){
        super(message);
    }
    public PaymentNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    public PaymentNotFoundException(Throwable cause){
        super(cause);
    }
    public PaymentNotFoundException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace) {
        super(message, cause, enableSupression, writableStackTrace);
    }
}
