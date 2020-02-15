package modeltrain.core;

import java.util.HashSet;
import java.util.Set;

import modeltrain.trains.Train;

public class TrackNetworkAlt {
 
    private Track[][][] tracks;
    @SuppressWarnings("unused")
    private Set<Train> trains;
    int translatedLength;
    int translatedHeight;

    public TrackNetworkAlt() {
        tracks = new Track[1][1][2];
        trains = new HashSet<>();
        translatedHeight = 0;
        translatedLength = 0;
    }

    public void addTrack(Track t) {
        int diffX = t.getEnd().getXCord() - t.getStart().getXCord();
        int diffY = t.getEnd().getYCord() - t.getStart().getYCord();
        if (diffX != 0 && diffY != 0) {
            throw new IllegalArgumentException("no diagonal tracks");
        } else {
            if (diffX != 0) {
                for (int i = t.getStart().getXCord(); i < t.getEnd().getXCord(); i++) {
                    
                }
            } else {
                 
            }
        }
    }

    /**
     * Combines {@code translate()} and {@code widen()} to adjust the array using
     * the right parameters
     */
    private void adjustArray(int x, int y) {
        if (x >= 0 && y >= 0) {
            tracks = widen(x, y, tracks);
        } else if (x >= 0 && y < 0) {
            tracks = translate(0, y, widen(x, 0, tracks));
        } else if (x < 0 && y >= 0) {
            tracks = translate(x, 0, widen(0, y, tracks));
        } else {
            tracks = translate(x, y, tracks);
        }
    }

    /**
     * Translates the track array by a negative value to compensate negative inputs
     * 
     * @param x   X value to translate by
     * @param y   Y value to translate by
     * @param old the array to translate
     * @return the translated array
     */
    private Track[][][] translate(int x, int y, Track[][][] old) {
        if (x > 0 || y > 0) {
            throw new IllegalArgumentException("method is meant to translate by negative numbers");
        }
        Track[][][] newArray = new Track[old.length + x][old[0].length + y][old[0][0].length];
        for (int i = 0; i < old.length; i++) {
            for (int j = 0; j < old[0].length; j++) {
                for (int k = 0; k < old[0][0].length; k++) {
                    newArray[i + x][j + y][k] = old[i][j][k];
                }
            }
        }
        this.translatedLength = x < 0 ? x : translatedLength;
        this.translatedHeight = y < 0 ? y : translatedHeight;
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
    private Track[][][] widen(int x, int y, Track[][][] old) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("method is meant to widen an array by positive integers");
        }
        Track[][][] returnArray = new Track[old.length > x ? old.length : x + 1][old[0].length > y ? old.length
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

}
