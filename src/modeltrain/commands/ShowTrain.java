package modeltrain.commands;

import java.util.regex.Matcher;

import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

public class ShowTrain extends Command {

    public static final String REGEX = "show train ((-|\\+|)\\d+)";
    
    public ShowTrain(Model model) {
        super(model, REGEX);
    }
    
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
