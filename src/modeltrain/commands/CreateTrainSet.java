package modeltrain.commands;

import modeltrain.Model;

public class CreateTrainSet extends Command {

    public static final String REGEX = "create train-set (\\S+) (\\S+) (\\d+) (" + COUPLING + ") (" + COUPLING + ")";
    
    public CreateTrainSet(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
