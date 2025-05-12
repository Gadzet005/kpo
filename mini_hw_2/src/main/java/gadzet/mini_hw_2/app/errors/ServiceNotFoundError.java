package gadzet.mini_hw_2.app.errors;

public class ServiceNotFoundError extends ServiceError {
    public ServiceNotFoundError(String message) {
        super(404, message);
    }
}
