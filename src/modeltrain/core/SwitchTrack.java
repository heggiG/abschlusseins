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
        Point adder = getEnd().sub(getStart()).reduce();
        for (int i = 1; i < getStart().lengthBetweenPoints(getEnd()); i++) {
            ret.add(getStart().add(adder.scale(i)));
        }
        return ret;
    }
    
    /**
     * Returns a set with all points between start and alternative end
     * @return A set with all points between start and alternative end
     */
    public Set<Point> getPointsBetweenAltEnd() {
        Set<Point> ret = new HashSet<>();
        Point adder = altEnd.sub(getStart()).reduce();
        for (int i = 1; i < getStart().lengthBetweenPoints(altEnd); i++) {
            ret.add(getStart().add(adder.scale(i)));
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
     * 
     * @param p the point to set to
     * @return the point that has been set to or null
     */
    public Point setSwitch(Point p) {
        if (getStart().equals(p)) {
            return null;
        } else if (getEnd().equals(p)) {
            currentSwitch = getEnd();
        } else if (altEnd.equals(p)) {
            currentSwitch = altEnd;
        }
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
    public long getLength() {
        int xLength = getStart().getXCord() - currentSwitch.getXCord();
        int yLength = getStart().getYCord() - currentSwitch.getYCord();
        return (long) Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        SwitchTrack st = (SwitchTrack) o;
        if (st.getStart().equals(this.getStart()) && st.altEnd.equals(this.altEnd) && st.getEnd().equals(this.getEnd()))
            return true;
        else if (st.getId() == this.getId())
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
        sb.append(getId());
        sb.append(" ");
        sb.append(getStart());
        sb.append(" -> ");
        sb.append(getEnd());
        sb.append(",");
        sb.append(altEnd);
        sb.append(" ");
        sb.append(getLength());
        return sb.toString();
    }
}
