package ro.btrl.dpuscau.technicalchallenge.service;

import org.junit.jupiter.api.Test;
import org.mockito.CheckReturnValue;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.btrl.dpuscau.technicalchallenge.exception.AlreadyExistingClientException;
import ro.btrl.dpuscau.technicalchallenge.exception.DocumentExpiredException;
import ro.btrl.dpuscau.technicalchallenge.exception.HighRiskClientException;
import ro.btrl.dpuscau.technicalchallenge.external.ExistingClientsService;
import ro.btrl.dpuscau.technicalchallenge.external.RiskCalculatorService;
import ro.btrl.dpuscau.technicalchallenge.model.IDDocument;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class FullClientCheckServiceTest {
    @MockBean
    private RiskCalculatorService riskCalculatorService;
    @MockBean
    private ExistingClientsService existingClientsService;

    @Autowired
    private FullClientCheckService fullClientCheckService;

    @Test
    void checkPotentialClientHappyFlow() {
        IDDocument idDocument = new IDDocument();
        idDocument.firstName("Ana").lastName("Pop").country("FR").nationalIdentificationNumber("a_france_id").expiryDate(LocalDate.of(2030,01,31));
        when(riskCalculatorService.calculateRisk(any(IDDocument.class))).thenReturn(1);
        when(existingClientsService.isClient(any(IDDocument.class))).thenReturn(false);
        assertDoesNotThrow(() -> fullClientCheckService.checkPotentialClient(idDocument));

    }

    @Test
    void checkPotentialClientWithExpiredId_throwsException() {
        IDDocument idDocument = new IDDocument();
        idDocument.firstName("Ana").lastName("Pop").country("FR").nationalIdentificationNumber("a_france_id").expiryDate(LocalDate.of(2010,01,31));
        assertThrows(DocumentExpiredException.class, () -> fullClientCheckService.checkPotentialClient(idDocument));

    }

    @Test
    void checkPotentialClientWithHighRisk_throwsException() {
        IDDocument idDocument = new IDDocument();
        idDocument.firstName("Ana").lastName("Pop").country("FR").nationalIdentificationNumber("a_france_id").expiryDate(LocalDate.of(2030,01,31));
        when(riskCalculatorService.calculateRisk(any(IDDocument.class))).thenReturn(100);

        assertThrows(HighRiskClientException.class, () -> fullClientCheckService.checkPotentialClient(idDocument));
    }

    @Test
    void checkPotentialClientAlreadyExists_throwsException() {
        IDDocument idDocument = new IDDocument();
        idDocument.firstName("Ana").lastName("Pop").country("FR").nationalIdentificationNumber("a_france_id").expiryDate(LocalDate.of(2030,01,31));
        when(riskCalculatorService.calculateRisk(any(IDDocument.class))).thenReturn(99);
        when(existingClientsService.isClient(any(IDDocument.class))).thenReturn(true);
        assertThrows(AlreadyExistingClientException.class, () -> fullClientCheckService.checkPotentialClient(idDocument));

    }
}