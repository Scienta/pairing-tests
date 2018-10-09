package geometry;

/**
 * A geometric shape with methods for computing the area
 * and circumference.
 *
 * @author Sindre Mehus
 */
public interface Shape {

    /**
     * Returns the area of this shape.
     *
     * @return The area.
     */
    double area();

    /**
     * Returns the circumference of this shape.
     *
     * @return The circumference.
     */
    double circumference();
}
