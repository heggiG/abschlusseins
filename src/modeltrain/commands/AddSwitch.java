package modeltrain.commands;

import modeltrain.Model;

public class AddSwitch extends Command {

    private static final String REGEX = "add switch (" + COORDINATE + ") -> (" + COORDINATE + "),(" + COORDINATE + ")";
    
    public AddSwitch(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        
    }

}
