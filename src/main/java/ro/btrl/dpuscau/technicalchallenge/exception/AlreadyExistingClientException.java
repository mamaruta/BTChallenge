package ro.btrl.dpuscau.technicalchallenge.exception;

public class AlreadyExistingClientException extends RuntimeException {
    public AlreadyExistingClientException(){
        super("Client already exists");
    }
}
