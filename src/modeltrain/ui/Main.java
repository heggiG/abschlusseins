package modeltrain.ui;

import java.util.NoSuchElementException;

import edu.kit.informatik.Terminal;
import modeltrain.core.Model;
import modeltrain.core.SemanticsException;
import modeltrain.core.SyntaxException;

public class Main {

    private Main() {
    };

    public static void main(String[] args) {
        Model model = new Model();
        UserInterface userInterface = new UserInterface(model);
        do {
            try {
                userInterface.executeCommand(Terminal.readLine());
            } catch (SyntaxException | SemanticsException e) {
                Terminal.printError(e.getMessage());
            } catch (NoSuchElementException e) {
                Terminal.printError("unknown command");
            }
        } while (!userInterface.isExit());
    }
}
