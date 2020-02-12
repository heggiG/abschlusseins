package modeltrain.commands;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import modeltrain.Model;

public enum CommandEnum {

    ADD("add \\S+\\s+") {
        @Override
        public void execute(String s, Model model) {
            try {
                AddEnum.runFitting(s, model);
            } catch (IllegalStateException e) {
                Terminal.printError(e.getMessage());
            }
        }
    },
    
    CREATE("") {
        @Override
        public void execute(String s, Model model) {
            
        }
    },
    
    DELETE("") {
        @Override
        public void execute(String s, Model model) {
            
        }
    },
    
    LIST("") {
        @Override
        public void execute(String s, Model model) {
            
        }
    },
    
    PUTTRAIN("") {
        @Override
        public void execute(String s, Model model) {
            
        }
    },
    
    SETSWITCH("") {
        @Override
        public void execute(String s, Model model) {
            
        }
    },
    
    SHOWTRAIN("") {
        @Override
        public void execute(String s, Model model) {
            
        }
    },
    
    STEP("") {
        @Override
        public void execute(String s, Model model) {
            
        }
    },
    
    EXIT("") {
        @Override
        public void execute(String s, Model model) {
            exit();
        }
    };
    
    public static final String COORDINATE = "\\((-|)\\d+),((-|)\\d+)\\)";
    public static final String ENGINE_TYPE = "electrical|steam|diesel";
    public static final String COACH_TYPE = "passenger|freight|special";
    public static final String COUPLING = "true|false";
    
    private boolean isRunning;
    private Pattern pat;
    
    private CommandEnum(String reg) {
        this.pat = Pattern.compile(reg);
        isRunning = true;
    }
    
    public static CommandEnum runFitting(String in, Model model) {
        for (CommandEnum comm : CommandEnum.values()) {
            Matcher match = comm.pat.matcher(in);
            if (match.matches()) {
                comm.execute(in, model);
                return comm;
            }
        }
        throw new IllegalStateException("Unknown command.");
    }
    
    public abstract void execute(String s, Model model);
    
    public boolean isExit() {
        return isRunning;
    }
    
    public void exit() {
        isRunning = false;
    }
    
}
