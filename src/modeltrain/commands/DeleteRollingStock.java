package modeltrain.commands;

import modeltrain.Model;

public class DeleteRollingStock extends Command {

    public static final String REGEX = "delete rolling stock (\\S+)";
    
    public DeleteRollingStock(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
