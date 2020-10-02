package Proj;

import java.util.ArrayList;

public class Landmark
{
    public String name;
    public double xCoord, yCoord;
    public int cultureVA1;

    //not sure if landmark arrayList is needed
    //public static ArrayList<Landmark> landmarkArrayList = new ArrayList<>();

    Landmark(String name, double xCoord, double yCoord, int cultureVA1)
    {
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.cultureVA1 = cultureVA1;
        //landmarkArrayList.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public int getCultureVA1() {
        return cultureVA1;
    }

    public void setCultureVA1(int cultureVA1) {
        this.cultureVA1 = cultureVA1;
    }

    @Override
    public String toString() {
        return name;
    }
}
