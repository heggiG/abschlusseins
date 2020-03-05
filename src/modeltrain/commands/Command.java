package modeltrain.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modeltrain.core.Model;
import modeltrain.core.SyntaxException;

/**
 * Abstract Command that acts as a super class to all specific commands
 * 
 * @author Florian Heck
 * @version 1.0
 */
public abstract class Command {
    
    /**
     * Regex that matches a coordinate
     */
    public static final String COORDINATE = "\\(((-|\\+|)\\d+),((-|\\+|)\\d+)\\)";
    
    /**
     * Regex that matches the engine type
     */
    public static final String ENGINE_TYPE = "electrical|steam|diesel";
    
    /**
     * Regex that matches the coach type
     */
    public static final String COACH_TYPE = "passenger|freight|special";
    
    /**
     * Regex that matches true or false (the states coupling can be in)
     */
    public static final String COUPLING = "true|false";
    
    /**
     * The model to operate on
     */
    protected final Model model;
    private final Pattern commandPattern;

    /**
     * Constructor that compiles the given regex into a pattern and sets the model
     * 
     * @param model The model to operate on
     * @param regex The regular expression to match on
     */
    protected Command(Model model, String regex) {
        this.model = model;
        commandPattern = Pattern.compile(regex);
    }

    /**
     * Executes the given command
     * 
     * @param command User Input
     * @throws SyntaxException If the input contains syntax errors
     */
    public abstract void execute(String command) throws SyntaxException;

    /**
     * 
     * @param command User input
     * @return True, if a given input matches a regular expression
     */
    public boolean matches(String command) {
        return getMatcher(command).matches();
    }

    /**
     * 
     * @param command User input
     * @return The matcher from a given input
     */
    protected Matcher getMatcher(String command) {
        Matcher matcher = commandPattern.matcher(command);
        matcher.matches();
        return matcher;
    }

    /**
     * 
     * @return True, if this command is the exit command, false else
     */
    public boolean isExit() {
        return false;
    }
}
