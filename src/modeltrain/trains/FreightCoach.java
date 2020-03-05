package modeltrain.trains;

/**
 * Implements the freightcoach, provides the proper toString and show methods
 * 
 * @author Florian Heck
 * @version 1.2
 *
 */
public class FreightCoach extends Coach {

    /**
     * Constructor to set the needed attributes
     * 
     * @param id     The coaches id
     * @param front  Whether this freight coach has front coupling
     * @param back   Whether this coach has back coupling
     * @param length The coaches length
     */
    public FreightCoach(int id, boolean front, boolean back, int length) {
        super(id, front, back, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] show() {
        return new String[] {"                    ", "                    ", "                    ",
            "|                  |", "|                  |", "|                  |", "|__________________|",
            "   (O)        (O)   " };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "freight coach";
    }

    /**
     * Returns this classes String representation by working with the supertypes
     * {@link #getStringBuilder()}
     */
    @Override
    public String toString() {
        StringBuilder sb = super.getStringBuilder();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "f");
        return sb.toString();
    }
}
