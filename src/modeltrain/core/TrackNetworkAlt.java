package modeltrain.core;

import java.util.HashSet;
import java.util.Set;

import modeltrain.trains.Train;

public class TrackNetworkAlt {

    @SuppressWarnings("unused")
    private Object[][][] tracks;
    @SuppressWarnings("unused")
    private Set<Train> trains;
    
    public TrackNetworkAlt() {
        tracks = new Object[1][1][2];
        trains = new HashSet<>();
    }
    
    public static int[][][] translate(int x, int y, int[][][] old) {
        int[][][] newArray = new int[old.length + x][old[0].length + y][old[0][0].length];
        for (int i = 0; i < old.length; i++) {
            for (int j = 0; j < old[0].length; j++) {
                for (int k = 0; k < old[0][0].length; k++) {
                    newArray[i + x][j + y][k] = old[i][j][k];
                }
            }
        }
        return newArray;
    }
    
}
