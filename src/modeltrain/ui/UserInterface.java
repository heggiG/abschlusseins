package modeltrain.ui;

import java.util.HashSet;
import java.util.Set;
import modeltrain.commands.Add;
import modeltrain.commands.Command;
import modeltrain.commands.CreateCoach;
import modeltrain.commands.CreateEngine;
import modeltrain.commands.CreateTrainSet;
import modeltrain.commands.DeleteRollingStock;
import modeltrain.commands.DeleteTrack;
import modeltrain.commands.DeleteTrain;
import modeltrain.commands.Exit;
import modeltrain.commands.List;
import modeltrain.commands.PutTrain;
import modeltrain.commands.SetSwitch;
import modeltrain.commands.ShowTrain;
import modeltrain.commands.Step;
import modeltrain.core.Model;

public class UserInterface {

    private final Set<Command> commands = new HashSet<>();
    private Command lastFoundCommand;

    public UserInterface(Model model) {
        commands.add(new Add(model));
        commands.add(new CreateCoach(model));
        commands.add(new CreateEngine(model));
        commands.add(new CreateTrainSet(model));
        commands.add(new DeleteRollingStock(model));
        commands.add(new DeleteTrack(model));
        commands.add(new DeleteTrain(model));
        commands.add(new Exit(model));
        commands.add(new List(model));
        commands.add(new PutTrain(model));
        commands.add(new SetSwitch(model));
        commands.add(new ShowTrain(model));
        commands.add(new Step(model));
    }

    public void executeCommand(String command) {
        getCommand(command).execute(command);
    }

    private Command getCommand(String command) {
        return lastFoundCommand = commands.stream()
                .filter(co -> co.matches(command))
                .findFirst().get();
    }

    public boolean isExit() {
        if (lastFoundCommand == null) {
            return false;
        }
        return lastFoundCommand.isExit();
    }
}
