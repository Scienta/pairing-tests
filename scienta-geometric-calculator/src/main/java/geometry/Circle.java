package geometry;

/**
 * A circle is defined by its radius.
 *
 * @author Sindre Mehus
 */
public class Circle implements Shape {

    private final double radius;

    /**
     * Creates a new circle.
     *
     * @param radius The radius.
     * @throws IllegalArgumentException If the radius is negative.
     */
    public Circle(double radius) {
        if (radius < 0.0) {
            throw new IllegalArgumentException();
        }
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     */
    public double area() {
        return Math.PI * radius * radius;
    }

    /**
     * {@inheritDoc}
     */
    public double circumference() {
        return 2.0 * Math.PI * radius;
    }
}
