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

}
