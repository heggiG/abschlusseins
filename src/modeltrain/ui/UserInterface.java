package modeltrain.ui;

import java.util.HashSet;
import java.util.Set;
import modeltrain.commands.Add;
import modeltrain.commands.Command;
import modeltrain.commands.Create;
import modeltrain.commands.Delete;
import modeltrain.commands.Exit;
import modeltrain.commands.List;
import modeltrain.commands.PutTrain;
import modeltrain.commands.SetSwitch;
import modeltrain.commands.ShowTrain;
import modeltrain.commands.Step;
import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

/**
 * Implements the userinterface. Accepts a string and will execute a command if
 * a fitting one has been found. This has been modeled after the SWT command
 * structure
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class UserInterface {

    private final Set<Command> commands = new HashSet<>();
    private Command lastFoundCommand;

    /**
     * Constructor that adds all commands to the command set.
     * 
     * @param model The model on which to operate on
     */
    public UserInterface(Model model) {
        commands.add(new Add(model));
        commands.add(new Create(model));
        commands.add(new Delete(model));
        commands.add(new Exit(model));
        commands.add(new List(model));
        commands.add(new PutTrain(model));
        commands.add(new SetSwitch(model));
        commands.add(new ShowTrain(model));
        commands.add(new Step(model));
    }

    /**
     * Executes the found command
     * 
     * @param command The command line input
     * @throws SyntaxException if there are syntax errors in the input
     */
    public void executeCommand(String command) throws SyntaxException {
        getCommand(command).execute(command);
    }

    /**
     * Gets the fitting command from the set.
     * 
     * @param command The input
     * @return The found command
     */
    private Command getCommand(String command) {
        lastFoundCommand = commands.stream().filter(co -> co.matches(command)).findFirst().get();    
        return lastFoundCommand;
    }

    /**
     * 
     * @return Whether the last found command is the exit command / false if no
     *         command has been found
     */
    public boolean isExit() {
        if (lastFoundCommand == null) {
            return false;
        }
        return lastFoundCommand.isExit();
    }
}
