package modeltrain.trains;

/**
 * Class that implements rollmaterial, the parent class to anything that goes on
 * train tracks
 * 
 * @author Florian
 * @version 0.2
 */
public abstract class RollingStock {

    private final boolean frontCoupling;
    private final boolean backCoupling;
    private final String modelSeries;
    private final String name;
    private final String id;
    private final int length;
    private String trainNumber;

    /**
     * Constructor that sets all needed parameters. Coaches as a sub type do not
     * have a name nor a model series
     * 
     * @param modelSeries   The stocks model series (empty if coach)
     * @param name          The stocks name (emty if coach)
     * @param frontCoupling Whether the stock has front coupling
     * @param backCoupling  Whether the stock has back coupling
     * @param length        The stocks length
     * @param type          The rollingstocks type
     */
    public RollingStock(String modelSeries, String name, boolean frontCoupling, boolean backCoupling, int length) {
        this.frontCoupling = frontCoupling;
        this.backCoupling = backCoupling;
        this.modelSeries = modelSeries;
        this.name = name;
        if (modelSeries != "")
            this.id = modelSeries + "-" + name;
        else
            this.id = "W" + name;
        this.length = length;
        trainNumber = "none";
    }
    
    /**
     * Returns a string array containing the rolling stocks ascii art representation
     * 
     * @return The stocks ascii art representation
     */
    public abstract String[] show();

    /**
     * 
     * @return Returns the exact type of any given rolling stock
     */
    public abstract String getType();
    
    /**
     * 
     * @return Whether the stock has front coupling
     */
    public boolean getFrontCoupling() {
        return frontCoupling;
    }

    /**
     * 
     * @return Whether the stock has back coupling
     */
    public boolean getBackCoupling() {
        return backCoupling;
    }

    /**
     * 
     * @return The stocks name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return The stocks model series
     */
    public String getModelSeries() {
        return modelSeries;
    }

    /**
     * 
     * @return The stocks id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return The stocks length
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets this stocks train number to the id, or to "none" if -1 is given as
     * parameter
     * 
     * @param id The id to set to
     */
    public void setTrainNumber(Integer id) {
        if (id == -1) {
            trainNumber = "none";
        } else {
            trainNumber = id.toString();
        }
    }

    /**
     * 
     * @return The id of the train this stock is attached to
     */
    public String getTrainNumber() {
        return trainNumber;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (getClass() != o.getClass())
            return false;
        RollingStock rm = (RollingStock) o;
        if (rm.id.equals(id))
            return true;
        return false;
    }
}
