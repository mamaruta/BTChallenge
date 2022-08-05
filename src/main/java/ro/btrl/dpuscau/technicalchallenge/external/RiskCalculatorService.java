package ro.btrl.dpuscau.technicalchallenge.external;

import ro.btrl.dpuscau.technicalchallenge.model.IDDocument;

/*
@Reviewer@ In a normal, full project, the client jar of the existing external service will be added as a dependency in
the pom file but for the purpose of this challenge I choose to just keep it simple and go with a mock implementation */
public interface RiskCalculatorService {
    int calculateRisk(IDDocument idDocument);
}
