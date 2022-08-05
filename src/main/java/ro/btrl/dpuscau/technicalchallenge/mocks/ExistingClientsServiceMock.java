package ro.btrl.dpuscau.technicalchallenge.mocks;

import org.springframework.stereotype.Service;
import ro.btrl.dpuscau.technicalchallenge.external.ExistingClientsService;
import ro.btrl.dpuscau.technicalchallenge.model.IDDocument;

/**
 * This is a mock service for the Existing clients service. It has a dummy optimistic implementation :) - it considers all the
 * romanian clients to be an existing BT customer, and all people identified with a foreign document to not have an account at BT.
 */
@Service
public class ExistingClientsServiceMock implements ExistingClientsService {
    @Override
    public boolean isClient(IDDocument idDocument) {
        return idDocument.getCountry().equalsIgnoreCase("RO");
    }
}
