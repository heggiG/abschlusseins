package modeltrain.core;

import modeltrain.trains.Train;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class TrackNetwork {
    
    private Set<Point> points;
    private List<Point> hiddenPoints;
    private Set<Track> tracks;
    
    public TrackNetwork() {
        points = new HashSet<>();
        tracks = new HashSet<>();
        hiddenPoints = new ArrayList<>();
    }
    
    public void addTrack(Track tr) {
        if (points.size() == 0) {
            points.add(tr.getStart());
            points.add(tr.getEnd());
            addHiddenPoints(tr);
        } else {
            if (hiddenPoints.contains(tr.getStart()) && hiddenPoints.contains(tr.getEnd())) {
                throw new IllegalStateException("track is already contained");
            } else if (hiddenPoints.contains(tr.getStart())) {
                hiddenPoints.add(tr.getEnd());
                tracks.add(tr);
            } else if (hiddenPoints.contains(tr.getEnd())) {
                hiddenPoints.add(hiddenPoints.indexOf(tr.getEnd()), tr.getStart());
                tracks.add(tr);
            }
        }
    }
    
    private void addHiddenPoints(Track tr) {
        int diffX = tr.getEnd().getXCord() - tr.getStart().getXCord();
        int diffY = tr.getEnd().getYCord() - tr.getStart().getYCord();
        int index = hiddenPoints.indexOf(tr.getEnd());
        if (Math.abs(diffX) > 0) {
            if (diffX > 0) {
                for (int i = diffX - 1; i > 1; i--) {
                    hiddenPoints.add(index, new Point(tr.getStart().getXCord() + i, tr.getStart().getYCord()));
                }
            } else {//TODO richtung ändern
                for (int i = diffX + 1; i < -1; i++) {
                    hiddenPoints.add(index, new Point(tr.getStart().getXCord() + i, tr.getStart().getYCord()));
                }
            }
        } else if (Math.abs(diffY) > 0) {
            if (diffY > 0) {
                for (int i = diffY - 1; i > 1; i--) {
                    hiddenPoints.add(new Point(tr.getStart().getXCord(), tr.getStart().getYCord() + i));
                }
            } else {//TODO richtung ändern
                for (int i = diffY + 1; i < -1; i++) {
                    hiddenPoints.add(new Point(tr.getStart().getXCord(), tr.getStart().getYCord() + i));
                }
            }
        }
    }
    
    
//    public void addTrack(Track track) {
//        if (tracks.size() == 0) {
//            //adds a track if there are none
//            tracks.add(track);
//            for (Point p : track.getPoints()) {
//                points.put(p, true);
//            }
//            //adding hidden points
//            for (Point p : getHiddenPoints(track)) {
//                points.put(p, false);
//            }
//        } else {
//            //adds a track if there is already one or more
//            if (!(points.containsKey(track.getStart())) && !(points.containsKey(track.getEnd()))) {
//                //if none of the points exist;
//                throw new IllegalStateException("none of the points are on the track already");
//            } else if (tracks.contains(track)) {
//                //if the track already exists
//                throw new IllegalStateException("track already exists");
//            } else if (tracks.contains(new Track(track.getEnd(), track.getStart(), track.getId()))) {
//                //if track in the other direction already exists
//                throw new IllegalStateException("track exists already in the other direction");
//            } else {
//                if (points.containsKey(track.getStart())) {
//                    points.put(track.getEnd(), true);
//                } else {
//                    points.put(track.getStart(), true);
//                }
//                for (Point p : getHiddenPoints(track)) {
//                    points.put(p, false);
//                }
//                tracks.add(track);
//            }
//        }
//        // TODO der Rest
//    }
//    
//    private Set<Point> getHiddenPoints(Track tr) {
//        Set<Point> ret = new HashSet<>();
//        int diffX = tr.getStart().getXCord() - tr.getEnd().getXCord();
//        int diffY = tr.getStart().getYCord() - tr.getEnd().getYCord();
//        //fills the set with hidden points, to make stopping between track ends possible
//        if (Math.abs(diffX) > 1 && diffY == 0) {
//            if (diffX > 0) {
//                for (int i = diffX - 1; i > 1; i--) {
//                    ret.add(new Point(tr.getStart().getXCord() - i, tr.getStart().getYCord()));
//                }
//            } else {
//                for (int i = Math.abs(diffX) - 1; i > 1; i--) {
//                    ret.add(new Point(tr.getStart().getXCord() + i, tr.getStart().getYCord()));
//                }
//            } 
//        } else if (Math.abs(diffY) > 1 && diffX == 0) {
//            if (diffY > 0) {
//                for (int i = diffX - 1; i > 1; i--) {
//                    ret.add(new Point(tr.getStart().getXCord(), tr.getStart().getYCord() - i));
//                }
//            } else {
//                for (int i = Math.abs(diffX) - 1; i > 1; i--) {
//                    ret.add(new Point(tr.getStart().getXCord(), tr.getStart().getYCord() + i));
//                }
//            }
//        }
//        return ret;
//    }
//    
//    /**
//     * returns the point on which the tip of the given train sits
//     * 
//     * @param train The train to get the position from
//     * @return The point where the trains tip sits
//     */
//    public Point getTrainOnPoint(Train train) {
//        return trainsOnTracks.get(train).get(0);
//    }
    
}
