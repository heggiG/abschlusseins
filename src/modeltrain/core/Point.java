package modeltrain.core;

import java.util.HashSet;
import java.util.Set;

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

    public int lengthBetweenPoints(Point p) {
        int diffX = p.xCord - this.xCord;
        int diffY = p.yCord - this.yCord;
        return (int) Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }

    public Set<Point> pointsBetween(Point p) {
        Set<Point> ret = new HashSet<>();
        Point adder = p.sub(this).reduce();
        for (int i = 0; i < this.lengthBetweenPoints(p); i++) {
            ret.add(this.add(adder.scale(i)));
        }
        return ret;
    }

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
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(xCord);
        sb.append(",");
        sb.append(yCord);
        sb.append(")");
        return sb.toString();
    }

}
