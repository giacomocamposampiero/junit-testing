package exceptions;

/**
 *
 * @author Giacomo Camposampiero
 */
public class IllegalStateException extends RuntimeException{

    public IllegalStateException(String message) {
        super(message);
    }

    public IllegalStateException() {
        super("exceptions.IllegalStateException");
    }
    
}
