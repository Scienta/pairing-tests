package no.scienta.yatzy;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;

/**
 * @author Sindre Mehus
 */
public enum Die {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    public static final ImmutableList<Die> ASCENDING = Lists.immutable.of(values());
    public static final ImmutableList<Die> DESCENDING = ASCENDING.toReversed();

    private final int value;

    Die(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
