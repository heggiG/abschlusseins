package modeltrain.ui;

import java.util.Arrays;

import edu.kit.informatik.Terminal;
import modeltrain.core.Model;

public class Main {
    
    public static void main(String[] args) {
      /*  Model model = new Model();
        UserInterface userInterface = new UserInterface(model);
        do {
            userInterface.executeCommand(Terminal.readLine());
        } while (!userInterface.isExit()); */
        
        int[][][] n = new int[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    n[i][j][k] = i + j + k;
                }
            }
        }
        
        n = modeltrain.core.TrackNetworkAlt.translate(2, 2, n);
        
        for(int k[][] : n) {
                Terminal.printLine(Arrays.toString(k));
        }
    }
}
