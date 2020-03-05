package modeltrain.commands;

import modeltrain.core.Model;

/**
 * Implements the exit command
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class Exit extends Command {

    private static final String REGEX = "exit";

    /**
     * Constructor that calls the super constructor
     * 
     * @param model The model to operate on
     */
    public Exit(Model model) {
        super(model, REGEX);
    }

    /**
     * {@inheritDoc}. Does nothing here
     */
    @Override
    public void execute(String command) {
        // nothing to do here
    }

    /**
     * Overrides the isExit method to return true, since this is the exit command
     */
    @Override
    public boolean isExit() {
        return true;
    }

}
