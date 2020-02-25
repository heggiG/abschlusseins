package modeltrain.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modeltrain.trains.Train;

public class Model {

    private Map<Integer, Track> tracks;
    private Map<Integer, Train> trainsNotOnTrack;
    private Map<Point, Boolean> trackMap;
    private Map<Train, List<Point>> trainsOnTrack;

    public Model() {
        tracks = new HashMap<>();
        trackMap = new HashMap<>();
        trainsOnTrack = new HashMap<>();
        trainsNotOnTrack = new HashMap<>();
    }

    private void removeTrack(int id) {
        if (!tracks.containsKey(id)) {
            throw new SemanticsException("no such track id");
        } else {
            Track toRemove = tracks.get(id);
            boolean legalOperation;
            try {
                SwitchTrack st = (SwitchTrack) toRemove;
//               legalOperation = checkTracks(toRemove.getNextEnd(), toRemove.getNextStart());
            } catch (ClassCastException e) {

            }
        }

    }

    private boolean checkTracks(Track toFind, Track current, Track prev, Set<Track> alreadyChecked) {
        if (alreadyChecked == null) {
            alreadyChecked = new HashSet<>();
        }
        if (current.equals(toFind)) {
            return true;
        } else if (current.getNextEnd().equals(toFind) || current.getNextStart().equals(toFind)) {
            return true;
        } else {
            alreadyChecked.add(current);
            if (prev.equals(current.getNextEnd())) {
                if (alreadyChecked.contains(current.getNextStart())) {
                    return false;
                }
                return checkTracks(toFind, current.getNextStart(), current, alreadyChecked);
            } else if (prev.equals(current.getNextStart())) {
                if (alreadyChecked.contains(current.getNextEnd())) {
                    return false;
                }
                return checkTracks(toFind, current.getNextEnd(), current, alreadyChecked);
            }
        }
        return false;
    }
    
    private boolean checkTracks(Track toFind, SwitchTrack current, Track prev, Set<Track> alreadyChecked) {
        if (alreadyChecked == null) {
            alreadyChecked = new HashSet<>();
        }
        if (current.equals(toFind)) {
            return true;
        } else if (current.getNextEnd().equals(toFind) || current.getNextStart().equals(toFind)) {
                        
        }
        return false;
    }

    public Tuple<Set<Tuple<Integer, Point>>, Set<Integer>> step(short n) {
        if (trainsOnTrack.size() == 0) {
            return null;
        }
        Set<Tuple<Integer, Point>> locations = new HashSet<>();
        Set<Integer> crashes = new HashSet<>();
        for (short i = 0; i < n; i++) {
            for (Train t : trainsOnTrack.keySet()) {
                List<Point> current = trainsOnTrack.get(t);
                for (int o = current.size() - 1; o > 0; o--) {
                    current.set(o, current.get(o - 1));
                }
                if (getNextPoint(current.get(0), t.getDirection()) == null) {
                    crashes.add(t.getId());
                    trainsNotOnTrack.put(t.getId(), t);
                    trainsOnTrack.remove(t);
                    continue;
                } else {
                    Tuple<Point, Point> next = getNextPoint(current.get(0), t.getDirection());
                    current.set(0, next.getFirst());
                    t.setDirection(next.getSecond());
                }
            }
            // needed to avoid concurrent modification exception
            Set<Train> removals = new HashSet<>();
            for (Train t : trainsOnTrack.keySet()) {
                for (Train k : trainsOnTrack.keySet()) {
                    if (t != k) {
                        if (trainsOnTrack.get(k).contains(trainsOnTrack.get(t).get(0))) {
                            removals.add(t);
                            removals.add(k);
                            crashes.add(t.getId());
                            crashes.add(k.getId());
                        }
                    }
                }
            }

            for (Train t : removals)
                trainsOnTrack.remove(t);
        }
        for (Train t : trainsOnTrack.keySet()) {
            locations.add(new Tuple<>(t.getId(), trainsOnTrack.get(t).get(0)));
        }
        return new Tuple<Set<Tuple<Integer, Point>>, Set<Integer>>(locations, crashes);
    }

    public void switchSwitch(int id, Point dest) {
        if (!tracks.containsKey(id)) {
            throw new SemanticsException("no such track id");
        }
        SwitchTrack toSwitch;
        try {
            toSwitch = (SwitchTrack) tracks.get(id);
        } catch (ClassCastException e) {
            throw new SemanticsException("the track with the id " + id + " is not a switchtrack");
        }
        if (dest.equals(toSwitch.getStart())) {
            throw new SemanticsException("the given point is not one to switch to");
        } else if (dest.equals(toSwitch.getEnd())) {
            for (Point p : toSwitch.pointsBetweenEnd()) {
                trackMap.replace(p, true);
            }
            for (Point p : toSwitch.pointsBetweenAltEnd()) {
                trackMap.replace(p, false);
            }
        } else if (dest.equals(toSwitch.getAltEnd())) {
            for (Point p : toSwitch.pointsBetweenEnd()) {
                trackMap.replace(p, false);
            }
            for (Point p : toSwitch.pointsBetweenAltEnd()) {
                trackMap.replace(p, true);
            }
        } else {
            throw new SemanticsException("an unknown error occurred");
        }
    }

    /**
     * Places a train on a track.
     * 
     * @param t     The train to place
     * @param place The point to put the train on
     * @param dir   The dierction the train is heading
     */
    public void putTrain(Train t, Point place, Point dir) {
        if (!t.isValid()) {
            throw new SemanticsException("train is not valid");
        }
        if (trackMap.get(place) == null) {
            throw new SemanticsException("no such point on the track");
        }
        Point newDir = dir.negate();
        List<Point> points = new ArrayList<>();
        points.add(place);
        for (int i = 1; i < t.getLength(); i++) {
            Tuple<Point, Point> ret = getNextPoint(points.get(points.size() - 1), newDir);
            if (ret != null) {
                points.add(ret.getFirst());
                newDir = ret.getSecond();
            } else {
                throw new SemanticsException("not enough space on the track");
            }
        }
        t.setDirection(dir);
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
    private Tuple<Point, Point> getNextPoint(Point p, Point dir) {
        if (trackMap.get(p.add(dir))) {
            return new Tuple<Point, Point>(p.add(dir), dir);
        } else if (trackMap.get(p.add(getLeft(dir)))) {
            return new Tuple<Point, Point>(p.add(getLeft(dir)), getLeft(dir));
        } else if (trackMap.get(p.add(getRight(dir)))) {
            return new Tuple<Point, Point>(p.add(getRight(dir)), getRight(dir));
        } else {
            return null;
        }
    }

    /**
     * Adds a track to the network
     * 
     * @param t The track to add
     */
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
                for (Integer id : tracks.keySet()) {
                    if (tracks.get(id).getEnd().equals(t.getStart())) {
                        t.setNextStart(tracks.get(id));
                        tracks.get(id).setNextEnd(t);
                    } else if (tracks.get(id).getStart().equals(t.getStart())) {
                        t.setNextStart(tracks.get(id));
                        tracks.get(id).setNextStart(t);
                    } else if (tracks.get(id).getEnd().equals(t.getEnd())) {
                        t.setNextEnd(tracks.get(id));
                        tracks.get(id).setNextEnd(t);
                    } else if (tracks.get(id).getStart().equals(t.getEnd())) {
                        t.setNextEnd(tracks.get(id));
                        tracks.get(id).setNextStart(t);
                    }
                }
            }
        }
    }

    /**
     * Adds a switch track to the network
     * 
     * @param st The switchtrack to add
     */
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

    /**
     * Adds the Track ends and the points between ends
     * 
     * @param t The track from which to add points
     */
    private void addPointsFromTrack(Track t) {
        trackMap.put(t.getStart(), true);
        trackMap.put(t.getEnd(), true);
        for (Point p : t.getPointsBetween()) {
            trackMap.put(p, true);
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