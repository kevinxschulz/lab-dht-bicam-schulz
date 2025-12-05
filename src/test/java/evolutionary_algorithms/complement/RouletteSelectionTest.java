package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import problem.definition.State;

public class RouletteSelectionTest {

    @Test
    void selectsOnlyNonZeroWeight() {
        List<State> list = new ArrayList<>();

        State s1 = new State();
        ArrayList<Double> ev1 = new ArrayList<>();
        ev1.add(1.0);
        s1.setEvaluation(ev1);

        State s2 = new State();
        ArrayList<Double> ev2 = new ArrayList<>();
        ev2.add(0.0);
        s2.setEvaluation(ev2);

        State s3 = new State();
        ArrayList<Double> ev3 = new ArrayList<>();
        ev3.add(0.0);
        s3.setEvaluation(ev3);

        list.add(s1);
        list.add(s2);
        list.add(s3);

        RouletteSelection rs = new RouletteSelection();
        List<State> fathers = rs.selection(list, 0);

        assertEquals(list.size(), fathers.size(), "Selection should return same number of fathers as input size");
        for (State f : fathers) {
            assertSame(s1, f, "When only one state has positive weight, all selected fathers must be that state (same instance)");
        }
    }

    @Test
    void returnsOnlyInputStatesAndCorrectSize() {
        List<State> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            State s = new State();
            ArrayList<Double> ev = new ArrayList<>();
            ev.add(1.0);
            s.setEvaluation(ev);
            list.add(s);
        }

        RouletteSelection rs = new RouletteSelection();
        List<State> fathers = rs.selection(list, 0);

        assertEquals(list.size(), fathers.size(), "Returned list should have same size as input list");
        for (State f : fathers) {
            assertTrue(list.contains(f), "Each selected father should be one of the original states (no new objects)");
        }
    }
}
