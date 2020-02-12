package modeltrain.commands;

import modeltrain.core.Model;

public class ShowTrain extends Command {

    public static final String REGEX = "show train (\\S+)";
    
    public ShowTrain(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
