package modeltrain.commands;

import modeltrain.core.Model;

public class DeleteRollingStock extends Command {

    public static final String REGEX = "delete rolling stock ((-|\\+|)\\d+)";
    
    public DeleteRollingStock(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {

    }

}
