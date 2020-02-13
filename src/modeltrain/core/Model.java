package modeltrain.core;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import modeltrain.trains.RollMaterial;
import modeltrain.trains.Train;

public class Model {

    private Map<Track, Set<Point>> tracks;
    private Set<Train> trains;
    // Maps points to boolean values whether a point can be seen or if it is hidden
    private Map<Point, Boolean> points;
    private Set<RollMaterial> rollMaterial;
    //maps trains to points
    private Map<Train, Point> trainsOnTracks;

    public Model() {
        tracks = new HashMap<>();
        trains = new HashSet<>();
        points = new HashMap<>();
        rollMaterial = new HashSet<>();
        trainsOnTracks = new HashMap<>();
    }

    public void addTrack(Track track) {
        if (tracks.size() == 0) {
            //adds a track if there are non
            tracks.put(track, track.getPoints());
            for (Point p : track.getPoints()) {
                points.put(p, true);
            }
            //adding hidden points
            for (Point p : getHiddenPoints(track)) {
                points.put(p, false);
            }
        } else {
            //adds a track if there is already one or more
            if (!(points.containsKey(track.getStart())) && !(points.containsKey(track.getEnd()))) {
                //if none of the points exist;
                throw new IllegalStateException("none of the points are on the track already");
            } else if (tracks.containsKey(track)) {
                //if the track already exists
                throw new IllegalStateException("track already exist one or another direction");
            } else {
                if (points.containsKey(track.getStart())) {
                    points.put(track.getEnd(), true);
                } else {
                    points.put(track.getStart(), true);
                }
                for (Point p : getHiddenPoints(track)) {
                    points.put(p, false);
                }
                tracks.put(track, track.getPoints());
            }
        }
        // TODO der Rest
    }
    
    private Set<Point> getHiddenPoints(Track tr) {
        Set<Point> ret = new HashSet<>();
        int diffX = tr.getStart().getXCord() - tr.getEnd().getXCord();
        int diffY = tr.getStart().getYCord() - tr.getEnd().getYCord();
        //fills the set with hidden points, to make stopping between track ends possible
        if (Math.abs(diffX) > 1 && diffY == 0) {
            if (diffX > 0) {
                for (int i = diffX - 1; i > 1; i--) {
                    ret.add(new Point(tr.getStart().getXCord() - i, tr.getStart().getYCord()));
                }
            } else {
                for (int i = Math.abs(diffX) - 1; i > 1; i--) {
                    ret.add(new Point(tr.getStart().getXCord() + i, tr.getStart().getYCord()));
                }
            } 
        } else if (Math.abs(diffY) > 1 && diffX == 0) {
            if (diffY > 0) {
                for (int i = diffX - 1; i > 1; i--) {
                    ret.add(new Point(tr.getStart().getXCord(), tr.getStart().getYCord() - i));
                }
            } else {
                for (int i = Math.abs(diffX) - 1; i > 1; i--) {
                    ret.add(new Point(tr.getStart().getXCord(), tr.getStart().getYCord() + i));
                }
            }
        }
        return ret;
    }

    public void addTrack(SwitchTrack sw) {

    }

    public void addPoint(Point p) {
        points.put(p, true);
        // TODO der rest
    }

    public void addTrain(Train tr) {
        trains.add(tr);
        // TODO der rest
    }

    public void addRollMaterial(RollMaterial rm) {
        rollMaterial.add(rm);
        // TODO der rest
    }

//    public void putTrain(Train tr, Map<Track, Point> trackDirection) {
//        if (trainsOnTracks.containsKey(tr)) {
//            throw new IllegalStateException("train already on the track");
//        }
//        trainsOnTracks.put(tr, trackDirection);
//        // TODO alles andere
//    }

    public void step(int n) {
        // TODO alles
    }

    public void removeTrain(Train tr) {

    }
}
