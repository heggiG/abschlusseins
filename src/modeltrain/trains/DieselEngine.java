package modeltrain.trains;

/**
 * Implments a diesel engine as a subtyp of Engine
 * @author Florian Heck
 * @version 1.2
 */
public class DieselEngine extends Engine {

    /**
     * Constructor to set all needed parameters, only calls the super constructor
     * @param modelSeries
     * @param name
     * @param front
     * @param back
     * @param length
     */
    public DieselEngine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] show() {
        return new String[] { "                      ", "                      ", "  _____________|____  ",
                " /_| ____________ |_\\ ", "/   |____________|   \\", "\\                    /",
                " \\__________________/ ", "  (O)(O)      (O)(O)  " };
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "diesel engine";
    }
    
    /**
     * Returns this classes String representation by working with the supertypes {@link #getStringBuilder()}
     */
    @Override
    public String toString() {
        StringBuilder sb = super.getStringBuilder();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "d");
        return sb.toString();
    }
    
}
