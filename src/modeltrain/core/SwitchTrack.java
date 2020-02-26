package modeltrain.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a switch track as an extension of the normal track.
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class SwitchTrack extends Track {

    private Point altEnd;
    private Point currentSwitch;
    private Track nextAltEnd;

    public SwitchTrack(Point start, Point end, Point altEnd, int id) {
        super(start, end, id);
        this.altEnd = altEnd;
        currentSwitch = end;
    }

    @Override
    public Set<Point> getPointsBetween() {
        Set<Point> ret = new HashSet<>();
        ret.addAll(pointsBetweenEnd());
        ret.addAll(pointsBetweenAltEnd());
        return ret;
    }
    
    public Set<Point> pointsBetweenEnd() {
        Set<Point> ret = new HashSet<>();
        Point adder = end.sub(start).reduce();
        for (int i = 1; i < start.lengthBetweenPoints(end); i++) {
            ret.add(start.add(adder.scale(i)));
        }
        return ret;
    }
    
    public Set<Point> pointsBetweenAltEnd() {
        Set<Point> ret = new HashSet<>();
        Point adder = altEnd.sub(start).reduce();
        for (int i = 1; i < start.lengthBetweenPoints(altEnd); i++) {
            ret.add(start.add(adder.scale(i)));
        }
        return ret;
    }

    public Point getAltEnd() {
        return altEnd;
    }

    public Point toggle() {
        if (currentSwitch.equals(end))
            currentSwitch = altEnd;
        else
            currentSwitch = end;
        return currentSwitch;
    }

    public Track getNextAltEnd() {
        return nextAltEnd;
    }

    public void setNextAltEnd(Track nextAltEnd) {
        this.nextAltEnd = nextAltEnd;
    }

    @Override
    public Point getOtherPoint(Point po) {
        if (po.equals(start))
            return currentSwitch;
        else if (po.equals(currentSwitch))
            return start;
        else
            throw new IllegalStateException("Point is not part of the track/switch is in wrong position");
    }

    @Override
    public int getLength() {
        int xLength = start.getXCord() - currentSwitch.getXCord();
        int yLength = start.getYCord() - currentSwitch.getYCord();
        return (int) Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass())
            return false;
        SwitchTrack st = (SwitchTrack) o;
        if (st.start.equals(this.start) && st.altEnd.equals(this.altEnd) && st.end.equals(this.end))
            return true;
        else if (st.id == this.id)
            return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("s ");
        sb.append(id);
        sb.append(" ");
        sb.append(start);
        sb.append(" -> ");
        sb.append(end);
        sb.append(",");
        sb.append(altEnd);
        sb.append(" ");
        sb.append(getLength());
        return sb.toString();
    }
}
