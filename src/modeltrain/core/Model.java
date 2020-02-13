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
    private Map<Train, Map<Track, Point>> trainsOnTracks;

    public Model() {
        tracks = new HashMap<>();
        trains = new HashSet<>();
        points = new HashMap<>();
        rollMaterial = new HashSet<>();
        trainsOnTracks = new HashMap<>();
    }

    public void addTrack(Track track) {
        if (tracks.size() == 0) {
            tracks.put(track, track.getPoints());
            for (Point p : track.getPoints()) {
                points.put(p, true);
            }
            int diffX = track.getStart().getXCord() - track.getEnd().getXCord();
            int diffY = track.getStart().getYCord() - track.getEnd().getYCord();
            //fills the points with hidden ones, to make stopping between track ends possible
            if (Math.abs(diffX) > 1 && diffY == 0) {
                if (diffX > 0) {
                    for (int i = diffX - 1; i > 1; i--) {
                        points.put(new Point(track.getStart().getXCord() - i, track.getStart().getYCord()), false);
                    }
                } else {
                    for (int i = Math.abs(diffX) - 1; i > 1; i--) {
                        points.put(new Point(track.getStart().getXCord() + i, track.getStart().getYCord()), false);
                    }
                } 
            } else if (Math.abs(diffY) > 1 && diffX == 0) {
                if (diffY > 0) {
                    for (int i = diffX - 1; i > 1; i--) {
                        points.put(new Point(track.getStart().getXCord(), track.getStart().getYCord() - i), false);
                    }
                } else {
                    for (int i = Math.abs(diffX) - 1; i > 1; i--) {
                        points.put(new Point(track.getStart().getXCord(), track.getStart().getYCord() + i), false);
                    }
                }
            }
        } else {
            if (!(points.containsKey(track.getStart())) && !(points.containsKey(track.getEnd()))) {
                throw new IllegalStateException("none of the points are on the track already");
            } else if (points.containsKey(track.getStart()) && points.containsKey(track.getEnd())) {
                throw new IllegalStateException("track already exist in either direction");
            } else {
                if (points.containsKey(track.getStart())) {
                    points.put(track.getEnd(), true);
                }
            }
        }
        // TODO der Rest
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

    public void putTrain(Train tr, Map<Track, Point> trackDirection) {
        if (trainsOnTracks.containsKey(tr)) {
            throw new IllegalStateException("train already on the track");
        }
        trainsOnTracks.put(tr, trackDirection);
        // TODO alles andere
    }

    public void step(int n) {
        // TODO alles
    }

    public void removeTrain(Train tr) {

    }
}
