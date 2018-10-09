package crossword;

/**
 * A square within a crossword.
 * <p/>
 * NOTE: YOU SHOULD UNDER NO CIRCUMSTANCES MODIFY THIS INTERFACE.
 *
 * @see Crossword
 */
public interface Square {

    /**
     * Returns whether this square is writable ("white") or not ("black").
     *
     * @return Whether this square is writable.
     */
    boolean isWritable();

    /**
     * Returns the character in this square.
     *
     * @return The character in this square, or <code>null</code> if the square does
     *         not contain a character.
     */
    Character getCharacter();

    /**
     * Returns the clue index for this square.
     *
     * @return The clue index for this square, or <code>null</code> if the square does
     *         not contain a clue.
     */
    Integer getClueIndex();
}
