package modeltrain.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modeltrain.trains.Train;

public class TrackNetworkTwo {

    private Set<Point> trackMap;
    private Map<Train, List<Point>> trainsOnTrack;

    public TrackNetworkTwo() {
        trackMap = new HashSet<>();
        trainsOnTrack = new HashMap<>();
    }

    public void addTrack(Track t) {
        if (trackMap.isEmpty()) {
            addPointsFromTrack(t);
        } else {
            if (!trackMap.contains(t.getStart()) && !trackMap.contains(t.getEnd())) {
                throw new SemanticsException("track contains none of the points from the track");
            } else {
                addPointsFromTrack(t);
            }
        }
    }

    public void addTrack(SwitchTrack st) {
        
    }

    private void addPointsFromTrack(Track t) {
        int diffX = t.getEnd().getXCord() - t.getStart().getXCord();
        int diffY = t.getEnd().getYCord() - t.getStart().getYCord();
        Point toAdd = new Point(diffX, diffY).reduce();
        for (int i = 0; i < t.getLength(); i++) {
            trackMap.add(t.getStart().add(toAdd.scale(i)));
        }
    }

}