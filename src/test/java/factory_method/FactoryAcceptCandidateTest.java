package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import local_search.acceptation_type.AcceptType;
import local_search.acceptation_type.AcceptableCandidate;

public class FactoryAcceptCandidateTest {

    @Test
    public void createAllAcceptTypes() throws Exception {
        FactoryAcceptCandidate factory = new FactoryAcceptCandidate();

        for (AcceptType t : AcceptType.values()) {
            AcceptableCandidate ac = factory.createAcceptCandidate(t);
            assertNotNull(ac, "Factory returned null for " + t);

            String expectedClass = "local_search.acceptation_type." + t.toString();
            assertEquals(expectedClass, ac.getClass().getName(), "Unexpected implementation for " + t);
        }
    }
}
