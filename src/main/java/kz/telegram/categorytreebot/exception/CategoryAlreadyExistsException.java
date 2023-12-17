package kz.telegram.categorytreebot.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException() {
        super();
    }

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
