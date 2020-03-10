package modeltrain.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import modeltrain.trains.Train;

/**
 * Implements a Tracknetwork which holds all tracks and handles the moving of
 * trains, deletion of tracks and so on
 * 
 * @author Florian Heck
 * @version 1.12
 */
public class TrackNetwork {

    private Map<Integer, Track> tracks;
    // maps points to boolean whether they can be driven on or not
    private Map<Point, Boolean> trackMap;
    // maps each train to a list of points it stands on
    private Map<Train, List<Point>> trainsOnTrack;
    private List<Integer> unsetSwitches;

    /**
     * Standard constructor, instanciates all attributes
     */
    public TrackNetwork() {
        tracks = new TreeMap<>();
        // didn't work with treemap so hashmap it is
        trackMap = new HashMap<>();
        trainsOnTrack = new TreeMap<>();
        unsetSwitches = new ArrayList<>();
    }

    /**
     * Removes a train from the track
     * 
     * @param t the train to remove
     */
    public void removeTrain(Train t) {
        if (trainsOnTrack.containsKey(t))
            trainsOnTrack.remove(t);
    }

    /**
     * Tries to remove a track from the trackmap. Can only succeed if the deletion
     * won't create two seperate tracks
     * 
     * @param id The tracks id to remove
     * @return true if success, false if not
     * @throws SemanticsException if theres no such track, or if theres a train on
     *                            the track
     */
    public boolean removeTrack(int id) throws SemanticsException {
        if (!tracks.containsKey(id)) {
            throw new SemanticsException("no such track");
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
            } else {
                Set<Track> toCheck = checkTrack(tracks.get(id), tracks.get(id).getNextStart(), new HashSet<Track>());
                Collection<Track> other = new HashSet<>(tracks.values());
                other.remove(tracks.get(id));
                if (toCheck.containsAll(other)) {
                    for (Point p : tracks.get(id).getPointsBetween()) {
                        trackMap.remove(p);
                    }
                    tracks.remove(id);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private Set<Track> checkTrack(Track current, Track prev, Set<Track> ret) {
        if (current == null) {
            return ret;
        }
        if (ret.contains(current)) {
            return ret;
        }
        if (prev.equals(current.getNextStart())) {
            ret.add(current.getNextEnd());
            ret.add(current.getNextAltEnd());
            ret.addAll(checkTrack(current.getNextEnd(), current, ret));
            ret.addAll(checkTrack(current.getNextAltEnd(), current, ret));
            
        } else if (prev.equals(current.getNextEnd())) {
            ret.add(current.getNextStart());
            ret.add(current.getNextAltEnd());
            ret.addAll(checkTrack(current.getNextStart(), current, ret));
            ret.addAll(checkTrack(current.getNextAltEnd(), current, ret));
            
        } else if (prev.equals(current.getNextAltEnd())) {
            ret.add(current.getNextStart());
            ret.add(current.getNextEnd());
            ret.addAll(checkTrack(current.getNextEnd(), current, ret));
            ret.addAll(checkTrack(current.getNextStart(), current, ret));
        }
        return ret;
    }

    /**
     * Moves all trains by n steps forward or backwards.
     * 
     * @param n The amount of steps to make
     * @return a map of crashed trains ids mapped to true if they crashed into
     *         another train or false if they only derailed
     * @throws SemanticsException If not all switches have been set yet
     */
    public Set<Integer> step(short n) throws SemanticsException {
        if (unsetSwitches.size() != 0) {
            throw new SemanticsException("not all switches have been set");
        }
        if (trainsOnTrack.size() == 0) {
            return null;
        }
        Set<Integer> crashes = new HashSet<>();
        for (short i = 0; i < Math.abs(n); i++) {
            for (Train t : trainsOnTrack.keySet()) {
                if (n > 0) {
                    // going forwards
                    List<Point> current = trainsOnTrack.get(t);
                    for (int o = current.size() - 1; o > 0; o--) {
                        current.set(o, current.get(o - 1));
                    }
                    // no more track to drive on
                    if (getNextPoint(current.get(0), t.getDirection()) == null) {
                        crashes.add(t.getId());
                        trainsOnTrack.remove(t);
                    } else {
                        Tuple<Point, Point> next = getNextPoint(current.get(0), t.getDirection());
                        current.set(0, next.getFirst());
                        t.setDirection(next.getSecond());
                    }
                } else {
                    // going backwards
                    List<Point> current = trainsOnTrack.get(t);
                    for (int o = 0; o < current.size() - 1; o++) {
                        current.set(o, current.get(o + 1));
                    }
                    if (getNextPoint(current.get(current.size() - 1), t.getDirection().negate()) == null) {
                        crashes.add(t.getId());
                        trainsOnTrack.remove(t);
                    } else {
                        Tuple<Point, Point> next = getNextPoint(current.get(current.size() - 1),
                                t.getDirection().negate());
                        current.set(current.size() - 1, next.getFirst());
                        if (!(trackMap.containsKey(current.get(0).add(t.getDirection())))) {
                            t.setDirection(getNextPoint(current.get(0), t.getDirection()).getSecond());
                        }
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
                            crashes.add(t.getId());
                            crashes.add(k.getId());
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
     * @throws SemanticsException if theres no such track, the given point is not
     *                            one to switch to or if the track is not a switch
     *                            track
     */
    public void toggleSwitch(int id, Point dest) throws SemanticsException {
        if (!tracks.containsKey(id)) {
            throw new SemanticsException("no such track id");
        } else if (tracks.get(id).getAltEnd() == null) {
            throw new SemanticsException("track with the id " + id + " is not a switch track");
        }
        SwitchTrack toSwitch = (SwitchTrack) tracks.get(id);
        if (dest.equals(toSwitch.getStart())) {
            throw new SemanticsException("the given point is not one to switch to");
        } else if (dest.equals(toSwitch.getEnd())) {
            // replaces the boolean values the points are mapped to
            for (Point p : toSwitch.getPointsBetweenEnd()) {
                trackMap.replace(p, true);
            }
            for (Point p : toSwitch.getPointsBetweenAltEnd()) {
                trackMap.replace(p, false);
            }
            toSwitch.setSwitch(toSwitch.getEnd());
            unsetSwitches.remove((Integer) id);
        } else if (dest.equals(toSwitch.getAltEnd())) {
            for (Point p : toSwitch.getPointsBetweenEnd()) {
                trackMap.replace(p, false);
            }
            for (Point p : toSwitch.getPointsBetweenAltEnd()) {
                trackMap.replace(p, true);
            }
            toSwitch.setSwitch(toSwitch.getAltEnd());
            unsetSwitches.remove((Integer) id);
        }
        // removes all trains on the switchtrack
        for (Map.Entry<Train, List<Point>> ent : trainsOnTrack.entrySet()) {
            if (!Collections.disjoint(ent.getValue(), toSwitch.getAllPointsFromTrack())) {
                trainsOnTrack.remove(ent.getKey());
            }
        }
    }

    /**
     * Places a train on a track.
     * 
     * @param t     The train to place
     * @param place The point to put the train on
     * @param dir   The dierction the train is heading
     * @throws SemanticsException If the train is not valid or if theres no such
     *                            point on the track
     */
    public void putTrain(final Train t, final Point place, final Point dir) throws SemanticsException {
        if (!t.isValid()) {
            throw new SemanticsException("train is not valid");
        }
        if (trackMap.get(place) == null) {
            throw new SemanticsException("no such point on the track");
        }
        Point newDir = dir.negate();
        // the list on which points the train is located
        List<Point> points = new ArrayList<>();
        points.add(place);
        for (int i = 1; i <= t.getLength(); i++) {
            // uses the negative trains direction to get the points the train is placed on
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
        Track found = tracks.values().stream().filter(t -> t.getAllPointsFromTrack().contains(p)).findFirst()
                .orElse(null);
        if (found == null) {
            return null;
        }
        for (Track toFind : tracks.values()) {
            if (toFind.getAllPointsFromTrack().contains(p)) {
                found = toFind;
                break;
            }
        }
        if (trackMap.get(p.add(dir)) != null && trackMap.get(p.add(dir))
                && found.getPointsFromThisAndSurroundingTracks().contains(p.add(dir))) {
            return new Tuple<Point, Point>(p.add(dir), dir);

        } else if (trackMap.get(p.add(dir.getLeft())) != null && trackMap.get(p.add(dir.getLeft()))
                && found.getPointsFromThisAndSurroundingTracks().contains(p.add(dir.getLeft()))) {
            return new Tuple<Point, Point>(p.add(dir.getLeft()), dir.getLeft());

        } else if (trackMap.get(p.add(dir.getRight())) != null && trackMap.get(p.add(dir.getRight()))
                && found.getPointsFromThisAndSurroundingTracks().contains(p.add(dir.getRight()))) {
            return new Tuple<Point, Point>(p.add(dir.getRight()), dir.getRight());

        } else {
            return null;
        }
    }

    /**
     * Adds a track to the network
     * 
     * @param toAdd The track to add
     * @throws SemanticsException if the track already exists or if none of the
     *                            point already exist
     */
    public void addTrack(final Track toAdd) throws SemanticsException {
        if (trackMap.isEmpty()) {
            addPointsFromTrack(toAdd);
            tracks.put(toAdd.getId(), toAdd);
        } else {
            if (tracks.containsValue(toAdd)) {
                throw new SemanticsException("track already exists");
            }
            if (!findAndSetFittingTracks(toAdd)) {
                throw new SemanticsException("none of the points exist already");
            } else {
                tracks.put(toAdd.getId(), toAdd);
            }
        }
    }

    /**
     * Finds the proper tracks from the already added ones to properly update their
     * pointers
     * 
     * @param toAdd the track to add
     * @return true, if a fitting track has been found, false else
     */
    private boolean findAndSetFittingTracks(Track toAdd) {
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
     * 
     * @return list of all tracks
     */
    public List<Track> listTracks() {
        List<Track> ret = new ArrayList<>(tracks.values());
        Collections.sort(ret);
        return ret;
    }

    /**
     * Returns a map of all trains and their Points they stand on
     * 
     * @return Map of trains and their points
     */
    public Map<Train, List<Point>> getTrainsOnTrack() {
        return trainsOnTrack;
    }
}