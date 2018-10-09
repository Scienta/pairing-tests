package crossword;

import java.util.Iterator;
import java.util.List;

/**
 * Empty implementation of the {@link Crossword} interface.
 * <p/>
 * You should edit this class, providing real implementations of the methods.
 */
public class CrosswordImpl implements Crossword {

    /**
     * Creates a new crossword with the given number of rows and columns.
     * <p/>
     * All squares in the crosswords are initially writable, and have no clue index
     * or character content.
     *
     * @param rows    Number of rows in the crossword.
     * @param columns Number of columns in the crossword.
     * @throws IllegalArgumentException If the number of rows or columns are less than zero.
     */
    public CrosswordImpl(int rows, int columns) throws IllegalArgumentException {
        // TODO: Implement
    }

    /**
     * Returns the number of rows in the crossword.
     *
     * @return The number of rows.
     */
    public int getRowCount() {
        // TODO: Implement
        return 0;
    }

    /**
     * Returns the number of columns in the crossword.
     *
     * @return The number of columns.
     */
    public int getColumnCount() {
        // TODO: Implement
        return 0;
    }

    /**
     * Returns the square at the given row and column.
     *
     * @param row    The zero-based row number.
     * @param column The zero-based column number.
     * @return The square at the given coordinates, or <code>null</code> if the row
     *         and/or column is not within the crossword.
     */
    public Square getSquare(int row, int column) {
        // TODO: Implement
        return null;
    }

    /**
     * Sets a square writable ("white") or not writable ("black").
     * <p/>
     * The clue indices may become invalid when invoking this method. It is the
     * caller's responsibility to re-generate clue indices after changing square
     * writability.
     *
     * @param row      The zero-based row number of the square.
     * @param column   The zero-based column number of the square.
     * @param writable Whether the square should be writable or not.
     * @throws IllegalArgumentException If there is no square at the given coordinates.
     */
    public void setSquareWritable(int row, int column, boolean writable) throws IllegalArgumentException {
        // TODO: Implement
    }

    /**
     * Sets the character content of a square.
     *
     * @param row       The zero-based row number of the square.
     * @param column    The zero-based column number of the square.
     * @param character The character to put in the square, or <code>null</code>
     *                  to indicate no content.
     * @throws IllegalArgumentException If there is no square at the given
     *                                  coordinates, or if the square is not writable.
     */
    public void setSquareCharacter(int row, int column, Character character) throws IllegalArgumentException {
        // TODO: Implement
    }

    /**
     * Returns the squares in a given row.
     *
     * @param row The zero-based row number.
     * @return A list of squares in the row from left to right. The returned list
     *         can be freely modified without modifying the underlying data.
     * @throws IllegalArgumentException If there is no row with the given number.
     */
    public List<Square> getRow(int row) throws IllegalArgumentException {
        // TODO: Implement
        return null;
    }

    /**
     * Returns the squares in a given column.
     *
     * @param column The zero-based column number.
     * @return A list of squares in the column from top to bottom. The returned list
     *         can be freely modified without modifying the underlying data.
     * @throws IllegalArgumentException If there is no column with the given number.
     */
    public List<Square> getColumn(int column) throws IllegalArgumentException {
        // TODO: Implement
        return null;
    }

    /**
     * Returns an iterator which iterates through all the squares in the crossword,
     * row by row and from left to right.
     * <p/>
     * The iterator is not guaranteed to handle concurrent modifications. Also, the
     * iterator does not support the optional {@link Iterator#remove()} method.
     * Calling it will throw an {@link UnsupportedOperationException}.
     *
     * @return A square iterator.
     */
    public Iterator<Square> iterator() {
        // TODO: Implement
        return null;
    }

    /**
     * Generates clue indices for the crossword, using the rules described in the
     * exercise documentation.
     *
     * @return The highest clue index generated, or zero if no clues were generated.
     */
    public int generateClueIndices() {
        // TODO: Implement
        return 0;
    }

    /**
     * Returns the square with the given clue index.
     *
     * @param clueIndex The one-based clue index.
     * @return The square with the given clue index, or <code>null</code> if no square
     *         has the given index, or if square indices have not been generated yet.
     */
    public Square getSquare(int clueIndex) {
        // TODO: Implement
        return null;
    }

    /**
     * Returns the answer word for the given clue index and direction.
     *
     * @param clueIndex The one-based clue index.
     * @param direction The direction; down or across.
     * @return The answer word as a list of characters. Some of the characters may
     *         be <code>null</code>. The returned list can be freely modified
     *         without modifying the underlying data.
     * @throws IllegalArgumentException If the given clue index and direction does
     *                                  not identify a valid clue/answer.
     */
    public List<Character> getAnswer(int clueIndex, Direction direction) throws IllegalArgumentException {
        // TODO: Implement
        return null;
    }

    /**
     * Sets the answer word for the given clue index and direction. Will overwrite any existing
     * characters in the squares.
     *
     * @param clueIndex The one-based clue index.
     * @param direction The direction; down or across.
     * @param answer    The answer word to set.
     * @throws IllegalArgumentException If the given clue index and direction does
     *                                  not identify a valid clue/answer, or if the given answer
     *                                  word has wrong length.
     */
    public void setAnswer(int clueIndex, Direction direction, String answer) throws IllegalArgumentException {
        // TODO: Implement
    }

    /**
     * Returns whether the given answer word is not in conflict with the existing characters
     * in the crossword.
     *
     * @param clueIndex The one-based clue index.
     * @param direction The direction; down or across.
     * @param answer    The answer word to evaluate for validity.
     * @return Whether the given answer word has valid length and does not conflict with existing
     *         characters in the crossword.
     * @throws IllegalArgumentException If the given clue index and direction does
     *                                  not identify a valid clue/answer.
     */
    public boolean isAnswerValid(int clueIndex, Direction direction, String answer) throws IllegalArgumentException {
        // TODO: Implement
        return false;
    }
}
