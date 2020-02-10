package modeltrain.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modeltrain.Model;

public abstract class Command {
    
    protected String regex = "";
    private final Pattern commandPattern;
    protected final Model model;
    
    public static final String COORDINATE = "\\((\\d+),(\\d+)\\)";
    

    protected Command(Model model, String regex) {
        this.regex = regex;
        this.model = model;
        commandPattern = Pattern.compile(regex);
    }

    public abstract void execute(String command);

    public boolean matches(String command) {
        return getMatcher(command).matches();
    }

    protected Matcher getMatcher(String command) {
        Matcher matcher = commandPattern.matcher(command);
        matcher.matches();
        return matcher;
    }

    public boolean isQuit() {
        return false;
    }
}
