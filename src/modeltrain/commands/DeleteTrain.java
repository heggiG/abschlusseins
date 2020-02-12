package modeltrain.commands;

import modeltrain.core.Model;

public class DeleteTrain extends Command {
    
    public static final String REGEX = "delete train (\\S+)";
    
    public DeleteTrain(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        
    }
    
}
