package modeltrain.commands;

import modeltrain.core.Model;

public class Exit extends Command {

    public static final String REGEX = "exit";

    public Exit(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }
    
    @Override
    public boolean isExit() {
        return true;
    }

}
