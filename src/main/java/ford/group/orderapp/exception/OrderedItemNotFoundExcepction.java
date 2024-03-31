package ford.group.orderapp.exception;

public class OrderedItemNotFoundExcepction extends RuntimeException {
    public OrderedItemNotFoundExcepction(){
    }
    public OrderedItemNotFoundExcepction(String message){
        super(message);
    }
    public OrderedItemNotFoundExcepction(String message, Throwable cause){
        super(message, cause);
    }
    public OrderedItemNotFoundExcepction(Throwable cause){
        super(cause);
    }
    public OrderedItemNotFoundExcepction(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace){
        super(message, cause,enableSupression,writableStackTrace);
    }
}
