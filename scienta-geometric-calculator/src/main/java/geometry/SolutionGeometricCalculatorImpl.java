package geometry;


/**
 * Sample solution implementation.
 *
 * @author Sindre Mehus
 */
public class SolutionGeometricCalculatorImpl implements GeometricCalculator {

    private final ShapeFactory shapeFactory = new ShapeFactory();

    /**
     * {@inheritDoc}
     */
    public double area(ShapeType shapeType, double[] parameters) {
        Shape shape = shapeFactory.create(shapeType, parameters);
        return shape.area();
    }

    /**
     * {@inheritDoc}
     */
    public double circumference(ShapeType shapeType, double[] parameters) {
        Shape shape = shapeFactory.create(shapeType, parameters);
        return shape.circumference();
    }

}
