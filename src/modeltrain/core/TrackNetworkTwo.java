package modeltrain.core;

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

    public void putTrain(Train t, Point p) {
        
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

}