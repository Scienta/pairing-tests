package geometry;

/**
 * Simple class which demonstrates the use of {@link GeometricCalculator}.
 * <p/>
 * You are free to modify this class if you like.
 */
public class GeometricCalculatorDemo {

    public GeometricCalculatorDemo() {
        GeometricCalculator calc = new GeometricCalculatorImpl();

        System.out.println("Area of 2 x 3 rectangle: " + calc.area(ShapeType.RECTANGLE, new double[]{2, 3}));
        System.out.println("Circumference of circle with radius 5: " + calc.circumference(ShapeType.CIRCLE, new double[]{5}));
    }

    public static void main(String[] args) {
        new GeometricCalculatorDemo();
    }
}
