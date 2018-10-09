package crossword;

/**
 * A factory for creating crosswords.
 * <p/>
 * NOTE: YOU SHOULD UNDER NO CIRCUMSTANCES MODIFY THIS CLASS.
 *
 * @see Crossword
 */
public final class CrosswordFactory {

    /**
     * Creates a new crossword with the given number of rows and columns.
     * <p/>
     * All squares in the crosswords are initially writable, and have no clue index
     * or character content.
     *
     * @param rows    Number of rows in the crossword.
     * @param columns Number of columns in the crossword.
     * @return The newly created crossword.
     * @throws IllegalArgumentException If the number of rows or columns are less than zero.
     */
    public Crossword createCrossword(int rows, int columns) throws IllegalArgumentException {
        return new CrosswordImpl(rows, columns);
    }
}
