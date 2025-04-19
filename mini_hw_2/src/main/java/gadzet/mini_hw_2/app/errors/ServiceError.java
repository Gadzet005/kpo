package gadzet.mini_hw_2.app.errors;

public class ServiceError extends Exception {
    public final int code;

    public ServiceError(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceError(String message) {
        super(message);
        this.code = 400;
    }
}
