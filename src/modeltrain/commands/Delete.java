package modeltrain.commands;

import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

public class Delete extends Command {

    private static final String REGEX = "delete (train|rolling stock|track) (\\S*)";

    public Delete(Model model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) throws SyntaxException {
        switch (getMatcher(command).group(1)) {
        case "train":
            deleteTrain(command);
            break;

        case "rolling stock":
            deleteRS(command);
            break;

        case "track":
            deleteTrack(command);
            break;

        default:
            throw new SyntaxException("wrong syntax for delete command");
        }
    }

    private void deleteTrain(String command) throws SyntaxException {
        int id;
        try {
            id = Integer.parseInt(getMatcher(command).group(2));
        } catch (NumberFormatException e) {
            throw new SyntaxException("number format: integer required for delete train");
        }
        model.deleteTrain(id);
    }

    private void deleteRS(String command) {
        String id = getMatcher(command).group(2);
        model.deleteRS(id);
    }

    private void deleteTrack(String command) throws SyntaxException {
        int id;
        try {
            id = Integer.parseInt(getMatcher(command).group(2));
        } catch (NumberFormatException e) {
            throw new SyntaxException("number format: integer required for delete track");
        }
        model.deleteTrack(id);
    }
}