package modeltrain.trains;

/**
 * Implements the passenger coach as a subclass of {@link Coach}
 * 
 * @author Florian Heck
 * @version 1.2
 */
public class PassengerCoach extends Coach {

    /**
     * Uses the coaches constructor {@link Coach}
     * 
     * @param id
     * @param front
     * @param back
     * @param length
     */
    public PassengerCoach(int id, boolean front, boolean back, int length) {
        super(id, front, back, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] show() {
        return new String[] { "                    ", "                    ", "____________________",
                "|  ___ ___ ___ ___ |", "|  |_| |_| |_| |_| |", "|__________________|", "|__________________|",
                "   (O)        (O)   " };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "passenger coach";
    }

    @Override
    public String toString() {
        StringBuilder sb = super.getStringBuilder();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "p");
        return sb.toString();
    }
}
