package modeltrain.ui;

import java.util.NoSuchElementException;

import edu.kit.informatik.Terminal;
import modeltrain.core.Model;
import modeltrain.core.SemanticsException;
import modeltrain.core.SyntaxException;

/**
 * The Trainsimulations main class
 * 
 * @author Florian
 * @version 1.0
 */
public class Main {

    /**
     * Private Main class constructor
     */
    private Main() {
    };

    public static void main(String[] args) {
        Model model = new Model();
        UserInterface userInterface = new UserInterface(model);
        do {
            try {
                userInterface.executeCommand(Terminal.readLine());
            } catch (SyntaxException e) {
                Terminal.printError(e.getMessage());
            } catch (NoSuchElementException e) {
                Terminal.printError("unknown command");
            }
        } while (!userInterface.isExit());
    }
}
