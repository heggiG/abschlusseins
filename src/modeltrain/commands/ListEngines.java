package modeltrain.commands;

import modeltrain.Model;

public class ListEngines extends Command {

    public static final String REGEX = "list engines";
    
    public ListEngines(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
