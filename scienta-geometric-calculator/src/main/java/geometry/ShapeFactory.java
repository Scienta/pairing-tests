package geometry;


/**
 * A factory for creating {@link Shape} instances.
 *
 * @author Sindre Mehus
 */
public class ShapeFactory {

    /**
     * Returns a shape instance for the given shape type and parameters.
     *
     * @param shapeType  The shape type, e.g., circle or square.
     * @param parameters Shape parameters.
     * @return The newly created shape instance.
     * @throws IllegalArgumentException If the shape type is unknown, or if
     *                                  the shape parameters are illegal.
     */
    public Shape create(ShapeType shapeType, double[] parameters) {
        if (shapeType == null) {
            throw new IllegalArgumentException();
        }

        switch (shapeType) {
            case RECTANGLE:
                return new Rectangle(parameters[0], parameters[1]);
            case SQUARE:
                return new Square(parameters[0]);
            case CIRCLE:
                return new Circle(parameters[0]);
            default:
                throw new IllegalArgumentException();
        }
    }
}
