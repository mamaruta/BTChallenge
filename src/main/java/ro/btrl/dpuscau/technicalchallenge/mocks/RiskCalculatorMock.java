package ro.btrl.dpuscau.technicalchallenge.mocks;

import org.springframework.stereotype.Service;
import ro.btrl.dpuscau.technicalchallenge.external.RiskCalculatorService;
import ro.btrl.dpuscau.technicalchallenge.model.IDDocument;

/**
 * This is a mocked risk calculator that calculates the risk based on the length of the last name.
 * If the length of the last name is divisible by 3- it considers it a high risk client.
 * If the length of the last name has a reminder of 1 at division by 3 - it considers it a moderate risk client.
 * If the length of the last name has a reminder of 2 at division by 3- it considers it a low risk client.
 * THIS IS A DUMMY IMPLEMENTATION THAT SHOULD BE REPLACED WITH THE REAL SERVICE WHEN REAL SERVICE IS AVAILABLE
 */
@Service
public class RiskCalculatorMock implements RiskCalculatorService {

    @Override
    public int calculateRisk(IDDocument idDocument) {
        switch (idDocument.getLastName().length() % 3) {
            case 0:
                return 100;
            case 1:
                return 50;
            case 2:
                return 10;
            default:
                return 200;
        }
    }
}
