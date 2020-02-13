package modeltrain.core;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import modeltrain.trains.RollMaterial;
import modeltrain.trains.Train;


public class Model {
    
    private Set<Track> tracks;
    private Set<Train> trains;
    //Maps points to boolean values whether a point can be seen or if it is hidden
    private Map<Point, Boolean> points;
    private Set<RollMaterial> rollMaterial;
    private Map<Train, Map<Track, Point>> trainsOnTracks;
    
    public Model() {
        tracks = new HashSet<>();
        trains = new HashSet<>();
        points = new HashMap<>();
        rollMaterial = new HashSet<>();
        trainsOnTracks = new HashMap<>();
    }
    
    public void addTrack(Track track) {
        if (track.getStart().getXCord() != track.getEnd().getXCord() //checking for diagonals
                && track.getStart().getYCord() != track.getEnd().getYCord()) {
            throw new IllegalStateException("tracks can't be diagonal");
        }
        //checking if one point exists
        if (tracks.size() != 0 && !(points.containsKey(track.getStart())) && !(points.containsKey(track.getEnd()))) {
            throw new IllegalStateException("none of the given points exist");
        }
        //if there are no points, any track will be accepted
        if (tracks.size() == 0) {
            tracks.add(track);
            points.put(track.getStart(), true);
            points.put(track.getEnd(), true);            
        } else {
            if (points.containsKey(track.getStart())) {
                points.put(track.getEnd(), true);
            }
        }
        //TODO der Rest
    }
    
    public void addTrack(SwitchTrack sw) {
        
    }
    
    public void addPoint(Point p) {
        points.put(p, true);
        //TODO der rest
    }
    
    public void addTrain(Train tr) {
        trains.add(tr);
        //TODO der rest
    }
    
    public void addRollMaterial(RollMaterial rm) {
        rollMaterial.add(rm);
        //TODO der rest
    }
    
    public void putTrain(Train tr, Map<Track, Point> trackDirection) {
        if (trainsOnTracks.containsKey(tr)) {
            throw new IllegalStateException("train already on the track");
        }
        trainsOnTracks.put(tr, trackDirection);
        //TODO alles andere
    }
    
    public void step(int n) {
        //TODO alles
    }
    
    public void removeTrain(Train tr) {
        
    }
}
