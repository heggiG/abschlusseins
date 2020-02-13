package modeltrain.core;

/**
 * Implements a standard Point in the way of a coordinate
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class Point {

    private int xCord;
    private int yCord;

    public Point(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public int getXCord() {
        return this.xCord;
    }

    public int getYCord() {
        return this.yCord;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o.getClass().equals(this.getClass())))
            return false;
        if (o.toString().equals(this.toString()))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "(" + xCord + "),(" + yCord + ")";
    }

}
