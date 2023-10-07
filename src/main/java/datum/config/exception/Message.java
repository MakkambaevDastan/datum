package datum.config.exception;

public enum Message {
    USER_NOT_FOUND("Пользователь не найден");
    String message;
    Message(String message){
        this.message = message;
    }
}
