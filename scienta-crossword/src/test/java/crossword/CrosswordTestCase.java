package crossword;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unit test of {@link Crossword}, to be used for automated assessment
 * of code quality.
 *
 * @author Sindre Mehus
 */
public class CrosswordTestCase extends TestCase {

    private CrosswordFactory factory;
    private Crossword testCrossword;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        factory = new CrosswordFactory();

        int[][] crosswordDefinition = {
                {1, 0, 2, -1, -1},
                {0, -1, 0, -1, -1},
                {3, 0, 0, 0, 4},
                {-1, -1, 0, -1, 0},
                {-1, -1, 5, 0, 0}
        };

        testCrossword = createCrossword(crosswordDefinition);
    }

    /**
     * Verify that the factory creates new instances every time.
     */
    public void testFactoryCreatesNewInstance() {
        Crossword c1 = factory.createCrossword(2, 2);
        Crossword c2 = factory.createCrossword(2, 2);
        Crossword c3 = factory.createCrossword(3, 3);

        assertNotSame("Error in createCrossword().", c1, c2);
        assertNotSame("Error in createCrossword().", c1, c3);
        assertNotSame("Error in createCrossword().", c2, c3);
    }

    /**
     * Verify that IllegalArgumentException is thrown if specifying
     * negative row and/or column count.
     */
    public void testIllegalSize() {
        try {
            factory.createCrossword(-1, 10);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            factory.createCrossword(10, -1);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            factory.createCrossword(-3, -3);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }
    }

    /**
     * Verify that crosswords have the correct dimensions.
     */
    public void testDimensions() {
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                Crossword c = factory.createCrossword(i, j);
                assertEquals("Wrong number of rows.", i, c.getRowCount());
                assertEquals("Wrong number of columns.", j, c.getColumnCount());
            }
        }
    }

    /**
     * Verify that zero rows and/or columns works.
     */
    public void testZeroRowsOrColumns() {
        Crossword c = factory.createCrossword(0, 0);
        assertEquals("Wrong number of rows.", 0, c.getRowCount());
        assertEquals("Wrong number of columns.", 0, c.getColumnCount());

        c = factory.createCrossword(0, 3);
        assertEquals("Wrong number of rows.", 0, c.getRowCount());
        assertEquals("Wrong number of columns.", 3, c.getColumnCount());

        c = factory.createCrossword(3, 0);
        assertEquals("Wrong number of rows.", 3, c.getRowCount());
        assertEquals("Wrong number of columns.", 0, c.getColumnCount());
    }

    /**
     * Verify that squares are initially empty and writable.
     */
    public void testInitialSquareState() {
        Crossword c = factory.createCrossword(4, 5);
        for (int i = 0; i < c.getRowCount(); i++) {
            for (int j = 0; j < c.getColumnCount(); j++) {
                Square square = c.getSquare(i, j);
                assertNull("Character should be null initially.", square.getCharacter());
                assertNull("Clue index should be null initially.", square.getClueIndex());
                assertTrue("Square should be writable initially.", square.isWritable());
            }
        }
    }

    /**
     * Verify that getSquare() returns null when row and column are outside the crossword.
     */
    public void testGetNonExistentSquare() {
        Crossword c = factory.createCrossword(2, 10);
        assertNull("Error in getSquare().", c.getSquare(-1, -1));
        assertNull("Error in getSquare().", c.getSquare(-1, 0));
        assertNull("Error in getSquare().", c.getSquare(0, -1));
        assertNull("Error in getSquare().", c.getSquare(2, 1));
        assertNull("Error in getSquare().", c.getSquare(3, 1));
        assertNull("Error in getSquare().", c.getSquare(1, 10));
        assertNull("Error in getSquare().", c.getSquare(1, 11));
    }

    /**
     * Verify that setting and getting of characters work.
     */
    public void testSetSquareCharacter() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Crossword c = factory.createCrossword(i, j);
                doTestSetSquareCharacter(c);
            }
        }
    }

    /**
     * Negative tests of setSquareCharacter().
     */
    public void testSetSquareCharacterNegative() {

        Crossword c = factory.createCrossword(2, 3);
        try {
            c.setSquareCharacter(-1, 2, 'a');
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            c.setSquareCharacter(1, -1, 'a');
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            c.setSquareCharacter(2, 1, 'a');
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            c.setSquareCharacter(1, 3, 'a');
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }
    }

    private void doTestSetSquareCharacter(Crossword crossword) {
        char character = 'a';
        for (int i = 0; i < crossword.getRowCount(); i++) {
            for (int j = 0; j < crossword.getColumnCount(); j++) {
                crossword.setSquareCharacter(i, j, character);
                character++;
            }
        }

        character = 'a';
        for (int i = 0; i < crossword.getRowCount(); i++) {
            for (int j = 0; j < crossword.getColumnCount(); j++) {
                Square square = crossword.getSquare(i, j);
                assertNotNull("Error in getSquare().", square);
                assertEquals("Error in getCharacter().", Character.valueOf(character), square.getCharacter());
                character++;
            }
        }

        for (int i = 0; i < crossword.getRowCount(); i++) {
            for (int j = 0; j < crossword.getColumnCount(); j++) {
                crossword.setSquareCharacter(i, j, null);
            }
        }

        for (int i = 0; i < crossword.getRowCount(); i++) {
            for (int j = 0; j < crossword.getColumnCount(); j++) {
                Square square = crossword.getSquare(i, j);
                assertNull("Error in getCharacter().", square.getCharacter());
            }
        }
    }

    /**
     * Verify that setSquareWritable() works.
     */
    public void testSetSquareWritable() {
        Crossword c = factory.createCrossword(2, 3);

        c.setSquareWritable(1, 2, false);
        assertFalse("Error in setSquareWritable().", c.getSquare(1, 2).isWritable());

        c.setSquareWritable(1, 2, true);
        assertTrue("Error in setSquareWritable().", c.getSquare(1, 2).isWritable());
    }

    /**
     * Negative tests of setSquareWritable().
     */
    public void testSetSquareWritableNegative() {
        Crossword c = factory.createCrossword(2, 3);

        try {
            c.setSquareWritable(-1, 2, true);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            c.setSquareWritable(1, -1, true);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            c.setSquareWritable(2, 1, true);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            c.setSquareWritable(1, 3, true);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }
    }

    /**
     * Verify that getRow() works.
     */
    public void testGetRow() {
        Crossword c = factory.createCrossword(2, 3);

        List<Square> row = c.getRow(0);
        assertEquals("Error in getRow().", 3, row.size());
        assertSame("Error in getRow().", c.getSquare(0, 0), row.get(0));
        assertSame("Error in getRow().", c.getSquare(0, 1), row.get(1));
        assertSame("Error in getRow().", c.getSquare(0, 2), row.get(2));

        row = c.getRow(1);
        assertEquals("Error in getRow().", 3, row.size());
        assertSame("Error in getRow().", c.getSquare(1, 0), row.get(0));
        assertSame("Error in getRow().", c.getSquare(1, 1), row.get(1));
        assertSame("Error in getRow().", c.getSquare(1, 2), row.get(2));
    }

    /**
     * Negative tests of getRow().
     */
    public void testGetRowNegative() {
        Crossword c = factory.createCrossword(2, 3);

        try {
            c.getRow(-1);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            c.getRow(2);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }
    }

    /**
     * Verify that getRow() works with zero columns.
     */
    public void testGetRowZeroColumns() {
        Crossword c = factory.createCrossword(2, 0);
        assertTrue("Error in getRow().", c.getRow(0).isEmpty());
        assertTrue("Error in getRow().", c.getRow(1).isEmpty());
    }

    /**
     * Verify that getRow() works if you modify the returned list.
     */
    public void testGetRowModify() {
        Crossword c = factory.createCrossword(2, 3);

        List<Square> row = c.getRow(0);
        assertEquals("Error in getRow().", 3, row.size());
        assertSame("Error in getRow().", c.getSquare(0, 0), row.get(0));
        assertSame("Error in getRow().", c.getSquare(0, 1), row.get(1));
        assertSame("Error in getRow().", c.getSquare(0, 2), row.get(2));

        row.clear();
        row = c.getRow(0);
        assertEquals("Error in getRow().", 3, row.size());
        assertSame("Error in getRow().", c.getSquare(0, 0), row.get(0));
        assertSame("Error in getRow().", c.getSquare(0, 1), row.get(1));
        assertSame("Error in getRow().", c.getSquare(0, 2), row.get(2));
    }

    /**
     * Verify that getColumn() works.
     */
    public void testGetColumn() {
        Crossword c = factory.createCrossword(2, 3);

        List<Square> column = c.getColumn(0);
        assertEquals("Error in getColumn().", 2, column.size());
        assertSame("Error in getColumn().", c.getSquare(0, 0), column.get(0));
        assertSame("Error in getColumn().", c.getSquare(1, 0), column.get(1));

        column = c.getColumn(1);
        assertEquals("Error in getColumn().", 2, column.size());
        assertSame("Error in getColumn().", c.getSquare(0, 1), column.get(0));
        assertSame("Error in getColumn().", c.getSquare(1, 1), column.get(1));

        column = c.getColumn(2);
        assertEquals("Error in getColumn().", 2, column.size());
        assertSame("Error in getColumn().", c.getSquare(0, 2), column.get(0));
        assertSame("Error in getColumn().", c.getSquare(1, 2), column.get(1));
    }

    /**
     * Negative tests of getColumn().
     */
    public void testGetColumnNegative() {
        Crossword c = factory.createCrossword(2, 3);

        try {
            c.getColumn(-1);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            c.getColumn(3);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }
    }

    /**
     * Verify that getColumn() works with zero rows.
     */
    public void testGetColumnZeroRows() {
        Crossword c = factory.createCrossword(0, 2);
        assertTrue("Error in getColumn().", c.getColumn(0).isEmpty());
        assertTrue("Error in getColumn().", c.getColumn(1).isEmpty());
    }

    /**
     * Verify that getColumn() works if you modify the returned list.
     */
    public void testGetColumnModify() {
        Crossword c = factory.createCrossword(2, 3);

        List<Square> column = c.getColumn(0);
        assertEquals("Error in getColumn().", 2, column.size());
        assertSame("Error in getColumn().", c.getSquare(0, 0), column.get(0));
        assertSame("Error in getColumn().", c.getSquare(1, 0), column.get(1));

        column.clear();
        column = c.getColumn(0);
        assertEquals("Error in getColumn().", 2, column.size());
        assertSame("Error in getColumn().", c.getSquare(0, 0), column.get(0));
        assertSame("Error in getColumn().", c.getSquare(1, 0), column.get(1));
    }

    /**
     * Verify that iterator() works.
     */
    public void testIterator() {
        Crossword c = factory.createCrossword(2, 2);
        Iterator<Square> iterator = c.iterator();

        assertTrue("Error in hasNext().", iterator.hasNext());
        assertSame("Error in next().", c.getSquare(0, 0), iterator.next());

        assertTrue("Error in hasNext().", iterator.hasNext());
        assertSame("Error in next().", c.getSquare(0, 1), iterator.next());

        assertTrue("Error in hasNext().", iterator.hasNext());
        assertSame("Error in next().", c.getSquare(1, 0), iterator.next());

        assertTrue("Error in hasNext().", iterator.hasNext());
        assertSame("Error in next().", c.getSquare(1, 1), iterator.next());

        assertFalse("Error in hasNext().", iterator.hasNext());
        try {
            iterator.next();
            fail("Expected NoSuchElementException.");
        } catch (NoSuchElementException x) {
        }
    }

    /**
     * Verify that iterator doesn't implement remove().
     */
    public void testIteratorRemove() {
        Crossword c = factory.createCrossword(2, 2);
        Iterator<Square> iterator = c.iterator();
        iterator.next();

        try {
            iterator.remove();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException x) {
        }
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices1() {
        int[][] crossword = {
                {1, 2},
                {3, 0},
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices2() {
        int[][] crossword = {
                {-1, 1},
                {2, 0},
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices3() {
        int[][] crossword = {
                {-1, -1},
                {1, 0},
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices4() {
        int[][] crossword = {
                {-1, -1},
                {-1, 0},
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices5() {
        int[][] crossword = {
                {-1, -1},
                {-1, -1},
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices6() {
        int[][] crossword = {
                {1, 0, 2, -1, -1},
                {0, -1, 0, -1, -1},
                {3, 0, 0, 0, 4},
                {-1, -1, 0, -1, 0},
                {-1, -1, 5, 0, 0}
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices7() {
        int[][] crossword = {
                {-1, 1, -1, 2, -1, 3, -1, 4},
                {-1, 5, 0, 0, 0, 0, 0, 0},
                {-1, 0, -1, 0, -1, 0, -1, 0},
                {6, 0, 0, 0, 0, 0, 0, 0},
                {-1, 0, -1, 0, -1, 0, -1, 0},
                {7, 0, 8, 0, 9, 0, 10, 0, -1},
                {11, 0, 0, 0, 0, 0, 0, -1}
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices8() {
        int[][] crossword = {
                {0}
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * Verify that generateClueIndices() works with the given crossword.
     */
    public void testGenerateClueIndices9() {
        int[][] crossword = {
                {-1}
        };
        doTestGenerateClueIndices(crossword);
    }

    /**
     * The integers in the matrix are interpreted as follows:
     * <p/>
     * -1: The square is black.<br/>
     * 0: The square is white and has no clue index.<br/>
     * >0: The square is white and has the given clue index.<br/>
     */
    private void doTestGenerateClueIndices(int[][] crosswordDefinition) {
        int rows = crosswordDefinition.length;
        int columns = crosswordDefinition[0].length;
        Crossword crossword = createCrossword(crosswordDefinition);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Square square = crossword.getSquare(i, j);
                switch (crosswordDefinition[i][j]) {
                    case-1:
                        assertFalse("Error in generateClueIndices().", square.isWritable());
                        assertNull("Error in generateClueIndices().", square.getClueIndex());
                        break;
                    case 0:
                        assertTrue("Error in generateClueIndices().", square.isWritable());
                        assertNull("Error in generateClueIndices().", square.getClueIndex());
                        break;
                    default:
                        assertTrue("Error in generateClueIndices().", square.isWritable());
                        assertEquals("Error in generateClueIndices().", crosswordDefinition[i][j], square.getClueIndex().intValue());
                        break;

                }
            }
        }
    }

    private Crossword createCrossword(int[][] crosswordDefinition) {
        int rows = crosswordDefinition.length;
        int columns = crosswordDefinition[0].length;
        Crossword crossword = factory.createCrossword(rows, columns);

        int highestClueIndex = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                highestClueIndex = Math.max(highestClueIndex, crosswordDefinition[i][j]);
                if (crosswordDefinition[i][j] == -1) {
                    crossword.setSquareWritable(i, j, false);
                }
            }
        }

        assertEquals("Error in generateClueIndices().", highestClueIndex, crossword.generateClueIndices());
        return crossword;
    }

    /**
     * Test generateClueIndices() when the crossword is empty.
     */
    public void testGenerateClueIndicesEmpty() {
        assertEquals("Error in generateClueIndices().", 0, factory.createCrossword(0, 0).generateClueIndices());
        assertEquals("Error in generateClueIndices().", 0, factory.createCrossword(0, 3).generateClueIndices());
        assertEquals("Error in generateClueIndices().", 0, factory.createCrossword(3, 0).generateClueIndices());
    }

    /**
     * Test of getSquare().
     */
    public void testGetSquare() {
        assertNull("Error in getSquare().", testCrossword.getSquare(-1));
        assertNull("Error in getSquare().", testCrossword.getSquare(0));
        assertSame("Error in getSquare().", testCrossword.getSquare(0, 0), testCrossword.getSquare(1));
        assertSame("Error in getSquare().", testCrossword.getSquare(0, 2), testCrossword.getSquare(2));
        assertSame("Error in getSquare().", testCrossword.getSquare(2, 0), testCrossword.getSquare(3));
        assertSame("Error in getSquare().", testCrossword.getSquare(2, 4), testCrossword.getSquare(4));
        assertSame("Error in getSquare().", testCrossword.getSquare(4, 2), testCrossword.getSquare(5));
        assertNull("Error in getSquare().", testCrossword.getSquare(6));
    }

    /**
     * Test of getAnswer().
     */
    public void testGetAnswer() {

        doTestAnswer(testCrossword, 3, Direction.ACROSS, "_____");

        testCrossword.setSquareCharacter(2, 0, 'S');
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "S____");

        testCrossword.setSquareCharacter(2, 1, 'O');
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "SO___");

        testCrossword.setSquareCharacter(2, 2, 'L');
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "SOL__");

        testCrossword.setSquareCharacter(2, 3, 'I');
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "SOLI_");

        testCrossword.setSquareCharacter(2, 4, 'D');
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "SOLID");

        testCrossword.setSquareCharacter(2, 0, null);
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "_OLID");

        testCrossword.setSquareCharacter(2, 1, null);
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "__LID");

        testCrossword.setSquareCharacter(2, 2, null);
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "___ID");

        testCrossword.setSquareCharacter(2, 3, null);
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "____D");

        testCrossword.setSquareCharacter(2, 4, null);
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "_____");
    }

    /**
     * Test of setAnswer().
     */
    public void testSetAnswer() {
        testCrossword.setAnswer(3, Direction.ACROSS, "SOLID");
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "SOLID");

        testCrossword.setAnswer(3, Direction.ACROSS, "SHAFT");
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "SHAFT");

        testCrossword.setAnswer(2, Direction.DOWN, "ALLOW");
        doTestAnswer(testCrossword, 2, Direction.DOWN, "ALLOW");
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "SHLFT");

        testCrossword.setAnswer(1, Direction.DOWN, "CAR");
        doTestAnswer(testCrossword, 1, Direction.DOWN, "CAR");
        doTestAnswer(testCrossword, 3, Direction.ACROSS, "RHLFT");
    }

    private void doTestAnswer(Crossword crossword, int clueIndex, Direction direction, String expectedAnswer) {
        List<Character> actualAnswer = crossword.getAnswer(clueIndex, direction);
        assertEquals("Wrong answer length.", expectedAnswer.length(), actualAnswer.size());

        for (int i = 0; i < expectedAnswer.length(); i++) {
            Character actualChar = actualAnswer.get(i);
            Character expectedChar = expectedAnswer.charAt(i) == '_' ? null : expectedAnswer.charAt(i);
            assertEquals("Wrong character in answer.", expectedChar, actualChar);
        }
    }

    /**
     * Test of isAnswerValid().
     */
    public void testIsAnswerValid() {
        assertTrue("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLID"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLI"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLIDS"));

        testCrossword.setSquareCharacter(2, 0, 'S');
        assertTrue("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLID"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "MOLID"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLI"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLIDS"));

        testCrossword.setSquareCharacter(2, 2, 'L');
        assertTrue("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLID"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "MOLID"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SORID"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLI"));
        assertFalse("Error in isAnswerValid().", testCrossword.isAnswerValid(3, Direction.ACROSS, "SOLIDS"));
    }

    /**
     * Negative test of getAnswer().
     */
    public void testGetAnswerNegative() {
        try {
            testCrossword.getAnswer(0, Direction.ACROSS);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.getAnswer(2, Direction.ACROSS);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.getAnswer(3, Direction.DOWN);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.getAnswer(4, Direction.ACROSS);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.getAnswer(5, Direction.DOWN);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.getAnswer(6, Direction.DOWN);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }
    }

    /**
     * Negative test of setAnswer().
     */
    public void testSetAnswerNegative() {
        try {
            testCrossword.setAnswer(0, Direction.ACROSS, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.setAnswer(2, Direction.ACROSS, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.setAnswer(3, Direction.DOWN, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.setAnswer(4, Direction.ACROSS, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.setAnswer(5, Direction.DOWN, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.setAnswer(6, Direction.DOWN, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }
    }

    /**
     * Negative test of isAnswerValid().
     */
    public void testIsAnswerValidNegative() {
        try {
            testCrossword.isAnswerValid(0, Direction.ACROSS, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.isAnswerValid(2, Direction.ACROSS, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.isAnswerValid(3, Direction.DOWN, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.isAnswerValid(4, Direction.ACROSS, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.isAnswerValid(5, Direction.DOWN, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }

        try {
            testCrossword.isAnswerValid(6, Direction.DOWN, "foo");
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException x) {
        }
    }

}
