package modeltrain.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modeltrain.core.Model;
import modeltrain.core.Point;
import modeltrain.core.SyntaxException;

public class Add extends Command {

    private static final String ADD_REGEX = "add (\\s*\\S*)*";
    private static final String ADD_TRACK = "add track " + COORDINATE + " -> " + COORDINATE;
    private static final String ADD_SWITCH = "add switch " + COORDINATE + " -> " + COORDINATE + "," + COORDINATE;
    private static final String ADD_TRAIN = "add train ((-|\\+|)\\d+) (\\S+)";

    public Add(Model model) {
        super(model, ADD_REGEX);
    }

    @Override
    public void execute(String command) {
        if (command.split(" ").length < 2) {
            throw new SyntaxException("wrong syntax for add command");
        }
        switch (command.split(" ")[1]) {

        case "track":
            addTrack(command);
            break;

        case "train":
            addTrain(command);
            break;

        case "switch":
            addSwitch(command);
            break;

        default:
            throw new SyntaxException("unknown add command");
        }

    }

    private void addTrack(String command) {
        Matcher match = Pattern.compile(ADD_TRACK).matcher(command);
        if (!match.matches()) {
            throw new SyntaxException("wrong syntax for add track command");
        } else {
            Point p1 = new Point(Integer.parseInt(match.group(1)), Integer.parseInt(match.group(3)));
            Point p2 = new Point(Integer.parseInt(match.group(5)), Integer.parseInt(match.group(7)));
            model.addTrack(p1, p2);
        }
    }

    private void addSwitch(String command) {
        Matcher match = Pattern.compile(ADD_SWITCH).matcher(command);
        if (!match.matches()) {
            throw new SyntaxException("wrong syntax for add switch command");
        } else {
            Point p1 = new Point(Integer.parseInt(match.group(1)), Integer.parseInt(match.group(3)));
            Point p2 = new Point(Integer.parseInt(match.group(5)), Integer.parseInt(match.group(7)));
            Point p3 = new Point(Integer.parseInt(match.group(9)), Integer.parseInt(match.group(11)));
            model.addTrack(p1, p2, p3);
        }
    }

    private void addTrain(String command) {
        Matcher match = Pattern.compile(ADD_TRAIN).matcher(command);
        if (!match.matches()) {
            throw new SyntaxException("wrong syntax for add train command");
        } else {
            int trainId = Integer.parseInt(match.group(1));
            String rmId = match.group(3);
            model.addToTrain(trainId, rmId);
        }
    }
}
