package ro.btrl.dpuscau.technicalchallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.btrl.dpuscau.technicalchallenge.exception.DocumentExpiredException;
import ro.btrl.dpuscau.technicalchallenge.exception.AlreadyExistingClientException;
import ro.btrl.dpuscau.technicalchallenge.exception.HighRiskClientException;
import ro.btrl.dpuscau.technicalchallenge.external.ExistingClientsService;
import ro.btrl.dpuscau.technicalchallenge.external.RiskCalculatorService;
import ro.btrl.dpuscau.technicalchallenge.model.IDDocument;

import java.time.LocalDate;
@Service
public class FullClientCheckService {

    @Value("${maximum.accepted.risk}")
    private Integer MAXIMUM_ACCEPTED_RISK;
    private final RiskCalculatorService riskCalculatorService;
    private final ExistingClientsService existingClientsService;

    @Autowired
    public FullClientCheckService(RiskCalculatorService riskCalculatorService, ExistingClientsService existingClientsService) {
        this.riskCalculatorService = riskCalculatorService;
        this.existingClientsService = existingClientsService;
    }

    public void checkPotentialClient(IDDocument idDocument) {

        if(!isDocumentValid(idDocument.getExpiryDate())) throw new DocumentExpiredException();
        if(isHighRiskClient(idDocument)) throw new HighRiskClientException();
        if(isExistingClient(idDocument)) throw new AlreadyExistingClientException();

    }

    private boolean isDocumentValid(LocalDate expiryDate) {
        return expiryDate.isAfter(LocalDate.now());
    }

    private boolean isExistingClient(IDDocument idDocument) {
        return existingClientsService.isClient(idDocument);
    }

    private boolean isHighRiskClient(IDDocument idDocument) {
        return riskCalculatorService.calculateRisk(idDocument) > MAXIMUM_ACCEPTED_RISK;
    }

}
