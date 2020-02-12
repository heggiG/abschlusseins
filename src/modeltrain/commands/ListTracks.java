package modeltrain.commands;

import modeltrain.core.Model;

public class ListTracks extends Command {

    public static final String REGEX = "list tracks";
    
    public ListTracks(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
