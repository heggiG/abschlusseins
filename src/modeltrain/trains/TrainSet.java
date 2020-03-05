package modeltrain.trains;

/**
 * Implements the train-set as a subclass of {@link RollingStock}
 * @author Florian Heck
 * @version 1.2
 */
public class TrainSet extends RollingStock {

    /**
     * Sets all needed parameters by using the super constructor, see {@link RollingStock}
     * @param model The trainset model series
     * @param name The trainsets name
     * @param front Whether the trainset has front coupling
     * @param back Whether the trainset has back coupling
     * @param length The trainsets length
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
     * The same as {@link TrainSet#getType()} but only in this case
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
        return new String[] {"         ++         ", "         ||         ", "_________||_________",
            "|  ___ ___ ___ ___ |", "|  |_| |_| |_| |_| |", "|__________________|", "|__________________|",
            "   (O)        (O)   "};
    }

}
