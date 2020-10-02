package Proj;

public class Waypoint
{
    private double x, y; //x and y coord of waypoint

    Waypoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX() {     return x; }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
