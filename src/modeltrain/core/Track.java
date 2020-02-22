package modeltrain.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a standard track with a start and an end point.
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class Track {
    
    protected Point start;
    protected Point end;
    protected int id;
    
    public Track(Point start, Point end, int id) {
        this.start = start;
        this.end = end;
        this.id = id;
    }
    
    public Point getStart() {
        return start;
    }
    
    public Point getEnd() {
        return end;
    }
    
    public Point getOtherPoint(Point po) {
        if(po.equals(start))
            return end;
        else if(po.equals(end))
            return start;
        else
            throw new SemanticsException("point is not part of the track");
    }
    
    public int getLength() {
     int xLength = start.getXCord() - end.getXCord();
     int yLength = start.getYCord() - end.getYCord();
     return xLength != 0 ? Math.abs(xLength) : Math.abs(yLength);
    }
    
    public int getId() {
        return id;
    }
    
    public Set<Point> getPointsBetween() {
        Set<Point> ret = new HashSet<>();
        Point adder = end.sub(start).reduce();
        for (int i = 1; i < start.lengthBetweenPoints(end); i++) {
            ret.add(start.add(adder.scale(i)));
        }
        return ret;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass())
            return false;
        Track t = (Track) o;
        if (t.start.equals(this.start) && t.end.equals(this.end))
            return true;
        return false;        
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("t ");
        sb.append(id);
        sb.append(" ");
        sb.append(start);
        sb.append(" -> ");
        sb.append(end);
        sb.append(" ");
        sb.append(getLength());
        return sb.toString();
    }
}
