package datum.exception;

public class InternalServerError extends RuntimeException{
    public InternalServerError(String msg){
        super(msg);
    }
}
