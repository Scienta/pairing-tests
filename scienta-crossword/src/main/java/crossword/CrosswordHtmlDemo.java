package crossword;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Sample application which creates a simple crossword and
 * generates an HTML representation of it.
 * <p/>
 * You are free to modify this class if you like.
 */
public class CrosswordHtmlDemo {

    public CrosswordHtmlDemo() throws IOException {
        Crossword crossword = createCrossword();
        formatCrossword(crossword);
    }

    private Crossword createCrossword() {
        CrosswordFactory factory = new CrosswordFactory();
        Crossword crossword = factory.createCrossword(9, 9);

        crossword.setSquareWritable(0, 4, false);
        crossword.setSquareWritable(1, 3, false);
        crossword.setSquareWritable(1, 8, false);
        crossword.setSquareWritable(2, 2, false);
        crossword.setSquareWritable(2, 5, false);
        crossword.setSquareWritable(2, 7, false);
        crossword.setSquareWritable(3, 1, false);
        crossword.setSquareWritable(3, 6, false);
        crossword.setSquareWritable(4, 4, false);
        crossword.setSquareWritable(5, 2, false);
        crossword.setSquareWritable(5, 7, false);
        crossword.setSquareWritable(6, 1, false);
        crossword.setSquareWritable(6, 3, false);
        crossword.setSquareWritable(6, 6, false);
        crossword.setSquareWritable(7, 0, false);
        crossword.setSquareWritable(7, 5, false);
        crossword.setSquareWritable(8, 4, false);

        crossword.generateClueIndices();
        crossword.setAnswer(29, Direction.ACROSS, "DUKE");

        return crossword;
    }

    private void formatCrossword(Crossword crossword) throws IOException {
        CrosswordHtmlFormatter formatter = new CrosswordHtmlFormatter();

        File file = new File("crossword.html");
        Writer writer = new FileWriter(file);
        writer.write(formatter.format(crossword));
        writer.close();

        System.out.println("Result written to " + file.getAbsolutePath() + ".");
    }

    public static void main(String[] args) throws IOException {
        new CrosswordHtmlDemo();
    }
}
