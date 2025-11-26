package nl.maritoldenburger.bakehub.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {

        super(message);
    }
}
