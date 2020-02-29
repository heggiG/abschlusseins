package modeltrain.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.Terminal;
import modeltrain.core.Model;

public class List extends Command {

    public static final String REGEX = "list (trains|train-sets|coaches|engines|tracks)";

    public List(Model model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        Matcher match = getMatcher(command);
        switch (match.group(1)) {
        case "trains":
            model.listTrains();
            break;

        case "train-sets":
            model.listTrainSets();
            break;

        case "coaches":
            model.listCoaches();
            break;

        case "enignes":
            model.listEngines();
            break;

        case "tracks":
            model.listTracks();
            break;

        default:
            Terminal.printError("no such list command");
            break;
        }
    }

}
