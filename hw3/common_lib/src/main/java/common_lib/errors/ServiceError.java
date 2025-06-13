package common_lib.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ServiceError extends Exception {
    @Getter
    private final String code;
    @Getter
    private final String message;

    public ServiceError(String code) {
        this.code = code;
        this.message = "";
    }

    @Override
    public String toString() {
        if (message.isEmpty()) {
            return code;
        }
        return code + ": " + message;
    }
}
