package modeltrain.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modeltrain.trains.Train;

public class TrackNetworkTwo {

    private Map<Point, Boolean> trackMap;
    private Map<Train, List<Point>> trainsOnTrack;
    private Map<Integer, Track> tracks;

    public TrackNetworkTwo() {
        trackMap = new HashMap<>();
        trainsOnTrack = new HashMap<>();
    }

    public void putTrain(Train t, Point place, Point dir) {
        if (trackMap.get(place) == null || trackMap.get(place) == false) {
            throw new SemanticsException("no such point on the track");
        }
        Point newDir = dir.negate();
        List<Point> points = new ArrayList<>();
        points.add(place);
        for (int i = 1; i < t.getLength(); i++) {
            Tuple<Point> ret = getNextPoint(points.get(points.size() - 1), newDir);
            if (ret != null) {
                points.add(ret.getFirst());
                newDir = ret.getSecond();
            } else {
                throw new SemanticsException("not enough space on the track");
            }
        }
        trainsOnTrack.put(t, points);
    }

    /**
     * Returns the next Point of a given point and a given direction as a Tuple,
     * where the first element is the new Point and the second element is the
     * direction that may have been updated.
     * 
     * @param p   The point from which to get the next Point
     * @param dir The direction from which the next point is determined
     * @return The next point on the track and the direction from which it came
     */
    private Tuple<Point> getNextPoint(Point p, Point dir) {
        if (trackMap.get(p.add(dir)) != null) {
            return new Tuple<Point>(p.add(dir), dir);
        } else if (trackMap.get(p.add(getLeft(dir))) != null) {
            return new Tuple<Point>(p.add(getLeft(dir)), getLeft(dir));
        } else if (trackMap.get(p.add(getRight(dir))) != null) {
            return new Tuple<Point>(p.add(getRight(dir)), getRight(dir));
        } else {
            return null;
        }
    }

    public void addTrack(Track t) {
        if (trackMap.isEmpty()) {
            addPointsFromTrack(t);
            tracks.put(t.getId(), t);
        } else {
            if (trackMap.get(t.getStart()) == null && trackMap.get(t.getEnd()) == null
                    || trackMap.get(t.getStart()) == false && trackMap.get(t.getStart()) == false) {
                throw new SemanticsException("track contains none of the points from the track");
            } else {
                addPointsFromTrack(t);
                tracks.put(t.getId(), t);
            }
        }
    }

    public void addTrack(SwitchTrack st) {
        if (trackMap.isEmpty()) {
            addPointsFromTrack(st);
            tracks.put(st.getId(), st);
        } else {
            if (trackMap.get(st.getStart()) == null && trackMap.get(st.getEnd()) == null
                    && trackMap.get(st.getAltEnd()) == null) {
                throw new SemanticsException("none of the points are on the track");
            } else if (trackMap.get(st.getStart()) == false && trackMap.get(st.getEnd()) == false
                    && trackMap.get(st.getAltEnd()) == false) {
                throw new SemanticsException("none of the points are on the track");
            } else {
                addPointsFromTrack(st);
                tracks.put(st.getId(), st);
            }
        }
    }

    private void addPointsFromTrack(Track t) {
        trackMap.put(t.getStart(), true);
        trackMap.put(t.getEnd(), true);
        for (Point p : t.getPointsBetween()) {
            trackMap.put(p, false);
        }
    }

    private void addPointsFromTrack(SwitchTrack st) {
        trackMap.put(st.getStart(), true);
        trackMap.put(st.getEnd(), true);
        trackMap.put(st.getAltEnd(), true);
        for (Point p : st.getPointsBetween()) {
            trackMap.put(p, false);
        }
    }

    /**
     * Returns a point that is 90 degrees to the left of any given point (90°
     * anti-clockwise)
     * 
     * @param dir the point to rotate
     * @return The rotated point
     */
    private Point getLeft(Point dir) {
        return new Point(-dir.getYCord(), dir.getXCord());
    }

    /**
     * Returns a point that is rotated 90 degrees to the right (90° clockwise)
     * 
     * @param dir
     * @return
     */
    private Point getRight(Point dir) {
        return getLeft(dir).negate();
    }

}