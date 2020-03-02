package modeltrain.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a standard track with a start and an end point.
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class Track implements Comparable<Track> {

    private Track nextStart;
    private Track nextEnd;
    protected final Point start;
    protected final Point end;
    protected final int id;

    /**
     * Constructs a track and sets start, and id
     * @param start Tracks start
     * @param end Tracks end
     * @param id Tracks id
     */
    public Track(Point start, Point end, int id) {
        this.start = start;
        this.end = end;
        this.id = id;
    }

    /**
     * Returns the Tracks start Point
     * @return The tracks start
     */
    public Point getStart() {
        return start;
    }

    /**
     * Returns the tracks end Point
     * @return The tracks end
     */
    public Point getEnd() {
        return end;
    }

    /**
     * Returns the Tracks alternative end (here null since this is a normal track, not a switch)
     * @return Null
     */
    public Point getAltEnd() {
        return null;
    }

    /**
     * Returns the track that follows the alternative end (here null since this is no switchtrack)
     * @return Null
     */
    public Track getNextAltEnd() {
        return null;
    }

    /**
     * Sets the next track following the alternative end (does nothing here since its a normal track)
     * @param t The track to set
     */
    public void setNextAltEnd(Track t) {
        // do nothing
    }

    /**
     * Returns the tracks length (see pythagoras) rounded down to the next integer
     * @return The tracks length as integer
     */
    public int getLength() {
        int xLength = start.getXCord() - end.getXCord();
        int yLength = start.getYCord() - end.getYCord();
        return (int) Math.round(Math.sqrt(xLength * xLength + yLength * yLength));
    }

    /**
     * Returns the tracks id
     * @return the tracks id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a set of Points containing all points between start and end
     * @return All points between start and end
     */
    public Set<Point> getPointsBetween() {
        Set<Point> ret = new HashSet<>();
        Point adder = end.sub(start).reduce();
        for (int i = 1; i < start.lengthBetweenPoints(end); i++) {
            ret.add(start.add(adder.scale(i)));
        }
        return ret;
    }

    /**
     * Returns the track which follows the start point
     * @return The next track after the start point
     */
    public Track getNextStart() {
        return nextStart;
    }

    /**
     * Sets the Track after the start point
     * @param t The track to set
     */
    public void setNextStart(Track t) {
        nextStart = t;
    }

    /**
     * Returns the track after the end Point
     * @return The track after the end Point
     */
    public Track getNextEnd() {
        return nextEnd;
    }

    /**
     * Sets the track after the end point
     * @param t The track to set
     */
    public void setNextEnd(Track t) {
        nextEnd = t;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Track o) {
        return this.id - o.id;
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
        Track t = (Track) o;
        if (t.start.equals(this.start) && t.end.equals(this.end))
            return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (start.hashCode() + end.hashCode()) / 2;
    }

    /**
     * {@inheritDoc}
     */
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