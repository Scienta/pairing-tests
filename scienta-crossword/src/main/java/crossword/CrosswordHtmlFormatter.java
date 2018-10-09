package crossword;

/**
 * Generates HTML representations of crosswords.
 * <p/>
 * You are free to modify this class if you like.
 *
 * @see Crossword
 */
public class CrosswordHtmlFormatter {

    /**
     * Creates an HTML representation of the given crossword.
     *
     * @param crossword The crossword to format.
     * @return An HTML document.
     */
    public String format(Crossword crossword) {

        StringBuffer buf = new StringBuffer();
        buf.append("<html>\n" +
                   "<head>\n" +
                   "\t<style type='text/css'>\n" +
                   "\t\ttable {border-collapse:collapse; border:3px solid black}\n" +
                   "\t\ttd {width:1.9em; height:1.9em; border:1px solid black; font-family:verdana,sans-serif; font-size:14pt}\n" +
                   "\t\ttd.black {background-color:black; color:white}\n" +
                   "\t\ttd.white {background-color:white; color:black}\n" +
                   "\t\tsup {font-size:7pt}\n" +
                   "\t</style>\n" +
                   "</head>\n" +
                   "<body>\n" +
                   "<table>\n");

        for (int i = 0; i < crossword.getRowCount(); i++) {
            buf.append("\t<tr>\n");

            for (int j = 0; j < crossword.getColumnCount(); j++) {
                Square square = crossword.getSquare(i, j);
                String cssClass = square.isWritable() ? "white" : "black";
                String clueIndex = square.getClueIndex() != null ? String.valueOf(square.getClueIndex()) : "&nbsp;";
                String character = square.getCharacter() != null ? String.valueOf(square.getCharacter()) : "&nbsp;";
                buf.append("\t\t<td class='").append(cssClass).append("'><sup>");
                buf.append(clueIndex).append("</sup>").append(character).append("</td>\n");
            }

            buf.append("\t</tr>\n");
        }

        buf.append("</table>\n" +
                   "</body>\n" +
                   "</html>");

        return buf.toString();
    }
}
