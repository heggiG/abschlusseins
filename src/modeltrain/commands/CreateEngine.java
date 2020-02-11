package modeltrain.commands;

import modeltrain.Model;

public class CreateEngine extends Command {

    public static final String REGEX = "create engine (" + ENGINE_TYPE + ") (\\S+) (\\S+) (\\d+) (" + COUPLING + ") (" + COUPLING + ")";
    
    public CreateEngine(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
