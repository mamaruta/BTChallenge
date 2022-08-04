package ro.btrl.dpuscau.technicalchallenge.external;

import ro.btrl.dpuscau.technicalchallenge.model.IDDocument;

//In a normal, full project, the client jar of the existing external service will be added as a dependency in the pom file
public interface ExistingClientsService {
    boolean isClient(IDDocument idDocument);
}
