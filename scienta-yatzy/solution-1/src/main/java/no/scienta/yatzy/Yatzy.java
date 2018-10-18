package no.scienta.yatzy;

/**
 * @author Sindre Mehus
 */
public class Yatzy {

    public int score(Hand hand, Category category) {
        return category.score(hand);
    }
}
