package no.scienta.yatzy;

import java.util.Arrays;

import org.junit.Test;

import static no.scienta.yatzy.Die.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Sindre Mehus
 */
public class YatzyTest {

    @Test
    public void score_ones() {
        doTest(Category.ONES, 0, TWO, SIX, FIVE, TWO, SIX);
        doTest(Category.ONES, 2, ONE, SIX, FIVE, ONE, SIX);
    }

    @Test
    public void score_twos() {
        doTest(Category.TWOS, 0, ONE, SIX, FIVE, ONE, SIX);
        doTest(Category.TWOS, 4, TWO, SIX, FIVE, TWO, SIX);
    }

    @Test
    public void score_two_pairs() {
        doTest(Category.TWO_PAIRS, 0, TWO, SIX, FIVE, THREE, SIX);
        doTest(Category.TWO_PAIRS, 0, FIVE, FIVE, FIVE, FIVE, SIX);
        doTest(Category.TWO_PAIRS, 16, TWO, SIX, TWO, THREE, SIX);
    }

    @Test
    public void score_one_pair() {
        doTest(Category.ONE_PAIR, 10, FIVE, FOUR, FIVE, FOUR, SIX);
        doTest(Category.ONE_PAIR, 10, FIVE, FIVE, FIVE, FOUR, SIX);
        doTest(Category.ONE_PAIR, 0, ONE, TWO, THREE, FOUR, FIVE);
    }

    @Test
    public void score_three_of_a_kind() {
        doTest(Category.THREE_OF_A_KIND, 0, TWO, SIX, FIVE, THREE, SIX);
        doTest(Category.THREE_OF_A_KIND, 15, FIVE, FIVE, FIVE, SIX, SIX);
        doTest(Category.THREE_OF_A_KIND, 15, FIVE, FIVE, FIVE, FIVE, SIX);
    }

    @Test
    public void score_house() {
        doTest(Category.HOUSE, 0, TWO, SIX, FIVE, TWO, SIX);
        doTest(Category.HOUSE, 28, SIX, SIX, SIX, FIVE, FIVE);
        doTest(Category.HOUSE, 27, SIX, SIX, FIVE, FIVE, FIVE);
    }

    @Test
    public void score_small_straight() {
        doTest(Category.SMALL_STRAIGHT, 0, ONE, TWO, THREE, FOUR, SIX);
        doTest(Category.SMALL_STRAIGHT, 0, TWO, THREE, FOUR, FIVE, SIX);
        doTest(Category.SMALL_STRAIGHT, 15, ONE, TWO, THREE, FOUR, FIVE);
        doTest(Category.SMALL_STRAIGHT, 15, TWO, ONE, THREE, FIVE, FOUR);
    }

    @Test
    public void score_yatzy() {
        doTest(Category.YATZY, 0, TWO, TWO, TWO, TWO, SIX);
        doTest(Category.YATZY, 50, TWO, TWO, TWO, TWO, TWO);
    }

    @Test
    public void score_chance() {
        doTest(Category.CHANCE, 21, TWO, SIX, FIVE, TWO, SIX);
    }

    private void doTest(Category category, int expectedScore, Die... dice) {
        Yatzy yatzy = new Yatzy();
        Hand hand = new Hand(Arrays.asList(dice));
        assertEquals(expectedScore, yatzy.score(hand, category));
    }
}