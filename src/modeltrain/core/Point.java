package modeltrain.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a standard Point in the sense of a coordinate. As per the task
 * this point works in the integer number space but in a general use case
 * floating point numbers would be more usefull.
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class Point implements Comparable<Point> {

    private final int xCord;
    private final int yCord;

    /**
     * Constructor that sets up the Points x and y coordinates
     * 
     * @param xCord The points x coordinate
     * @param yCord The points y coordinate
     */
    public Point(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    /**
     * Returns the points x coordinate
     * 
     * @return The points x coordinate
     */
    public int getXCord() {
        return this.xCord;
    }

    /**
     * Returns the points y coordinate
     * 
     * @return The points y coordinate
     */
    public int getYCord() {
        return this.yCord;
    }

    /**
     * Returns the sum of this Point to another (see vector addition)
     * 
     * @param p The point to add
     * @return The sum of both points
     */
    public Point add(Point p) {
        return new Point(p.xCord + this.xCord, p.yCord + this.yCord);
    }

    /**
     * Returns the difference of this Point to another (see vector subtraction)
     * 
     * @param p The point to subtract
     * @return
     */
    public Point sub(Point p) {
        return new Point(this.xCord - p.xCord, this.yCord - p.yCord);
    }

    /**
     * Scales the point by an integer n (see vector scaling)
     * 
     * @param n
     * @return
     */
    public Point scale(int n) {
        return new Point(this.xCord * n, this.yCord * n);
    }

    /**
     * Returns the negative of a point (scaling by -1)
     * 
     * @return The negative of a point
     */
    public Point negate() {
        return new Point(-this.xCord, -this.yCord);
    }

    /**
     * Reduces a point to be only consinsten of 0s and 1s, usefull for train
     * direction
     * 
     * @return The reduced point
     */
    public Point reduce() {
        int reducedX = this.xCord == 0 ? 0 : this.xCord / Math.abs(this.xCord);
        int reducedY = this.yCord == 0 ? 0 : this.yCord / Math.abs(this.yCord);
        return new Point(reducedX, reducedY);
    }

    /**
     * Returns the integer length between two points. Here also a floating point
     * value would be more usefull, but since the task is only operating in the
     * integer number space and without diagonal tracks i don't care
     * 
     * @param p The other point from which to determine the length between
     * @return The integer length
     */
    public int lengthBetweenPoints(Point p) {
        int diffX = p.xCord - this.xCord;
        int diffY = p.yCord - this.yCord;
        return (int) Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }

    /**
     * Returns this point rotated 90° to the left around the cartesian origin
     * 
     * @return The left rotated point
     */
    public Point getLeft() {
        return new Point(-yCord, xCord);
    }

    /**
     * Returns this point rotated 90° to the right around the origin
     * 
     * @return The right rotated point
     */
    public Point getRight() {
        return getLeft().negate();
    }

    /**
     * Returns a set of all integer valued points between this point and another
     * given point
     * 
     * @param p The point from which to determine the others
     * @return A set of points between this and p
     */
    public Set<Point> pointsBetween(Point p) {
        Set<Point> ret = new HashSet<>();
        Point adder = p.sub(this).reduce();
        for (int i = 0; i < this.lengthBetweenPoints(p); i++) {
            ret.add(this.add(adder.scale(i)));
        }
        return ret;
    }

    @Override
    public int compareTo(Point o) {
        return (this.xCord - o.xCord) - (this.yCord - o.yCord);
    }

    /**
     * I need to override hashcode so i can use the java.util Hashmap. this method
     * would have worked perfectly if point coordinates could only be from the short
     * number space. But since the hashmap maps higher ordered bits to lower ones i
     * have not encountered an error yet. It's not perfect, but who is?
     */
    @Override
    public int hashCode() {
        return (xCord << 16) + yCord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o.getClass() != this.getClass())
            return false;
        Point p = (Point) o;
        if (p.xCord == this.xCord && p.yCord == this.yCord)
            return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
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
