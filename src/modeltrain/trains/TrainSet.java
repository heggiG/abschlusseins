package modeltrain.trains;

/**
 * Implements the train-set as a subclass of {@link RollingStock}
 * @author Florian Heck
 *
 */
public class TrainSet extends RollingStock {

    /**
     * Sets all needed parameters by using the super constructor, see {@link RollingStock}
     * @param model
     * @param name
     * @param front
     * @param back
     * @param length
     */
    public TrainSet(String model, String name, boolean front, boolean back, int length) {
        super(model, name, front, back, length);
    }

    /**
     * @author flori
     */
    @Override
    public String getType() {
        return "train-set";
    }
    
    /**
     * The same as {@link TrainSet#getType()}
     */
    @Override
    public String getSuperType() {
        return getType();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String[] show() {
        return new String[] { "         ++         ", "         ||         ", "_________||_________",
                "|  ___ ___ ___ ___ |", "|  |_| |_| |_| |_| |", "|__________________|", "|__________________|",
                "   (O)        (O)   "};
    }

}
