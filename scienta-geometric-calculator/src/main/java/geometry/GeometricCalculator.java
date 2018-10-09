package geometry;

/**
 * Computes area and circumference of geometric shapes,
 * such as circles and rectangles.
 * <p/>
 * NOTE: YOU SHOULD NOT MODIFY THIS INTERFACE.
 */
public interface GeometricCalculator {

    /**
     * Computes the area of the given shape with the given parameters.
     *
     * @param shape      The shape type.
     * @param parameters The shape parameters.
     * @return The area.
     */
    double area(ShapeType shape, double[] parameters);

    /**
     * Computes the circumference of the given shape with the given parameters.
     *
     * @param shape      The shape type.
     * @param parameters The shape parameters.
     * @return The circumference.
     */
    double circumference(ShapeType shape, double[] parameters);

}
