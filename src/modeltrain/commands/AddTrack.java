package modeltrain.commands;

import modeltrain.Model;

public class AddTrack extends Command {

    public static final String REGEX = "add track " + COORDINATE;
    
    public AddTrack(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        
    }

}
