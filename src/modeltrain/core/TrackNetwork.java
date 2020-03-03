package modeltrain.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import modeltrain.trains.Train;

/**
 * Implements a Tracknetwork which holds all tracks and handles the moving of trains, deletion of tracks and so on
 * @author Florian Heck
 * @version 1.12
 */
public class TrackNetwork {

    private Map<Integer, Track> tracks;
    private Map<Point, Boolean> trackMap;
    private Map<Train, List<Point>> trainsOnTrack;
    private List<Integer> unsetSwitches;

    /**
     * Standard constructor, instanciates all attributes
     */
    public TrackNetwork() {
        tracks = new TreeMap<>();
        trackMap = new TreeMap<>();
        trainsOnTrack = new TreeMap<>();
        unsetSwitches = new ArrayList<>();
    }

    /**
     * Removes a train from the track
     * @param t the train to remove
     */
    public void deleteTrain(Train t) {
        if (trainsOnTrack.containsKey(t))
            trainsOnTrack.remove(t);
    }

    /**
     * Tries to remove a track from the trackmap. Can only succeed if the deletion won't create two seperate tracks
     * @param id The tracks id to remove
     * @return true if success, false if not
     */
    public boolean removeTrack(int id) {
        if (!tracks.containsKey(id)) {
            return false;
        } else {
            Track toRemove = tracks.get(id);
            for (List<Point> lp : trainsOnTrack.values()) {
                for (Point p : lp) {
                    if (toRemove.getPointsBetween().contains(p)) {
                        throw new SemanticsException("theres a train on the track");
                    }
                }
            }
            if (toRemove.getNextEnd() == null || toRemove.getNextStart() == null) {
                if (toRemove.getNextEnd() == null) {
                    trackMap.remove(toRemove.getEnd());
                }
                if (toRemove.getNextStart() == null) {
                    trackMap.remove(toRemove.getStart());
                }
                for (Point p : toRemove.getPointsBetween()) {
                    trackMap.remove(p);
                }
                return true;
            } else if (checkTracks(toRemove.getNextEnd(), toRemove.getNextStart(), toRemove, null)) {
                for (Point p : toRemove.getPointsBetween()) {
                    trackMap.remove(p);
                }
                return true;
            } else {
                throw new SemanticsException("removing track would create two seperate tracks");
            }
        }
    }

    //recursive algorithm to check if both ends of a track can still be reached after deletion
    private boolean checkTracks(Track toFind, Track current, Track prev, Set<Track> alreadyChecked) {
        if (current == null) {
            return false;
        }
        if (current.toString().charAt(0) == 's') {
            SwitchTrack st = (SwitchTrack) current;
            return checkTracks(toFind, st, prev, alreadyChecked);
        }
        if (alreadyChecked == null) {
            alreadyChecked = new HashSet<>();
        }
        if (current.equals(toFind)) {
            return true;
        } else if (prev == current.getNextStart() && current.getNextEnd() == null) {
            return false;
        } else if (prev == current.getNextEnd() && current.getNextStart() == null) {
            return false;
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

    //same as last one but with the switchtrack
    private boolean checkTracks(Track toFind, SwitchTrack current, Track prev, Set<Track> alreadyChecked) {
        if (alreadyChecked == null) {
            alreadyChecked = new HashSet<>();
        }
        if (current == null) {
            return false;
        }
        if (current.equals(toFind)) {
            return true;
        } else if (current.getNextEnd().equals(toFind) || current.getNextStart().equals(toFind)
                || current.getNextAltEnd().equals(toFind)) {
            return true;
        } else if (alreadyChecked.contains(current)) {
            return false;
        } else {
            alreadyChecked.add(current);
            if (prev.equals(current.getNextStart())) {
                return checkTracks(toFind, current.getNextEnd(), current, alreadyChecked)
                        || checkTracks(toFind, current.getNextAltEnd(), current, alreadyChecked);
            } else if (prev.equals(current.getNextEnd())) {
                return checkTracks(toFind, current.getNextAltEnd(), current, alreadyChecked)
                        || checkTracks(toFind, current.getNextStart(), current, alreadyChecked);
            } else if (prev.equals(current.getNextAltEnd())) {
                return checkTracks(toFind, current.getNextStart(), current, alreadyChecked)
                        || checkTracks(toFind, current.getNextEnd(), current, alreadyChecked);
            }
        }
        return false;
    }

    /**
     * Moves all trains by n steps forward or backwards.
     * 
     * @param n The amount of steps to make
     * @return a map of crashed trains ids mapped to true if they crashed into
     *         another train or false if they only derailed
     */
    public Map<Integer, Boolean> step(short n) {
        if (unsetSwitches.size() != 0) {
            throw new SemanticsException("not all switches have been set");
        }
        if (trainsOnTrack.size() == 0) {
            return null;
        }
        Map<Integer, Boolean> crashes = new HashMap<>();
        for (short i = 0; i < Math.abs(n); i++) {
            for (Train t : trainsOnTrack.keySet()) {
                if (n > 0) {
                    //going forwards
                    List<Point> current = trainsOnTrack.get(t);
                    for (int o = current.size() - 1; o > 0; o--) {
                        current.set(o, current.get(o - 1));
                    }
                    if (getNextPoint(current.get(0), t.getDirection()) == null) {
                        crashes.put(t.getId(), false);
                        trainsOnTrack.remove(t);
                    } else {
                        Tuple<Point, Point> next = getNextPoint(current.get(0), t.getDirection());
                        current.set(0, next.getFirst());
                        t.setDirection(next.getSecond());
                    }
                } else {
                    //going backwards
                    List<Point> current = trainsOnTrack.get(t);
                    for (int o = 0; o < current.size() - 1; o++) {
                        current.set(o, current.get(o + 1));
                    }
                    if (getNextPoint(current.get(current.size() - 1), t.getDirection().negate()) == null) {
                        crashes.put(t.getId(), false);
                        trainsOnTrack.remove(t);
                    } else {
                        Tuple<Point, Point> next = getNextPoint(current.get(current.size() - 1),
                                t.getDirection().negate());
                        current.set(0, next.getFirst());
                        t.setDirection(next.getSecond());
                    }
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
                            crashes.put(t.getId(), true);
                            crashes.put(k.getId(), true);
                        }
                    }
                }
            }
            for (Train t : removals)
                trainsOnTrack.remove(t);
        }
        return crashes;
    }

    /**
     * Switches a switchtracks switch to a point.
     * 
     * @param id   The track's id to switch
     * @param dest The Point to switch to
     */
    public void toggleSwitch(int id, Point dest) {
        if (!tracks.containsKey(id)) {
            throw new SemanticsException("no such track id");
        } else if (tracks.get(id).toString().charAt(0) != 's') {
            throw new SemanticsException("track with the id " + id + " is not a switch track");
        }
        SwitchTrack toSwitch = (SwitchTrack) tracks.get(id);
        if (dest.equals(toSwitch.getStart())) {
            throw new SemanticsException("the given point is not one to switch to");
        } else if (dest.equals(toSwitch.getEnd())) {
            for (Point p : toSwitch.getPointsBetweenEnd()) {
                trackMap.replace(p, true);
            }
            for (Point p : toSwitch.getPointsBetweenAltEnd()) {
                trackMap.replace(p, false);
            }
            unsetSwitches.remove((Integer) id);
        } else if (dest.equals(toSwitch.getAltEnd())) {
            for (Point p : toSwitch.getPointsBetweenEnd()) {
                trackMap.replace(p, false);
            }
            for (Point p : toSwitch.getPointsBetweenAltEnd()) {
                trackMap.replace(p, true);
            }
            unsetSwitches.remove((Integer) id);
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

    /*
     * Returns the next Point of a given point and a given direction as a Tuple,
     * where the first element is the new Point and the second element is the
     * direction that may have been updated.
     */
    private Tuple<Point, Point> getNextPoint(final Point p, final Point dir) {
        if (trackMap.get(p.add(dir)) != null && trackMap.get(p.add(dir))) {
            return new Tuple<Point, Point>(p.add(dir), dir);
            
        } else if (trackMap.get(p.add(dir.getLeft())) != null && trackMap.get(p.add(dir.getLeft()))) {
            return new Tuple<Point, Point>(p.add(dir.getLeft()), dir.getLeft());
            
        } else if (trackMap.get(p.add(dir.getRight())) != null && trackMap.get(p.add(dir.getRight()))) {
            return new Tuple<Point, Point>(p.add(dir.getRight()), dir.getRight());
            
        } else {
            return null;
        }
    }

    /**
     * Adds a track to the network
     * 
     * @param toAdd The track to add
     */
    public void addTrack(final Track toAdd) {
        if (trackMap.isEmpty()) {
            addPointsFromTrack(toAdd);
            tracks.put(toAdd.getId(), toAdd);
        } else {
            if (tracks.containsValue(toAdd)) {
                throw new SemanticsException("track already exists");
            }
            if (!findFitting(toAdd)) {
                throw new SemanticsException("none of the points exist already");
            } else {
                tracks.put(toAdd.getId(), toAdd);
            }
        }
    }

    /**
     * Finds the proper tracks from the already added ones to properly update their pointers
     * @param toAdd the track to add
     * @return true, if a fitting track has been found, false else
     */
    private boolean findFitting(Track toAdd) {
        for (Track tr : tracks.values()) {
            if (tr.getStart().equals(toAdd.getStart()) && tr.getNextStart() == null) {
                addPointsFromTrack(toAdd);
                tr.setNextStart(toAdd);
                toAdd.setNextStart(tr);
                return true;
            } else if (tr.getEnd().equals(toAdd.getStart()) && tr.getNextEnd() == null) {
                addPointsFromTrack(toAdd);
                tr.setNextEnd(toAdd);
                toAdd.setNextStart(tr);
                return true;
            } else if (tr.getStart().equals(toAdd.getEnd()) && tr.getNextStart() == null) {
                addPointsFromTrack(toAdd);
                tr.setNextStart(toAdd);
                toAdd.setNextEnd(tr);
                return true;
            } else if (tr.getEnd().equals(toAdd.getEnd()) && tr.getNextEnd() == null) {
                addPointsFromTrack(toAdd);
                tr.setNextEnd(toAdd);
                toAdd.setNextEnd(tr);
                return true;
            } else if (tr.getAltEnd() != null && tr.getStart().equals(toAdd.getAltEnd())
                    && tr.getNextAltEnd() == null) {
                addPointsFromTrack(toAdd);
                tr.setNextStart(toAdd);
                toAdd.setNextAltEnd(tr);
                return true;
            } else if (tr.getAltEnd() != null && tr.getEnd().equals(toAdd.getAltEnd()) && tr.getNextAltEnd() == null) {
                addPointsFromTrack(toAdd);
                tr.setNextEnd(toAdd);
                toAdd.setNextAltEnd(tr);
                return true;
            } else if (tr.getAltEnd() != null && tr.getAltEnd().equals(toAdd.getStart())) {
                addPointsFromTrack(toAdd);
                tr.setNextAltEnd(toAdd);
                toAdd.setNextStart(tr);
                return true;
            } else if (tr.getAltEnd() != null && tr.getAltEnd().equals(toAdd.getEnd())) {
                addPointsFromTrack(toAdd);
                ;
                tr.setNextAltEnd(toAdd);
                toAdd.setNextEnd(tr);
                return true;
            } else if (tr.getAltEnd() != null && tr.getAltEnd().equals(toAdd.getAltEnd())) {
                addPointsFromTrack(toAdd);
                tr.setNextAltEnd(toAdd);
                toAdd.setNextEnd(tr);
                return true;
            }
        }
        return false;
    }

    private void addPointsFromTrack(Track t) {
        if (t.toString().charAt(0) == 's') {
            SwitchTrack s = (SwitchTrack) t;
            addPointsFromTrack(s);
        }
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
        unsetSwitches.add(st.getId());
    }

    /**
     * Returns a sorted list of all tracks, sorted by id
     * @return list of all tracks
     */
    public List<Track> listTracks() {
        List<Track> ret = new ArrayList<>(tracks.values());
        Collections.sort(ret);
        return ret;
    }

    /**
     * Returns a map of all trains and their Points they stand on
     * @return Map of trains and their points
     */
    public Map<Train, List<Point>> getTrainsOnTrack() {
        return trainsOnTrack;
    }
}