package modeltrain.commands;

import modeltrain.core.Model;

public class DeleteTrack extends Command {

    private static final String REGEX = "delete track";
    
    public DeleteTrack(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        //TODO keine Luecken nach aufruf
        //TODO Zug auf gleis
        
    }

}
