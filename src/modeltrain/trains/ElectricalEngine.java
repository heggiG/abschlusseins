package modeltrain.trains;

/**
 * Implements the electrical engine as a subclass of {@link Engine}
 * 
 * @author Florian Heck
 * @version 1.2
 */
public class ElectricalEngine extends Engine {

    /**
     * Sets all parameters by using the super constructor, see {@link Engine}
     * 
     * @param modelSeries The engines model series
     * @param name        The enignes name
     * @param front       Whether the engine has front coupling
     * @param back        Whether the engine has back coupling
     * @param length      The engines length
     */
    public ElectricalEngine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] show() {
        return new String[] {"               ___    ", "                 \\    ", "  _______________/__  ",
            " /_| ____________ |_\\ ", "/   |____________|   \\", "\\                    /",
            " \\__________________/ ", "  (O)(O)      (O)(O)  " };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "electrical engine";
    }

    @Override
    public String toString() {
        StringBuilder sb = super.getStringBuilder();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "e");
        return sb.toString();
    }
}
