package modeltrain.ui;

import java.util.HashSet;
import java.util.Set;

import modeltrain.Model;
import modeltrain.commands.AddTrack;
import modeltrain.commands.Command;

public class UserInterface {
    private final Set<Command> commands = new HashSet<>();
    private Command lastFoundCommand;

    public UserInterface(Model model) {
        commands.add(new AddTrack(model));
    }

    public void executeCommand(String command) {
        getCommand(command).execute(command);
    }

    private Command getCommand(String command) {
        return lastFoundCommand = commands.stream()
                .filter(c -> c.matches(command))
                .findFirst().get();
    }

    public boolean isQuit() {
        return lastFoundCommand.isQuit();
    }
}
