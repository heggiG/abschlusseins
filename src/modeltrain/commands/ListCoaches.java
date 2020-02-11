package modeltrain.commands;

import modeltrain.Model;

public class ListCoaches extends Command {

    public static final String REGEX = "list coaches";
    
    public ListCoaches(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
