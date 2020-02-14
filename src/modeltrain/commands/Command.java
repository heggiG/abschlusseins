package modeltrain.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modeltrain.core.Model;

public abstract class Command {
    
    protected final Model model;
    private final Pattern commandPattern;    
    
    public static final String COORDINATE = "\\((-|)\\d+),((-|)\\d+)\\)";
    public static final String ENGINE_TYPE = "electrical|steam|diesel";
    public static final String COACH_TYPE = "passenger|freight|special";
    public static final String COUPLING = "true|false";    

    protected Command(Model model, String regex) {
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

    public boolean isExit() {
        return false;
    }
}
