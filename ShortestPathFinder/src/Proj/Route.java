package Proj;

public class Route
{
    public Waypoint start, end;
    public double length = 0.0;

    Route(Waypoint start, Waypoint end, double length)
    {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    //getters
    public Waypoint getStart() {
        return start;
    }
    public Waypoint getEnd() {
        return end;
    }
    public double getLength() {
        return length;
    }

    //setters
    public void setStart(Waypoint start) {
        this.start = start;
    }
    public void setEnd(Waypoint end) {
        this.end = end;
    }
    public void setLength(double length) {
        this.length = length;
    }
}
