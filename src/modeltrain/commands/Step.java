package modeltrain.commands;

import modeltrain.core.Model;

public class Step extends Command {

    public static final String REGEX = "step (\\d+)";
    
    public Step(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
