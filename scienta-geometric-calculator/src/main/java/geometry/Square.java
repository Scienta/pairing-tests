package geometry;

/**
 * A square is defined by its size (width and height).
 *
 * @author Sindre Mehus
 */
public class Square extends Rectangle {

    /**
     * Creates a new square.
     *
     * @param size The size (width and height) of the square.
     * @throws IllegalArgumentException If the size is negative.
     */
    public Square(double size) {
        super(size, size);
    }

}
