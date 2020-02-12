package modeltrain.commands;

import modeltrain.core.Model;

public class SetSwitch extends Command {

    public static final String REGEX = "set switch (\\d+) position (" + COORDINATE + ")";
    
    public SetSwitch(Model model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute(String command) {
        // TODO Point nicht vorhanden/Gleis-ID nicht Vorhanden

    }

}
