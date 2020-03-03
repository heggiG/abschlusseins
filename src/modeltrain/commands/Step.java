package modeltrain.commands;

import java.util.regex.MatchResult;
import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

public class Step extends Command {

    public static final String REGEX = "step ((-|\\+|)\\d+)";

    public Step(Model model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) throws SyntaxException {
        MatchResult mr = getMatcher(command);
        short steps = Short.parseShort(mr.group(1));
        if (steps == 0) {
            throw new SyntaxException("amount of steps can't be 0");
        } else {
            model.step(steps);
        }
    }

}
