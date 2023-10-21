package datum.config.exception;


public class ExceptionApp extends RuntimeException{
    private final int code;
    public ExceptionApp(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
