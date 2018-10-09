package geometry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of {@link GeometricCalculatorImpl}.
 *
 * @author Sindre Mehus
 */
public class GeometricCalculatorTestCase {

    private final static double DELTA = 0.000001;
    private GeometricCalculator calculator;

    @Before
    public void before() {
        calculator = new GeometricCalculatorImpl();
    }

    @Test
    public void testCircleArea() {
        double radius = 234.1235;
        assertEquals("Wrong area.", Math.PI * radius * radius, calculator.area(ShapeType.CIRCLE, new double[]{radius}), DELTA);
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.CIRCLE, new double[]{0.0}), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCircleAreaNegative() {
        calculator.area(ShapeType.CIRCLE, new double[] {-1.0});
    }

    @Test
    public void testRectangleArea() {
        double width = 9.356;
        double height = 7.2235;
        assertEquals("Wrong area.", width * height, calculator.area(ShapeType.RECTANGLE, new double[]{width, height}), DELTA);
        assertEquals("Wrong area.", width * height, calculator.area(ShapeType.RECTANGLE, new double[]{height, width}), DELTA);
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.RECTANGLE, new double[]{width, 0.0}), DELTA);
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.RECTANGLE, new double[]{0.0, height}), DELTA);
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.RECTANGLE, new double[]{0.0, 0.0}), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRectangleAreaNegative1() {
            calculator.area(ShapeType.RECTANGLE, new double[]{-1.0, 1.0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRectangleAreaNegative2() {
            calculator.area(ShapeType.RECTANGLE, new double[]{1.0, -1.0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRectangleAreaNegative3() {
            calculator.area(ShapeType.RECTANGLE, new double[]{-1.0, -1.0});
    }

    @Test
    public void testSquareArea() {
        double size = 73.2342;
        assertEquals("Wrong area.", size * size, calculator.area(ShapeType.SQUARE, new double[]{size}), DELTA);
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.SQUARE, new double[]{0.0}), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSquareAreaNegative() {
        calculator.area(ShapeType.SQUARE, new double[]{-1.0});
    }

    @Test
    public void testCircleCircumference() {
        double radius = 234.1235;
        assertEquals("Wrong circumference.", 2.0 * Math.PI * radius, calculator.circumference(ShapeType.CIRCLE, new double[]{radius}), DELTA);
        assertEquals("Wrong circumference.", 0.0, calculator.circumference(ShapeType.CIRCLE, new double[]{0.0}), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCircleCircumferenceNegative() {
        calculator.circumference(ShapeType.CIRCLE, new double[]{-1.0});
    }

    @Test
    public void testRectangleCircumference() {
        double width = 9.356;
        double height = 7.2235;
        assertEquals("Wrong circumference.", 2.0 * (width + height), calculator.circumference(ShapeType.RECTANGLE, new double[]{width, height}), DELTA);
        assertEquals("Wrong circumference.", 2.0 * (width + height), calculator.circumference(ShapeType.RECTANGLE, new double[]{height, width}), DELTA);
        assertEquals("Wrong circumference.", 2.0 * width, calculator.circumference(ShapeType.RECTANGLE, new double[]{width, 0.0}), DELTA);
        assertEquals("Wrong circumference.", 2.0 * height, calculator.circumference(ShapeType.RECTANGLE, new double[]{0.0, height}), DELTA);
        assertEquals("Wrong circumference.", 0.0, calculator.circumference(ShapeType.RECTANGLE, new double[]{0.0, 0.0}), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRectangleCircumferenceNegative1() {
        calculator.circumference(ShapeType.RECTANGLE, new double[]{-1.0, 1.0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRectangleCircumferenceNegative2() {
        calculator.circumference(ShapeType.RECTANGLE, new double[]{1.0, -1.0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRectangleCircumferenceNegative3() {
        calculator.circumference(ShapeType.RECTANGLE, new double[]{-1.0, -1.0});
    }

    @Test
    public void testSquareCircumference() {
        double size = 73.2342;
        assertEquals("Wrong circumference.", 4.0 * size, calculator.circumference(ShapeType.SQUARE, new double[]{size}), DELTA);
        assertEquals("Wrong circumference.", 0.0, calculator.circumference(ShapeType.SQUARE, new double[]{0.0}), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSquareCircumferenceNegative() {
        calculator.circumference(ShapeType.SQUARE, new double[]{-1.0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalShapeTypeArea() {
        calculator.area(null, new double[]{1.0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalShapeTypeCircumference() {
        calculator.circumference(null, new double[]{1.0});
    }
}