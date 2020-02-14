package modeltrain.core;

import java.util.HashSet;
import java.util.Set;

import modeltrain.trains.Train;

public class TrackNetworkAlt {

    @SuppressWarnings("unused")
    private int[][][] tracks;
    @SuppressWarnings("unused")
    private Set<Train> trains;
    int translatedLength;
    int translatedHeight;

    public TrackNetworkAlt() {
        tracks = new int[1][1][2];
        trains = new HashSet<>();
        translatedHeight = 0;
        translatedLength = 0;
    }

    /**
     * Translates the track array by a negative value to compensate negative inputs
     * 
     * @param x X value to translate by
     * @param y Y value to translate by
     * @param old the array to translate
     * @return the translated array
     */
    private int[][][] translate(int x, int y, int[][][] old) {
        if (x > 0 || y > 0) {
            throw new IllegalArgumentException("method is meant to translate by negative numbers");
        }
        int[][][] newArray = new int[old.length + x][old[0].length + y][old[0][0].length];
        for (int i = 0; i < old.length; i++) {
            for (int j = 0; j < old[0].length; j++) {
                for (int k = 0; k < old[0][0].length; k++) {
                    newArray[i + x][j + y][k] = old[i][j][k];
                }
            }
        }
        this.translatedLength = x;
        this.translatedHeight = y;
        return newArray;
    }

    /**
     * Widens an array to allow index inputs greater than {@code array.length}
     * 
     * @param x x value tried to input
     * @param y y value tried to input
     * @param old the array to widen
     * @return the widened array
     */
    private int[][][] widen(int x, int y, int[][][] old) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("method is meant to widen an array by positive integers");
        }
        int[][][] returnArray = new int[old.length > x ? old.length : x + 1]
                [old[0].length > y ? old.length : y + 1][old[0][0].length];
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
