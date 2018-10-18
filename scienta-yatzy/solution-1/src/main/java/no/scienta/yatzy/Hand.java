package no.scienta.yatzy;

import java.util.Iterator;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;

/**
 * @author Sindre Mehus
 */
public class Hand  {

    private final ImmutableList<Die> dice;

    public Hand(Iterable<Die> dice) {
        this.dice = Lists.immutable.ofAll(dice);
    }

    public ImmutableList<Die> getDice() {
        return dice;
    }

    public Frequency frequency() {
        return new Frequency(this);
    }
}
