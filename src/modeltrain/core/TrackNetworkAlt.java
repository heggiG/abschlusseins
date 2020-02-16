package modeltrain.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import modeltrain.trains.Train;

public class TrackNetworkAlt {

    private Integer[][][] trackMap;
    private Set<Train> trains;
    private Set<Track> tracks;
    private int translatedLength;
    private int translatedHeight;
    private int nextTrainId;
    private List<Integer> nextFreeId;

    public TrackNetworkAlt() {
        // acts as a double layer array, first level is indices of the track at that
        // point, second layer is train id
        trackMap = new Integer[1][1][2];
        tracks = new HashSet<>();
        trains = new HashSet<>();
        nextFreeId = new ArrayList<>();
        translatedHeight = 0;
        translatedLength = 0;
        nextTrainId = 0;
    }

    public void addTrack(Track t) {
        int diffX = t.getEnd().getXCord() - t.getStart().getXCord();
        int diffY = t.getEnd().getYCord() - t.getStart().getYCord();
        if (diffX != 0 && diffY != 0) { // no diagonal tracks (won't happen due to assignement)
            throw new IllegalArgumentException("no diagonal tracks");
        } else if (diffX == 0 && diffY == 0) {
            throw new IllegalArgumentException("track is of length 0");
        }
        if (tracks.size() == 0) {
            try {
                setAtMap(t.getStart(), t);
            } catch (ArrayIndexOutOfBoundsException e) {
                adjustArray(t.getStart().getXCord(), t.getStart().getYCord());
                setAtMap(t.getStart(), t);
                //TODO filling up the rest
            }
        } else {
            try {
                setAtMap(t.getStart(), t);
            } catch (ArrayIndexOutOfBoundsException e) {
                adjustArray(t.getStart().getXCord(), t.getStart().getYCord());
                setAtMap(t.getStart(), t);
                //TODO filling up the rest
            }
        }

    }

    private void setAtMap(Point p, Track t) {
        trackMap[p.getXCord()][p.getYCord()][0] = t.getId();
    }

    /**
     * Combines {@code translate()} and {@code widen()} to adjust the array using
     * the right parameters.
     * 
     * @param x The x value to adjust by
     * @param y The y value to adjust by
     */
    private void adjustArray(int x, int y) {
        if (x >= 0 && y >= 0) {
            trackMap = widen(x, y, trackMap);
        } else if (x >= 0 && y < 0) {
            trackMap = translate(0, y, widen(x, 0, trackMap));
        } else if (x < 0 && y >= 0) {
            trackMap = translate(x, 0, widen(0, y, trackMap));
        } else {
            trackMap = translate(x, y, trackMap);
        }
    }

    /**
     * Translates the track array by a negative value to compensate negative inputs
     * 
     * @param length X value to translate by
     * @param heigth Y value to translate by
     * @param old    the array to translate
     * @return the translated array
     */
    private Integer[][][] translate(int length, int heigth, Integer[][][] old) {
        if (length > 0 || heigth > 0) {
            throw new IllegalArgumentException("method is meant to translate by negative numbers");
        }
        Integer[][][] newArray = new Integer[old.length + length][old[0].length + heigth][old[0][0].length];
        for (int i = 0; i < old.length; i++) {
            for (int j = 0; j < old[0].length; j++) {
                for (int k = 0; k < old[0][0].length; k++) {
                    newArray[i + length][j + heigth][k] = old[i][j][k];
                }

            }
        }
        this.translatedLength = length < translatedLength ? length : translatedLength;
        this.translatedHeight = heigth < translatedHeight ? heigth : translatedHeight;
        return newArray;
    }

    /**
     * Widens an array to allow index inputs greater than {@code array.length}
     * 
     * @param x   x value tried to input
     * @param y   y value tried to input
     * @param old the array to widen
     * @return the widened array
     */
    private Integer[][][] widen(int x, int y, Integer[][][] old) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("method is meant to widen an array by positive integers");
        }
        Integer[][][] returnArray = new Integer[old.length > x ? old.length : x + 1][old[0].length > y ? old.length
                : y + 1][old[0][0].length];
        for (int i = 0; i < old.length; i++) {
            for (int j = 0; j < old[0].length; j++) {
                for (int k = 0; k < old[0][0].length; k++) {
                    returnArray[i][j][k] = old[i][j][k];
                }
            }
        }
        return returnArray;
    }

    public void addTrain(Train tr, Point p, Point dir) {
        if (trains.contains(tr)) {
            throw new IllegalStateException("train is already on the track");
        } else if (trackMap[p.getXCord()][p.getYCord()] == null) {
            throw new IllegalStateException("there is no track at the given point");
        } else if (true) {

        }
    }

    public Optional<Train> getTrain(int id) {
        return trains.stream().filter(c -> c.hashCode() == id).findFirst();
    }

    public void addTrain(Train t) {
        if (trains.contains(t)) {
            throw new IllegalArgumentException("Train already exists");
        }
    }

    private int getNextTrainId() {
        return ++nextTrainId;
    }

}
