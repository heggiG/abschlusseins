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
        tracks.add(track);
        //TODO der Rest
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