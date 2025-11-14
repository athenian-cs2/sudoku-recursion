public class Shape {
    private int area;

    public Shape(int area) {
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    public int howCoolIsThisShape() {
        // This is by definition an average shape
        return 5;
    }
}
