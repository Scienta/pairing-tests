package no.scienta.yatzy;

/**
 * @author Sindre Mehus
 */
public enum Category {

    ONES {
        @Override
        public int score(Hand hand) {
            return scoreSingles(hand, Die.ONE);
        }
    },

    TWOS {
        @Override
        public int score(Hand hand) {
            return scoreSingles(hand, Die.TWO);
        }
    },

    THREES {
        @Override
        public int score(Hand hand) {
            return scoreSingles(hand, Die.THREE);
        }
    },

    FOURS {
        @Override
        public int score(Hand hand) {
            return scoreSingles(hand, Die.FOUR);
        }
    },

    FIVES {
        @Override
        public int score(Hand hand) {
            return scoreSingles(hand, Die.FIVE);
        }
    },

    SIXES {
        @Override
        public int score(Hand hand) {
            return scoreSingles(hand, Die.SIX);
        }
    },

    ONE_PAIR {
        @Override
        public int score(Hand hand) {
            return scoreNMOfAKind(hand, 2, 0);
        }
    },

    TWO_PAIRS {
        @Override
        public int score(Hand hand) {
            return scoreNMOfAKind(hand, 2, 2);
        }
    },

    THREE_OF_A_KIND {
        @Override
        public int score(Hand hand) {
            return scoreNMOfAKind(hand, 3, 0);
        }
    },

    FOUR_OF_A_KIND {
        @Override
        public int score(Hand hand) {
            return scoreNMOfAKind(hand, 4, 0);
        }
    },

    SMALL_STRAIGHT {
        @Override
        public int score(Hand hand) {
            Frequency frequency = hand.frequency();
            return Die.ASCENDING.reject(die -> die == Die.SIX)
                                .allSatisfy(die -> frequency.count(die) == 1) ? 15 : 0;
        }
    },

    LARGE_STRAIGHT {
        @Override
        public int score(Hand hand) {
            Frequency frequency = hand.frequency();
            return Die.ASCENDING.reject(die -> die == Die.ONE)
                                .allSatisfy(die -> frequency.count(die) == 1) ? 20 : 0;
        }
    },

    HOUSE {
        @Override
        public int score(Hand hand) {
            return scoreNMOfAKind(hand, 3, 2);
        }
    },

    CHANCE {
        @Override
        public int score(Hand hand) {
            return (int) hand.getDice().sumOfInt(Die::getValue);
        }
    },

    YATZY {
        @Override
        public int score(Hand hand) {
            Frequency frequency = hand.frequency();
            return Die.ASCENDING.anySatisfy(die -> frequency.count(die) == 5) ? 50 : 0;
        }
    };

    public abstract int score(Hand hand);

    protected int scoreSingles(Hand hand, Die die) {
        return die.getValue() * hand.frequency().count(die);
    }

    protected int scoreNMOfAKind(Hand hand, int n, int m) {
        Frequency frequency = hand.frequency();
        for (Die a : Die.DESCENDING) {
            for (Die b : Die.DESCENDING) {
                if (a == b) {
                    continue;
                }
                if (frequency.count(a) >= n && frequency.count(b) >= m) {
                    return a.getValue() * n + b.getValue() * m;
                }
            }
        }
        return 0;
    }

}
