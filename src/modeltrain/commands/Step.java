package modeltrain.commands;

import java.util.Set;
import java.util.regex.MatchResult;
import edu.kit.informatik.Terminal;
import modeltrain.core.Model;
import modeltrain.core.Point;
import modeltrain.core.SyntaxException;
import modeltrain.core.Tuple;

public class Step extends Command {

    public static final String REGEX = "step ((-|+|)\\d+)";

    public Step(Model model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        MatchResult mr = getMatcher(command);
        short steps = Short.parseShort(mr.group(1));
        if (steps < 1) {
            throw new SyntaxException("amount of steps need to be greater than 0");
        } else {
            Tuple<Set<Tuple<Integer, Point>>, Set<Integer>> ret = model.step(steps);
            if (ret == null) {
                Terminal.printLine("OK");
            } else {
                for (Tuple<Integer, Point> tup : ret.getFirst()) {
                    Terminal.printLine("Train " + tup.getFirst() + " at " + tup.getSecond().toString());
                }
                if (!ret.getSecond().isEmpty()) {
                    StringBuilder crash = new StringBuilder();
                    crash.append("Crash of trains ");
                    for (Integer id : ret.getSecond()) {
                        crash.append(id);
                        crash.append(",");
                    }
                    crash.deleteCharAt(crash.lastIndexOf(","));
                    Terminal.printLine(crash.toString());
                }
            }
        }
    }

}
