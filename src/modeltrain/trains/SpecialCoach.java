package modeltrain.trains;

/**
 * Implements the special coach as a subclass of {@link Coach}
 * 
 * @author Florian Heck
 * @version 1.2
 */
public class SpecialCoach extends Coach {

    /**
     * Uses the super constuctor to set all needed parameters, see {@link Coach}
     * 
     * @param id     The coaches id
     * @param front  Whether this freight coach has front coupling
     * @param back   Whether this coach has back coupling
     * @param length The coaches length
     */
    public SpecialCoach(int id, boolean front, boolean back, int length) {
        super(id, front, back, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] show() {
        return new String[] {"                   ", "               ____", "/--------------|  |",
            "\\--------------|  |", "  | |          |  |", " _|_|__________|  |", "|_________________|",
            "   (O)       (O)   " };
    }

    /**
     * {@inheritDoc}
     */
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
