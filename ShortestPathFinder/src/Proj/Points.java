package Proj;

public class Points
{
    int x;

    public Points(int x) {
        this.x = x;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    @Override
    public String toString() {
        return "Points{" +
                "x=" + x +
                '}';
    }
}
