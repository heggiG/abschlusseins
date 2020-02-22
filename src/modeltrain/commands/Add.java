package modeltrain.commands;

import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

public class Add extends Command {

    private int nextTrackId;
    private static final String REGEX = "add //s*//S*";

    public Add(Model model) {
        super(model, REGEX);
        nextTrackId = 0;
    }

    @Override
    public void execute(String command) {
        switch (command.split(" ")[1]) {
        
        case "track":
            addTrack(command);
            break;
            
        case "train":
            addTrain(command);
            break;
            
        case "switch":
            addSwitch(command);
            break;
        
        default:
            throw new SyntaxException("unknown add command");
        }

    }
    
    private void addTrack(String command) {
        
    }
    
    private void addSwitch(String command) {
        
    }
    
    private void addTrain(String command) {
        
    }
    
    private int getNextTrackId() {
        return ++nextTrackId;
    }

}
