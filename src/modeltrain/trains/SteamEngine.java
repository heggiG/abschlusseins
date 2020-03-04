package modeltrain.trains;

/**
 * Implements the steam engine as a sub class of {@link Engine}
 * 
 * @author Florian Heck
 * @version 1.2
 */
public class SteamEngine extends Engine {

    /**
     * Sets all Parameters by using the super constructor, see {@link Engine}
     * 
     * @param modelSeries
     * @param name
     * @param front
     * @param back
     * @param length
     */
    public SteamEngine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] show() {
        return new String[] { "                    ", "                    ", "     ++      +------",
                "     ||      |+-+ | ", "   /---------|| | | ", "  + ========  +-+ | ", " _|--/~\\------/~\\-+ ",
                "//// \\_/      \\_/   " };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "steam engine";
    }

    @Override
    public String toString() {
        StringBuilder sb = super.getStringBuilder();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "s");
        return sb.toString();
    }
}
