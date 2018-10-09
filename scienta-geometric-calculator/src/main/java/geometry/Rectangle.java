package geometry;

/**
 * A rectangle is defined by its width and height.
 *
 * @author Sindre Mehus
 */
public class Rectangle implements Shape {

    private final double width;
    private final double height;

    /**
     * Creates a new rectangle.
     *
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @throws IllegalArgumentException If the width or height is negative.
     */
    public Rectangle(double width, double height) {
        if (width < 0.0 || height < 0.0) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;
    }

    /**
     * {@inheritDoc}
     */
    public double area() {
        return width * height;
    }

    /**
     * {@inheritDoc}
     */
    public double circumference() {
        return 2.0 * (width + height);
    }
}