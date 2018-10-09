package geometry;

import junit.framework.TestCase;

/**
 * Unit test of {@link GeometricCalculatorImpl}.
 *
 * @author Sindre Mehus
 */
public class GeometricCalculatorTestCase extends TestCase {

    private GeometricCalculator calculator;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        calculator = new GeometricCalculatorImpl();
    }

    public void testCircleArea() {
        double radius = 234.1235;
        assertEquals("Wrong area.", Math.PI * radius * radius, calculator.area(ShapeType.CIRCLE, new double[]{radius}));
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.CIRCLE, new double[]{0.0}));
    }

    public void testCircleAreaNegative() {
        try {
            calculator.area(ShapeType.CIRCLE, new double[] {-1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
    }

    public void testRectangleArea() {
        double width = 9.356;
        double height = 7.2235;
        assertEquals("Wrong area.", width * height, calculator.area(ShapeType.RECTANGLE, new double[]{width, height}));
        assertEquals("Wrong area.", width * height, calculator.area(ShapeType.RECTANGLE, new double[]{height, width}));
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.RECTANGLE, new double[]{width, 0.0}));
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.RECTANGLE, new double[]{0.0, height}));
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.RECTANGLE, new double[]{0.0, 0.0}));
    }

    public void testRectangleAreaNegative() {
        try {
            calculator.area(ShapeType.RECTANGLE, new double[]{-1.0, 1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
        try {
            calculator.area(ShapeType.RECTANGLE, new double[]{1.0, -1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
        try {
            calculator.area(ShapeType.RECTANGLE, new double[]{-1.0, -1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
    }

    public void testSquareArea() {
        double size = 73.2342;
        assertEquals("Wrong area.", size * size, calculator.area(ShapeType.SQUARE, new double[]{size}));
        assertEquals("Wrong area.", 0.0, calculator.area(ShapeType.SQUARE, new double[]{0.0}));
    }

    public void testSquareAreaNegative() {
        try {
            calculator.area(ShapeType.SQUARE, new double[]{-1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
    }

    public void testCircleCircumference() {
        double radius = 234.1235;
        assertEquals("Wrong circumference.", 2.0 * Math.PI * radius, calculator.circumference(ShapeType.CIRCLE, new double[]{radius}));
        assertEquals("Wrong circumference.", 0.0, calculator.circumference(ShapeType.CIRCLE, new double[]{0.0}));
    }

    public void testCircleCircumferenceNegative() {
        try {
            calculator.circumference(ShapeType.CIRCLE, new double[]{-1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
    }

    public void testRectangleCircumference() {
        double width = 9.356;
        double height = 7.2235;
        assertEquals("Wrong circumference.", 2.0 * (width + height), calculator.circumference(ShapeType.RECTANGLE, new double[]{width, height}));
        assertEquals("Wrong circumference.", 2.0 * (width + height), calculator.circumference(ShapeType.RECTANGLE, new double[]{height, width}));
        assertEquals("Wrong circumference.", 2.0 * width, calculator.circumference(ShapeType.RECTANGLE, new double[]{width, 0.0}));
        assertEquals("Wrong circumference.", 2.0 * height, calculator.circumference(ShapeType.RECTANGLE, new double[]{0.0, height}));
        assertEquals("Wrong circumference.", 0.0, calculator.circumference(ShapeType.RECTANGLE, new double[]{0.0, 0.0}));
    }

    public void testRectangleCircumferenceNegative() {
        try {
            calculator.circumference(ShapeType.RECTANGLE, new double[]{-1.0, 1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
        try {
            calculator.circumference(ShapeType.RECTANGLE, new double[]{1.0, -1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
        try {
            calculator.circumference(ShapeType.RECTANGLE, new double[]{-1.0, -1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
    }

    public void testSquareCircumference() {
        double size = 73.2342;
        assertEquals("Wrong circumference.", 4.0 * size, calculator.circumference(ShapeType.SQUARE, new double[]{size}));
        assertEquals("Wrong circumference.", 0.0, calculator.circumference(ShapeType.SQUARE, new double[]{0.0}));
    }

    public void testSquareCircumferenceNegative() {
        try {
            calculator.circumference(ShapeType.SQUARE, new double[]{-1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
    }

    public void testIllegalShapeTypeArea() {
        try {
            calculator.area(null, new double[]{1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
    }

    public void testIllegalShapeTypeCircumference() {
        try {
            calculator.circumference(null, new double[]{1.0});
            fail("Expected exception.");
        } catch (IllegalArgumentException x) {
        }
    }
}