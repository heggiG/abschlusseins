package modeltrain.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

/**
 * Implements the create command
 * 
 * @author Florian Heck
 * @version 1.1
 */
public class Create extends Command {

    private static final String REGEX = "create (\\s*\\S*)*";
    private static final String COACH_REGEX = "create coach (" + COACH_TYPE + ") ((-|\\+|)\\d+) (" + COUPLING + ") ("
            + COUPLING + ")";
    private static final String ENG_REGEX = "create engine (" + ENGINE_TYPE + ") (\\S+) (\\S+) ((-|\\+|)\\d+) ("
            + COUPLING + ") (" + COUPLING + ")";
    private static final String TS_REGEX = "create train-set (\\S+) (\\S+) ((-|\\+|)\\d+) (" + COUPLING + ") ("
            + COUPLING + ")";

    /**
     * Constructor that calls super constructor
     * 
     * @param model The model to operate on
     */
    public Create(Model model) {
        super(model, REGEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String command) throws SyntaxException {
        if (command.split(" ").length < 2) {
            throw new SyntaxException("unknown create command");
        } else {
            switch (command.split(" ")[1]) {
                case "coach":
                    createCoach(command);
                    break;
    
                case "engine":
                    createEngine(command);
                    break;
    
                case "train-set":
                    createTrainSet(command);
                    break;
    
                default:
                    throw new SyntaxException("unknown create command");
            }
        }
    }

    private void createTrainSet(String command) throws SyntaxException {
        Matcher match = Pattern.compile(TS_REGEX).matcher(command);
        if (!match.matches()) {
            throw new SyntaxException("wrong syntax for create coach command");
        }
        String modelType = match.group(1);
        if (modelType.equals("W")) {
            throw new SyntaxException("model type can't be W");
        }
        String name = match.group(2);
        int len = Integer.parseInt(match.group(3));
        boolean couplingFront = Boolean.parseBoolean(match.group(5));
        boolean couplingBack = Boolean.parseBoolean(match.group(6));
        if (!couplingBack && !couplingFront) {
            throw new SyntaxException("train-set needs at least one ");
        }
        model.createTrainSet(modelType, name, couplingFront, couplingBack, len);
    }

    private void createEngine(String command) throws SyntaxException {
        Matcher match = Pattern.compile(ENG_REGEX).matcher(command);
        if (!match.matches()) {
            throw new SyntaxException("wrong syntax for create coach command");
        }
        String type = match.group(1);
        String modelType = match.group(2);
        String name = match.group(3);
        int len = Integer.parseInt(match.group(4));
        boolean couplingFront = Boolean.parseBoolean(match.group(6));
        boolean couplingBack = Boolean.parseBoolean(match.group(7));
        if (!couplingBack && !couplingFront) {
            throw new SyntaxException("engine needs at least one coupling");
        }
        model.createEngine(couplingFront, couplingBack, len, modelType, name, type);
    }

    private void createCoach(String command) throws SyntaxException {
        Matcher match = Pattern.compile(COACH_REGEX).matcher(command);
        if (!match.matches()) {
            throw new SyntaxException("wrong syntax for create coach command");
        }
        String type = match.group(1);
        int len = Integer.parseInt(match.group(2));
        boolean couplingFront = Boolean.parseBoolean(match.group(4));
        boolean couplingBack = Boolean.parseBoolean(match.group(5));
        if (!couplingBack && !couplingFront) {
            throw new SyntaxException("coach needs at least one coupling");
        }
        model.createCoach(couplingFront, couplingBack, len, type);
    }
}