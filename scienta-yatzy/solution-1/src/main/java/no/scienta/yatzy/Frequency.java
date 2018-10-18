package no.scienta.yatzy;

import org.eclipse.collections.api.map.ImmutableMap;

/**
 * @author Sindre Mehus
 */
public class Frequency {

    private final ImmutableMap<Die, Integer> countByValue;

    public Frequency(Hand hand) {
        countByValue = hand.getDice().aggregateBy(die -> die, () -> 0, (count, die) -> count + 1);
    }

    public int count(Die die) {
        return countByValue.getIfAbsentValue(die, 0);
    }
}
