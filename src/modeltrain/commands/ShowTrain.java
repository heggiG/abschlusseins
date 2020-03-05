package modeltrain.commands;

import java.util.regex.Matcher;

import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

/**
 * Class that implements the show train command
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class ShowTrain extends Command {

    private static final String REGEX = "show train ((-|\\+|)\\d+)";

    /**
     * Constructor that calls the super constructor
     * 
     * @param model The model to operate on
     */
    public ShowTrain(Model model) {
        super(model, REGEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String command) throws SyntaxException {
        Matcher match = getMatcher(command);
        int id = Integer.parseInt(match.group(1));
        if (id < 1) {
            throw new SyntaxException("illegal id (smaller 1)");
        } else {
            model.showTrain(id);
        }
    }

}
