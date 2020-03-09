package modeltrain.commands;

import java.util.regex.MatchResult;
import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

/**
 * Implements the step command
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class Step extends Command {

    private static final String REGEX = "step ((-|\\+|)\\d+)";

    /**
     * Constructor that calls the super constructor
     * 
     * @param model The model to operate on
     */
    public Step(Model model) {
        super(model, REGEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String command) throws SyntaxException {
        MatchResult mr = getMatcher(command);
        short steps = Short.parseShort(mr.group(1));
        model.step(steps);
    }
}
