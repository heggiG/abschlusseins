package modeltrain.commands;

import java.util.regex.Matcher;

import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

public class DeleteTrack extends Command {

    private static final String REGEX = "delete track ((-|\\+|)\\d+)";

    public DeleteTrack(Model model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        Matcher match = getMatcher(command);
        int id = Integer.parseInt(match.group(1));
        if (id < 1) {
            throw new SyntaxException("illegal id (smaller 1)");
        }
        model.deleteTrack(id);
    }

}
