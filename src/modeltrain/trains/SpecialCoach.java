package modeltrain.trains;

public class SpecialCoach extends Coach {

    public SpecialCoach(int id, boolean front, boolean back, int length) {
        super(id, front, back, length);
    }

    @Override
    public String[] show() {
        return new String[] { "                   ", "               ____", "/--------------|  |",
                "\\--------------|  |", "  | |          |  |", " _|_|__________|  |", "|_________________|",
                "   (O)       (O)   " };
    }
    
    @Override
    public String getType() {
        return "special coach";
    }
    
    @Override
    public String toString() {
        StringBuilder sb = super.getStringBuilder();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "s");
        return sb.toString();
    }
}
