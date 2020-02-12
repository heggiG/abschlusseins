package modeltrain.commands;

import modeltrain.core.Model;

public class AddTrain extends Command {

    public static final String REGEX = "add train (\\S+) (\\S+)";
    
    public AddTrain(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
