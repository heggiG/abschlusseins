package modeltrain.commands;

import modeltrain.core.Model;

public class List extends Command {

    public static final String REGEX = "list //s*//S*";
    
    public List(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
