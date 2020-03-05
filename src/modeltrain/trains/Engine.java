package modeltrain.trains;

/**
 * Implements the engine as a subclass of the rolling stock
 * 
 * @author Florian Heck
 * @version 1.1
 */
public abstract class Engine extends RollingStock {

    /**
     * Constructor that sets all needed attributes by passing them into the super
     * constructor, see {@link RollingStock}
     * 
     * @param modelSeries The engines modelseries
     * @param name        The engines name
     * @param front       Whether the engine has front coupling
     * @param back        Whether the engine has back coupling
     * @param length      The engines length
     */
    public Engine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    /**
     * Returns this type as a string
     */
    @Override
    public String getSuperType() {
        return "engine";
    }

    /**
     * 
     * @return A StringBuilder that contains a '#' that will be replaced in the
     *         subclasses to match the requierd String
     */
    protected StringBuilder getStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTrainNumber());
        sb.append(" # ");
        sb.append(getModelSeries());
        sb.append(" ");
        sb.append(getName());
        sb.append(" ");
        sb.append(getLength());
        sb.append(" ");
        sb.append(getFrontCoupling());
        sb.append(" ");
        sb.append(getBackCoupling());
        return sb;
    }
}