package datum.config.exception;


public class ExceptionApp extends RuntimeException{
    private final int code;
    public ExceptionApp(int c, String msg){
        super(msg);
        code = c;
    }

    public int getCode() {
        return this.code;
    }
}
