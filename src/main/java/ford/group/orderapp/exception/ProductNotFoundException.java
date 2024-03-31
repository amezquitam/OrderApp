package ford.group.orderapp.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(){
    }
    public ProductNotFoundException(String message){
        super(message);
    }
    public ProductNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    public ProductNotFoundException(Throwable cause){
        super(cause);
    }
    public ProductNotFoundException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace) {
        super(message, cause, enableSupression, writableStackTrace);
    }
}
