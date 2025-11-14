import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class MyTests {

    // Shape tests
    @Test
    public void testShapeConstructorAndGetArea() {
        Shape shape = new Shape(100);
        assertEquals(100, shape.getArea());
    }

    @Test
    public void testShapeHowCool() {
        Shape shape = new Shape(50);
        assertEquals(5, shape.howCoolIsThisShape());
    }

    // Rectangle tests
    @Test
    public void testRectangleConstructor() {
        Rectangle rect = new Rectangle(5, 10);
        assertEquals(50, rect.getArea());
    }

    @Test
    public void testRectangleGetters() {
        Rectangle rect = new Rectangle(7, 8);
        assertEquals(7, rect.getWidth());
        assertEquals(8, rect.getHeight());
    }

    @Test
    public void testRectangleSetters() {
        Rectangle rect = new Rectangle(5, 10);
        rect.setWidth(6);
        rect.setHeight(12);
        assertEquals(6, rect.getWidth());
        assertEquals(12, rect.getHeight());
        assertEquals(72, rect.getArea());
    }

    @Test
    public void testRectangleHowCool() {
        Rectangle rect = new Rectangle(4, 5);
        assertEquals(7, rect.howCoolIsThisShape());
    }

    @Test
    public void testRectangleWithZeroDimensions() {
        Rectangle rect = new Rectangle(0, 0);
        assertEquals(0, rect.getArea());
    }

    // Square tests
    @Test
    public void testSquareConstructor() {
        Square square = new Square(5);
        assertEquals(25, square.getArea());
    }

    @Test
    public void testSquareHowCool() {
        Square square = new Square(4);
        assertEquals(10, square.howCoolIsThisShape());
    }

    @Test
    public void testSquareIsRectangle() {
        Square square = new Square(6);
        assertEquals(6, square.getWidth());
        assertEquals(6, square.getHeight());
    }

    @Test
    public void testSquareWithZeroSide() {
        Square square = new Square(0);
        assertEquals(0, square.getArea());
    }

    // ArrayListPractice tests
    @Test
    public void testCombineValuesEmpty() {
        ArrayList<Integer> list = new ArrayList<>();
        assertEquals(0, ArrayListPractice.combineValues(list));
    }

    @Test
    public void testCombineValuesSingle() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);
        assertEquals(5, ArrayListPractice.combineValues(list));
    }

    @Test
    public void testCombineValuesMultiple() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        assertEquals(10, ArrayListPractice.combineValues(list));
    }

    @Test
    public void testCombineValuesNegative() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(-5);
        list.add(10);
        list.add(-3);
        assertEquals(2, ArrayListPractice.combineValues(list));
    }

    @Test
    public void testMakeList() {
        ArrayList<String> result = ArrayListPractice.makeList("apple", "banana", "cherry");
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("apple", result.get(0));
        assertEquals("banana", result.get(1));
        assertEquals("cherry", result.get(2));
    }

    @Test
    public void testInterweaveEmpty() {
        ArrayList<Integer> list = new ArrayList<>();
        int[] array = new int[0];
        ArrayList<Integer> result = ArrayListPractice.interweave(list, array);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testInterweaveSingle() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        int[] array = {2};
        ArrayList<Integer> result = ArrayListPractice.interweave(list, array);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
    }

    @Test
    public void testInterweaveMultiple() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        int[] array = {2, 4, 6};
        ArrayList<Integer> result = ArrayListPractice.interweave(list, array);
        assertNotNull(result);
        assertEquals(6, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
        assertEquals(4, result.get(3));
        assertEquals(5, result.get(4));
        assertEquals(6, result.get(5));
    }

    @Test
    public void testInterweaveLonger() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        int[] array = {15, 25, 35, 45};
        ArrayList<Integer> result = ArrayListPractice.interweave(list, array);
        assertNotNull(result);
        assertEquals(8, result.size());
        assertEquals(10, result.get(0));
        assertEquals(15, result.get(1));
        assertEquals(20, result.get(2));
        assertEquals(25, result.get(3));
        assertEquals(30, result.get(4));
        assertEquals(35, result.get(5));
        assertEquals(40, result.get(6));
        assertEquals(45, result.get(7));
    }
}
