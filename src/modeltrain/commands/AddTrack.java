package modeltrain.commands;

import modeltrain.Model;
import java.util.regex.MatchResult;

public class AddTrack extends Command {

    private static final String REGEX = "add track (" + COORDINATE + ") -> (" + COORDINATE + ")";
    
    public AddTrack(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        MatchResult mr = getMatcher(command);
    }

}
