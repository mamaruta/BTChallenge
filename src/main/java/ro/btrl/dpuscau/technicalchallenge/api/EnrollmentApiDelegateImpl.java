package ro.btrl.dpuscau.technicalchallenge.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.btrl.dpuscau.technicalchallenge.exception.DocumentExpiredException;
import ro.btrl.dpuscau.technicalchallenge.exception.AlreadyExistingClientException;
import ro.btrl.dpuscau.technicalchallenge.exception.HighRiskClientException;
import ro.btrl.dpuscau.technicalchallenge.model.CustomerCheckResult;
import ro.btrl.dpuscau.technicalchallenge.model.IDDocument;
import ro.btrl.dpuscau.technicalchallenge.service.FullClientCheckService;

@Service
public class EnrollmentApiDelegateImpl implements EnrollmentApiDelegate {

    private final FullClientCheckService fullClientCheckService;

    @Autowired
    public EnrollmentApiDelegateImpl(FullClientCheckService fullClientCheckService) {
        this.fullClientCheckService = fullClientCheckService;
    }


    @Override
    public ResponseEntity<CustomerCheckResult> enrollmentCheckPost(IDDocument idDocument) {
        CustomerCheckResult result = new CustomerCheckResult();
        try {
            fullClientCheckService.checkPotentialClient(idDocument);
            result.isEligible(true);

        } catch (AlreadyExistingClientException | HighRiskClientException | DocumentExpiredException e) {
            result.isEligible(false);
            result.reasonForDenial(e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

}
