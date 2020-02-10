package modeltrain.ui;

import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import edu.kit.informatik.Terminal;

public enum Command {

    ADDTRACK("") {
        @Override
        public void execute(MatchResult match) {

        }
    },

    ADDSWITCH("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    DELETETRACK("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    LISTTRACKS("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    SETSWITCH("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    CREATEENGINE("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    LISTENGINES("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    CREATECOACH("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    LISTCOACHES("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    CREATETRAINSET("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    LISTTRAINSETS("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    DELETEROLLINGSTOCK("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    ADDTRAIN("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    DELETETRAIN("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    LISTTRAINS("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    SHOWTRAIN("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    PUTTRAIN("") {
        @Override
        public void execute(MatchResult match) {
            
        }
    },
    
    STEP("") {
        @Override
        public void execute(MatchResult match) {

        }
    },
    
    EXIT("") {
        @Override
        public void execute(MatchResult match) {

        }
    };

    Command(String pattern) {
        this.running = true;
        this.regexPattern = Pattern.compile(pattern);
    }

    private Pattern regexPattern;
    private boolean running;

    public abstract void execute(MatchResult match);

    public boolean isRunning() {
        return running;
    }

    public void exit() {
        running = false;
    }
}
