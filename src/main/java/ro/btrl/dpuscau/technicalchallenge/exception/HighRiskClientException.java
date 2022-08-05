package ro.btrl.dpuscau.technicalchallenge.exception;

public class HighRiskClientException extends RuntimeException {
    public HighRiskClientException() {
        super("High risk client");
    }
}
