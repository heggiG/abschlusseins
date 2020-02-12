package modeltrain.commands;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modeltrain.Model;

public enum AddEnum {
    
    ADDSWITCH("add switch (" + AddEnum.COORDINATE + ") -> (" + AddEnum.COORDINATE + "),(" + AddEnum.COORDINATE + ")") {
        @Override
        public void execute(MatchResult mr, Model model) {
            String pointOne = mr.group(1);
            int p1x = Integer.parseInt(mr.group(2));
        }
    }
    ,
    
    ADDTRACK("") {
        @Override
        public void execute(MatchResult mr, Model model) {
            
        }
    },
    
    ADDTRAIN("") {
        @Override
        public void execute(MatchResult mr, Model model) {
            
        }
    };
    
    public static final String COORDINATE = "\\(((-|)\\d+),((-|)\\d+)\\)";

    private Pattern pat;
    
    private AddEnum(String reg) {
        
    }
    
    public static AddEnum runFitting(String in, Model model) {
        for (AddEnum comm : AddEnum.values()) {
            Matcher match = comm.pat.matcher(in);
            if (match.matches()) {
                comm.execute(match, model);
                return comm;
            }
        }
        throw new IllegalStateException("Unknown command.");
    }
    
    public abstract void execute(MatchResult mr, Model model);
}
