package modeltrain.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.Terminal;
import modeltrain.core.Model;

/**
 * Class that implements the list commands
 * 
 * @author Florian Heck
 * @version 1.3
 */
public class List extends Command {

    private static final String REGEX = "list (trains|train-sets|coaches|engines|tracks)";

    /**
     * Constructor that calls the super constructor
     * @param model The model to operate on
     */
    public List(Model model) {
        super(model, REGEX);
    }

    /**
     * {@inheritDoc}
     */
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
    
            case "engines":
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
