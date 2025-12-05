package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import problem.definition.State;

public class UnivariateTest {

    private Probability find(List<Probability> list, Object key, Object value) {
        for (Probability p : list) {
            if (p.getKey().equals(key) && p.getValue().equals(value)) {
                return p;
            }
        }
        return null;
    }

    @Test
    public void testDistributionSingleVariable() {
        // fathers: [1], [1], [2]
        ArrayList<Object> c1 = new ArrayList<>();
        c1.add(1);
        ArrayList<Object> c2 = new ArrayList<>();
        c2.add(1);
        ArrayList<Object> c3 = new ArrayList<>();
        c3.add(2);

        State s1 = new State(c1);
        State s2 = new State(c2);
        State s3 = new State(c3);

        List<State> fathers = new ArrayList<>();
        fathers.add(s1);
        fathers.add(s2);
        fathers.add(s3);

        Univariate u = new Univariate();
        List<Probability> probs = u.distribution(fathers);

        // Expect two probability entries for variable index 0: value 1 (2/3) and 2 (1/3)
        assertEquals(2, probs.size(), "Should have two distinct values for single variable");

        Probability p1 = find(probs, 0, 1);
        Probability p2 = find(probs, 0, 2);

        assertNotNull(p1, "Probability for value 1 must be present");
        assertNotNull(p2, "Probability for value 2 must be present");

        assertEquals(2.0f/3.0f, p1.getProbability(), 1e-6f);
        assertEquals(1.0f/3.0f, p2.getProbability(), 1e-6f);
    }

    @Test
    public void testDistributionMultipleVariables() {
        // fathers: [1,2], [1,3], [2,2]
        ArrayList<Object> c1 = new ArrayList<>();
        c1.add(1); c1.add(2);
        ArrayList<Object> c2 = new ArrayList<>();
        c2.add(1); c2.add(3);
        ArrayList<Object> c3 = new ArrayList<>();
        c3.add(2); c3.add(2);

        State s1 = new State(c1);
        State s2 = new State(c2);
        State s3 = new State(c3);

        List<State> fathers = new ArrayList<>();
        fathers.add(s1); fathers.add(s2); fathers.add(s3);

        Univariate u = new Univariate();
        List<Probability> probs = u.distribution(fathers);

        // Expect four entries: index 0 -> values 1 (2/3),2 (1/3); index 1 -> values 2 (2/3),3 (1/3)
        assertEquals(4, probs.size(), "Should have four probability entries (two variables, two distinct values each)");

        Probability p0v1 = find(probs, 0, 1);
        Probability p0v2 = find(probs, 0, 2);
        Probability p1v2 = find(probs, 1, 2);
        Probability p1v3 = find(probs, 1, 3);

        assertNotNull(p0v1);
        assertNotNull(p0v2);
        assertNotNull(p1v2);
        assertNotNull(p1v3);

        assertEquals(2.0f/3.0f, p0v1.getProbability(), 1e-6f);
        assertEquals(1.0f/3.0f, p0v2.getProbability(), 1e-6f);
        assertEquals(2.0f/3.0f, p1v2.getProbability(), 1e-6f);
        assertEquals(1.0f/3.0f, p1v3.getProbability(), 1e-6f);
    }

    @Test
    public void testDistributionSkipsMinusOneValues() {
        // fathers: [-1], [1] -> algorithm should ignore -1 when creating entries
        ArrayList<Object> c1 = new ArrayList<>();
        c1.add(-1);
        ArrayList<Object> c2 = new ArrayList<>();
        c2.add(1);

        State s1 = new State(c1);
        State s2 = new State(c2);

        List<State> fathers = new ArrayList<>();
        fathers.add(s1); fathers.add(s2);

        Univariate u = new Univariate();
        List<Probability> probs = u.distribution(fathers);

        // Only one Probability (for value 1) should be created; denominator is values.length (2)
        assertEquals(1, probs.size());
        Probability p = find(probs, 0, 1);
        assertNotNull(p);
        assertEquals(1.0f/2.0f, p.getProbability(), 1e-6f);
    }
}
