package modeltrain.commands;

import java.util.regex.Matcher;
import modeltrain.core.Model;
import modeltrain.core.Point;
import modeltrain.core.SyntaxException;

/**
 * Class that implements the set switch command
 * 
 * @author Florian Heck
 * @version 1.1
 */
public class SetSwitch extends Command {

    private static final String REGEX = "set switch (\\d+) position " + COORDINATE;

    /**
     * Constructor that calls the super constructor
     * 
     * @param model The model to operate on
     */
    public SetSwitch(Model model) {
        super(model, REGEX);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void execute(String command) throws SyntaxException {
        Matcher mr = getMatcher(command);
        int id = Integer.parseInt(mr.group(1));
        if (id < 1) {
            throw new SyntaxException("no id's smaller 1");
        }
        Point p = new Point(Integer.parseInt(mr.group(2)), Integer.parseInt(mr.group(4)));
        model.setSwitch(id, p);
    }

}
