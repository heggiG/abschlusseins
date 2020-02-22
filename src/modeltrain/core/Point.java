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
    
    public Point add(Point p) {
        return new Point(p.xCord + this.xCord, p.yCord + this.yCord);
    }
    
    public Point sub(Point p) {
        return new Point(this.xCord - p.xCord, this.yCord - p.yCord);
    }
    
    public Point scale(int n) {
        return new Point(this.xCord * n, this.yCord * n);
    }
    
    public Point negate() {
        return new Point(-this.xCord, -this.yCord);
    }
    
    public Point reduce() {
        int reducedX = this.xCord == 0 ? 0 : this.xCord / Math.abs(this.xCord);
        int reducedY = this.yCord == 0 ? 0 : this.yCord / Math.abs(this.yCord);
        return new Point(reducedX, reducedY);
    }
    
    public int lengthBetweenPoints(Point p1) {
        int diffX = p1.xCord - this.xCord;
        int diffY = p1.yCord - this.yCord;
        return (int) Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }
    
//    public int getLength() {
//        return (int) Math.sqrt(Math.pow(xCord, 2) + Math.pow(yCord, 2));
//    }

    @Override
    public int hashCode() {
        return (xCord << 16) + yCord;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass())
            return false;
        Point p = (Point) o;
        if (p.xCord == this.xCord && p.yCord == this.yCord)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "(" + xCord + "),(" + yCord + ")";
    }

}
