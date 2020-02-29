package modeltrain.commands;

import java.util.regex.Matcher;

import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

public class DeleteTrain extends Command {
    
    public static final String REGEX = "delete train (\\S+)";
    
    public DeleteTrain(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        Matcher match = getMatcher(command);
        int id = Integer.parseInt(match.group(1));
        if (id < 1) {
            throw new SyntaxException("illegal id (smaller 1)");
        }
        model.deleteTrain(id);
    }
    
}
