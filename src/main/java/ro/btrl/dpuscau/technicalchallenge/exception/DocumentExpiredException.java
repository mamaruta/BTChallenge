package ro.btrl.dpuscau.technicalchallenge.exception;

public class DocumentExpiredException extends RuntimeException {
    public DocumentExpiredException() {
        super("Document is expired");
    }
}
