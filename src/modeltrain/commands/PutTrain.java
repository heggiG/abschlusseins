package modeltrain.commands;

import java.util.regex.MatchResult;

import modeltrain.core.Model;
import modeltrain.core.Point;

/**
 * Class that implements the put train command
 * 
 * @author Florian Heck
 * @version 1.0
 */
public class PutTrain extends Command {

    private static final String REGEX = "put train (\\d+) at " + COORDINATE
            + " in direction ((-|\\+|)\\d+),((-|\\+|)\\d+)";

    /**
     * Constructor that calls the super constructor
     * 
     * @param model The model to operate on
     */
    public PutTrain(Model model) {
        super(model, REGEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String command) {
        MatchResult mr = getMatcher(command);
        int id = Integer.parseInt(mr.group(1));
        Point place = new Point(Integer.parseInt(mr.group(2)), Integer.parseInt(mr.group(4)));
        Point dir = new Point(Integer.parseInt(mr.group(6)), Integer.parseInt(mr.group(8)));
        model.putTrain(id, place, dir);
    }

}
