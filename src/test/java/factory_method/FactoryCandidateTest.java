package factory_method;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import local_search.candidate_type.CandidateType;
import local_search.candidate_type.SearchCandidate;

public class FactoryCandidateTest {

    @Test
    public void createSmallerCandidate() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        FactoryCandidate factory = new FactoryCandidate();
        SearchCandidate sc = factory.createSearchCandidate(CandidateType.SmallerCandidate);
        assertNotNull(sc, "Factory should not return null");
        assertTrue(sc instanceof SearchCandidate, "Returned object should be a SearchCandidate");
        assertEquals("SmallerCandidate", sc.getClass().getSimpleName(), "Concrete class name should match enum name");
    }
}
