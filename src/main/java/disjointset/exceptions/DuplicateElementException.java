package disjointset.exceptions;


public class DuplicateElementException extends Exception{
    public DuplicateElementException() {
        super();
    }

    public DuplicateElementException(String message) {
        super(message);
    }
}
