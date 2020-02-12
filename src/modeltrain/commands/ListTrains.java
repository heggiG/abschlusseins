package modeltrain.commands;

import modeltrain.core.Model;

public class ListTrains extends Command {

    public static final String REGEX = "list trains";
    
    public ListTrains(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
