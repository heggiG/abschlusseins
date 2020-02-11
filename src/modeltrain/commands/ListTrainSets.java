package modeltrain.commands;

import modeltrain.Model;

public class ListTrainSets extends Command {

    public static final String REGEX = "list train-sets";
    
    public ListTrainSets(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}
