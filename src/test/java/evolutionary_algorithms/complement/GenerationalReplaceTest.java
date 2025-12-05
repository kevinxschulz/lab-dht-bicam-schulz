package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import problem.definition.State;

public class GenerationalReplaceTest {

    @Test
    public void testReplaceRemovesFirstAndAddsCandidate() throws Exception {
        GenerationalReplace replace = new GenerationalReplace();

        List<State> list = new ArrayList<>();
        State s1 = new State(); s1.setNumber(1);
        State s2 = new State(); s2.setNumber(2);
        State s3 = new State(); s3.setNumber(3);
        list.add(s1); list.add(s2); list.add(s3);

        State candidate = new State(); candidate.setNumber(99);

        List<State> result = replace.replace(candidate, list);

        // size unchanged
        assertEquals(3, result.size());
        // first element should now be the original second
        assertEquals(s2, result.get(0));
        // second element should be the original third
        assertEquals(s3, result.get(1));
        // last element should be the candidate
        assertEquals(candidate, result.get(2));
    }
}
