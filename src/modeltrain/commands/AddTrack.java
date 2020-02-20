package modeltrain.commands;

import java.util.regex.MatchResult;

import modeltrain.core.Model;
import modeltrain.core.Point;

public class AddTrack extends Command {

    private static final String REGEX = "add track (" + COORDINATE + ") -> (" + COORDINATE + ")";
    private int nextId;
    
    public AddTrack(Model model) {
        super(model, REGEX);
        nextId = 0;
    }
    
    @Override
    public void execute(String command) {
        MatchResult mr = getMatcher(command);
        Point start = new Point(Integer.parseInt(mr.group(2)), Integer.parseInt(mr.group(4)));
        Point end = new Point(Integer.parseInt(mr.group(7)), Integer.parseInt(mr.group(9)));
    }

}
