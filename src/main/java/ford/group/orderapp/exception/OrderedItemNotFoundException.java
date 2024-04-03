package ford.group.orderapp.exception;

public class OrderedItemNotFoundException extends RuntimeException {
    public OrderedItemNotFoundException(){
    }
    public OrderedItemNotFoundException(String message){
        super(message);
    }
    public OrderedItemNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    public OrderedItemNotFoundException(Throwable cause){
        super(cause);
    }
    public OrderedItemNotFoundException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace){
        super(message, cause,enableSupression,writableStackTrace);
    }
}
