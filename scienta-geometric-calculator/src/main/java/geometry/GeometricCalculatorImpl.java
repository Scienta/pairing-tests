package geometry;

/**
 * Implementation of the {@link GeometricCalculator} interface.
 * <p/>
 * You should modify this class.
 */
public class GeometricCalculatorImpl implements GeometricCalculator {

    @Override
    public double area(ShapeType shape, double[] parameters) {
        double area;

        if (shape == null) {
            throw new IllegalArgumentException();
        }

        if ("RECTANGLE".equals(shape.toString())) {
            if (parameters[0] < 0.0 || parameters[1] < 0.0) {
                throw new IllegalArgumentException();
            } else {
                area = parameters[0] * parameters[1];
            }
        } else if ("SQUARE".equals(shape.toString())) {
            if (parameters[0] < 0.0) {
                throw new IllegalArgumentException();
            } else {
                area = parameters[0] * parameters[0];
            }
        } else if ("CIRCLE".equals(shape.toString())) {
            if (parameters[0] < 0.0) {
                throw new IllegalArgumentException();
            } else {
                area = Math.PI * parameters[0] * parameters[0];
            }
        } else {
            throw new IllegalArgumentException();
        }

        return area;
    }

    @Override
    public double circumference(ShapeType shape, double[] parameters) {
        double circumference;

        if (shape == null) {
            throw new IllegalArgumentException();
        }

        if ("RECTANGLE".equals(shape.toString())) {
            if (parameters[0] < 0.0 || parameters[1] < 0.0) {
                throw new IllegalArgumentException();
            } else {
                circumference = parameters[0] + parameters[1] + parameters[0] + parameters[1];
            }
        } else if ("SQUARE".equals(shape.toString())) {
            if (parameters[0] < 0.0) {
                throw new IllegalArgumentException();
            } else {
                circumference = 4 * parameters[0];
            }
        } else if ("CIRCLE".equals(shape.toString())) {
            if (parameters[0] < 0.0) {
                throw new IllegalArgumentException();
            } else {
                circumference = 2.0 * Math.PI * parameters[0];
            }
        } else {
            throw new IllegalArgumentException();
        }

        return circumference;
    }

}
