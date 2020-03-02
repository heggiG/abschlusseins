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

    private final Point altEnd;
    private Point currentSwitch;
    private Track nextAltEnd;

    /**
     * Constructor that sets the switchtracks start, end and alternative end
     * @param start The switchtracks start
     * @param end The switchtracks end
     * @param altEnd The switchtracks alternative end
     * @param id The switchtracks id
     */
    public SwitchTrack(Point start, Point end, Point altEnd, int id) {
        super(start, end, id);
        this.altEnd = altEnd;
        currentSwitch = end;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Point> getPointsBetween() {
        Set<Point> ret = new HashSet<>();
        ret.addAll(getPointsBetweenEnd());
        ret.addAll(getPointsBetweenAltEnd());
        return ret;
    }
    
    /**
     * Returns a set containing all points between start and end
     * @return A set with all point between start and end
     */
    public Set<Point> getPointsBetweenEnd() {
        Set<Point> ret = new HashSet<>();
        Point adder = end.sub(start).reduce();
        for (int i = 1; i < start.lengthBetweenPoints(end); i++) {
            ret.add(start.add(adder.scale(i)));
        }
        return ret;
    }
    
    /**
     * Returns a set with all points between start and alternative end
     * @return A set with all points between start and alternative end
     */
    public Set<Point> getPointsBetweenAltEnd() {
        Set<Point> ret = new HashSet<>();
        Point adder = altEnd.sub(start).reduce();
        for (int i = 1; i < start.lengthBetweenPoints(altEnd); i++) {
            ret.add(start.add(adder.scale(i)));
        }
        return ret;
    }

    /**
     * Returns the alternative end of the Switchtrack
     * @return The alternative end
     */
    @Override
    public Point getAltEnd() {
        return altEnd;
    }

    /**
     * Toggles between the endpoints and returns the newly selected one
     * @return The now selected end point
     */
    public Point toggle() {
        if (currentSwitch.equals(end))
            currentSwitch = altEnd;
        else
            currentSwitch = end;
        return currentSwitch;
    }

    /**
     * Returns the track after the alternative end
     * @return the track after the alternative end
     */
    public Track getNextAltEnd() {
        return nextAltEnd;
    }

    /**
     * Sets the track after the alternative end
     * @param nextAltEnd the track that follows the alternative end
     */
    @Override
    public void setNextAltEnd(Track nextAltEnd) {
        this.nextAltEnd = nextAltEnd;
    }

    /**
     * {@inheritDoc} between the start and the currently selected switch
     */
    @Override
    public int getLength() {
        int xLength = start.getXCord() - currentSwitch.getXCord();
        int yLength = start.getYCord() - currentSwitch.getYCord();
        return (int) Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
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
        SwitchTrack st = (SwitchTrack) o;
        if (st.start.equals(this.start) && st.altEnd.equals(this.altEnd) && st.end.equals(this.end))
            return true;
        else if (st.id == this.id)
            return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
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
