package modeltrain.ui;

import modeltrain.Model;
import edu.kit.informatik.Terminal;

public class Main {
    
    public static void main(String[] args) {
        Model model = new Model();
        UserInterface userInterface = new UserInterface(model);
        do {
            userInterface.executeCommand(Terminal.readLine());
        } while (!userInterface.isQuit());
    }
}
