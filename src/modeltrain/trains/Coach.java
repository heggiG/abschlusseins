package modeltrain.trains;

/**
 * Implements the abstract coach rolling stock
 * 
 * @author Florian Heck
 * @version 1.2
 */
public abstract class Coach extends RollingStock {

    /**
     * Calls the super constructor but dosen't set the model series, since the coach
     * dosen't have one
     * 
     * @param id     The coaches id
     * @param front  Whether the coach has front coupling
     * @param back   Whether the coach has back coupling
     * @param length The coaches length
     */
    public Coach(Integer id, boolean front, boolean back, int length) {
        super("", id.toString(), front, back, length);
    }

    /**
     * Returns null as coaches have no name
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Returns null as coaches habe no model series
     */
    @Override
    public String getModelSeries() {
        return null;
    }

    /**
     * 
     * @return a stringbuilder that subclasses use to build the proper toString
     */
    protected StringBuilder getStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getId().substring(1));
        sb.append(" ");
        sb.append(getTrainNumber());
        sb.append(" # "); // to be replaced in subclass
        sb.append(super.getLength());
        sb.append(" ");
        sb.append(super.getFrontCoupling());
        sb.append(" ");
        sb.append(super.getBackCoupling());
        return sb;
    }
}