package crossword;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

/**
 * Sample solution.
 *
 * @author Sindre Mehus
 */
public class SampleCrosswordImpl implements Crossword {

    private final SampleCrosswordImpl.SquareImpl[][] squares;
    private final List<Clue> clues = new ArrayList<Clue>();
    private final int rowCount;
    private final int columnCount;

    public SampleCrosswordImpl(int rows, int columns) {
        if (rows < 0) {
            throw new IllegalArgumentException("Illegal number of rows.");
        }
        if (columns < 0) {
            throw new IllegalArgumentException("Illegal number of columns.");
        }

        rowCount = rows;
        columnCount = columns;

        squares = new SampleCrosswordImpl.SquareImpl[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                squares[i][j] = new SampleCrosswordImpl.SquareImpl(i, j);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * {@inheritDoc}
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * {@inheritDoc}
     */
    public Square getSquare(int row, int column) {
        return getSquareImpl(row, column);
    }

    private SampleCrosswordImpl.SquareImpl getSquareImpl(int row, int column) {
        if (row < 0 || row >= rowCount) {
            return null;
        }
        if (column < 0 || column >= columnCount) {
            return null;
        }
        return squares[row][column];
    }

    /**
     * {@inheritDoc}
     */
    public void setSquareWritable(int row, int column, boolean writable) throws IllegalArgumentException {
        SampleCrosswordImpl.SquareImpl square = getSquareImpl(row, column);
        if (square == null) {
            throw new IllegalArgumentException("Illegal square coordinates.");
        }
        square.setWritable(writable);
    }

    /**
     * {@inheritDoc}
     */
    public void setSquareCharacter(int row, int column, Character character) throws IllegalArgumentException {
        SampleCrosswordImpl.SquareImpl square = getSquareImpl(row, column);
        if (square == null) {
            throw new IllegalArgumentException("Illegal square coordinates.");
        }
        square.setCharacter(character);
    }

    /**
     * {@inheritDoc}
     */
    public List<Square> getRow(int row) throws IllegalArgumentException {
        if (row < 0 || row >= rowCount) {
            throw new IllegalArgumentException("Illegal row number.");
        }

        List<Square> result = new ArrayList<Square>(columnCount);
        for (int i = 0; i < columnCount; i++) {
            result.add(squares[row][i]);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public List<Square> getColumn(int column) throws IllegalArgumentException {
        if (column < 0 || column >= columnCount) {
            throw new IllegalArgumentException("Illegal column number.");
        }

        List<Square> result = new ArrayList<Square>(rowCount);
        for (int i = 0; i < rowCount; i++) {
            result.add(squares[i][column]);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<Square> iterator() {
        List<Square> all = new ArrayList<Square>(rowCount * columnCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                all.add(squares[i][j]);
            }
        }
        return Collections.unmodifiableList(all).iterator();
    }

    /**
     * {@inheritDoc}
     */
    public int generateClueIndices() {
        clues.clear();
        int clueIndex = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                SampleCrosswordImpl.SquareImpl square = squares[i][j];
                square.setClueIndex(null);

                boolean hasDownClue = hasDownClue(i, j);
                boolean hasAcrossClue = hasAcrossClue(i, j);

                if (hasDownClue || hasAcrossClue) {
                    clueIndex++;
                    square.setClueIndex(clueIndex);
                }
                if (hasDownClue) {
                    clues.add(new SampleCrosswordImpl.Clue(clueIndex, Direction.DOWN, square));
                }
                if (hasAcrossClue) {
                    clues.add(new SampleCrosswordImpl.Clue(clueIndex, Direction.ACROSS, square));
                }
            }
        }
        return clueIndex;
    }

    private boolean hasDownClue(int row, int column) {
        SampleCrosswordImpl.SquareImpl square = getSquareImpl(row, column);
        SampleCrosswordImpl.SquareImpl squareAbove = getSquareImpl(row - 1, column);
        SampleCrosswordImpl.SquareImpl squareBelow = getSquareImpl(row + 1, column);

        return square.isWritable() &&
               (squareAbove == null || !squareAbove.isWritable()) &&
               (squareBelow != null && squareBelow.isWritable());
    }

    private boolean hasAcrossClue(int row, int column) {
        SampleCrosswordImpl.SquareImpl square = getSquareImpl(row, column);
        SampleCrosswordImpl.SquareImpl squareLeft = getSquareImpl(row, column - 1);
        SampleCrosswordImpl.SquareImpl squareRight = getSquareImpl(row, column + 1);

        return square.isWritable() &&
               (squareLeft == null || !squareLeft.isWritable()) &&
               (squareRight != null && squareRight.isWritable());
    }

    /**
     * {@inheritDoc}
     */
    public Square getSquare(int clueIndex) {
        for (Square square : this) {
            if (square.getClueIndex() != null && square.getClueIndex() == clueIndex) {
                return square;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<Character> getAnswer(int clueIndex, Direction direction) throws IllegalArgumentException {
        SampleCrosswordImpl.Clue clue = getClue(clueIndex, direction);
        if (clue == null) {
            throw new IllegalArgumentException("Illegal clue index and/or direction.");
        }

        List<Character> result = new ArrayList<Character>();
        for (SampleCrosswordImpl.SquareImpl square = clue.getSquare(); square != null && square.isWritable(); square = getNextSquare(square, direction))
        {
            result.add(square.getCharacter());
        }
        return result;
    }

    private SampleCrosswordImpl.SquareImpl getNextSquare(SampleCrosswordImpl.SquareImpl square, Direction direction) {
        int row = square.getRow();
        int column = square.getColumn();

        if (direction == Direction.ACROSS) {
            column++;
        } else {
            row++;
        }

        return getSquareImpl(row, column);
    }

    private SampleCrosswordImpl.Clue getClue(int clueIndex, Direction direction) {
        for (SampleCrosswordImpl.Clue clue : clues) {
            if (clue.getClueIndex() == clueIndex && clue.getDirection() == direction) {
                return clue;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void setAnswer(int clueIndex, Direction direction, String answer) throws IllegalArgumentException {
        List<Character> existingAnswer = getAnswer(clueIndex, direction);
        if (existingAnswer.size() != answer.length()) {
            throw new IllegalArgumentException("Illegal answer length.");
        }

        SampleCrosswordImpl.Clue clue = getClue(clueIndex, direction);
        int i = 0;
        for (SampleCrosswordImpl.SquareImpl square = clue.getSquare(); square != null && square.isWritable(); square = getNextSquare(square, direction))
        {
            square.setCharacter(answer.charAt(i++));
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAnswerValid(int clueIndex, Direction direction, String answer) throws IllegalArgumentException {
        List<Character> existingAnswer = getAnswer(clueIndex, direction);
        if (existingAnswer.size() != answer.length()) {
            return false;
        }

        for (int i = 0; i < existingAnswer.size(); i++) {
            Character c = existingAnswer.get(i);
            if (c != null && c != answer.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Private implementation of square interface.
     */
    private static class SquareImpl implements Square {

        private final int row;
        private final int column;

        private boolean writable = true;
        private Character character;
        private Integer clueIndex;

        public SquareImpl(int row, int column) {
            this.row = row;
            this.column = column;
        }

        int getRow() {
            return row;
        }

        int getColumn() {
            return column;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isWritable() {
            return writable;
        }

        void setWritable(boolean writable) {
            this.writable = writable;
        }

        /**
         * {@inheritDoc}
         */
        public Character getCharacter() {
            return character;
        }

        void setCharacter(Character character) {
            this.character = character;
        }

        /**
         * {@inheritDoc}
         */
        public Integer getClueIndex() {
            return clueIndex;
        }

        void setClueIndex(Integer clueIndex) {
            this.clueIndex = clueIndex;
        }
    }

    /**
     * Internal helper class. Contains information about a clue.
     */
    private static class Clue {

        private final int clueIndex;
        private final Direction direction;
        private final SampleCrosswordImpl.SquareImpl square;

        public Clue(int clueIndex, Direction direction, SampleCrosswordImpl.SquareImpl square) {
            this.clueIndex = clueIndex;
            this.direction = direction;
            this.square = square;
        }

        public int getClueIndex() {
            return clueIndex;
        }

        public Direction getDirection() {
            return direction;
        }

        public SampleCrosswordImpl.SquareImpl getSquare() {
            return square;
        }
    }
}
