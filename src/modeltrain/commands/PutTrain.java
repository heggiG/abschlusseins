package modeltrain.commands;

import modeltrain.Model;

public class PutTrain extends Command {

    public static final String REGEX = "put train (\\S+) at (" + COORDINATE + ") in direction ((-|)\\d+),((-|)\\d+)";
    
    public PutTrain(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
