package modeltrain.commands;

import java.util.regex.MatchResult;
import modeltrain.core.Model;
import modeltrain.trains.Coach;
import modeltrain.trains.FreightCoach;
import modeltrain.trains.PassengerCoach;
import modeltrain.trains.RollMaterial;
import modeltrain.trains.SpecialCoach;

public class CreateCoach extends Command {

    private int id;
    public static final String REGEX = "create coach (" + COACH_TYPE + ") (\\d+) (" + COUPLING + ") (" + COUPLING + ")";
    
    public CreateCoach(Model model) {
        super(model, REGEX);
        this.id = 0;
    }
    
    @Override
    public void execute(String command) {
        Coach coach = null;
        MatchResult mr = super.getMatcher(command);
        int length = Integer.parseInt(mr.group(2));
        boolean front = Boolean.parseBoolean(mr.group(3));
        boolean back = Boolean.parseBoolean(mr.group(4));
        switch (mr.group(1)) {
        case "passenger":
            coach = new PassengerCoach(getNextId(), front, back, length);
            break;
            
        case "freight":
            coach = new FreightCoach(getNextId(), front, back, length);
            break;
            
        case "special":
            coach = new SpecialCoach(getNextId(), front, back, length);
            break;
        }
        model.addRollMaterial(coach);
    }
    
    private int getNextId() {
        return ++id;
    }

}