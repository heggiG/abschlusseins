package modeltrain.commands;

import modeltrain.Model;
import java.util.regex.MatchResult;

public class AddTrack extends Command {

    public static final String REGEX = "add track " + COORDINATE;
    private MatchResult mr;
    
    public AddTrack(Model model, String input) {
        super(model, REGEX);
        mr = getMatcher(input);
    }
    
    @Override
    public void execute(String command) {
        String coord = mr.group(1);
    }

}
