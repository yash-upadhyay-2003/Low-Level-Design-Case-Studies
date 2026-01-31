package LLDCaseStudies.tictactoe.exception;

public class InvalidMoveException extends RuntimeException {
    String message;
    public InvalidMoveException(String message) {
       super(message);
    }

}
