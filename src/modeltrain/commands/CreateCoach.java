package modeltrain.commands;

import modeltrain.Model;

public class CreateCoach extends Command {

    public static final String REGEX = "create coach (" + COACH_TYPE + ") (\\d+) (" + COUPLING + ") (" + COUPLING + ")";
    
    public CreateCoach(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Auto-generated method stub

    }

}