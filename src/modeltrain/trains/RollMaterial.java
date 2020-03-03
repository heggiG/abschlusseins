package modeltrain.trains;

/**
 * Class that implements rollmaterial, the parent class to anything that goes on
 * train tracks
 * 
 * @author Florian
 * @version 0.2
 */
public abstract class RollMaterial {

    private final boolean frontCoupling;
    private final boolean backCoupling;
    private final String model;
    private final String name;
    private final String id;
    private final int length;
    private String trainNumber;

    public RollMaterial(String model, String name, boolean frontCoupling, boolean backCoupling, int length) {
        this.frontCoupling = frontCoupling;
        this.backCoupling = backCoupling;
        this.model = model;
        this.name = name;
        if (model != "")
            this.id = model + "-" + name;
        else
            this.id = "W" + name;
        this.length = length;
        trainNumber = "none";
    }

    public boolean getFrontCoupling() {
        return frontCoupling;
    }

    public boolean getBackCoupling() {
        return backCoupling;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public void setTrainNumber(Integer id) {
        if (id == -1) {
            trainNumber = "none";
        } else {
            trainNumber = id.toString();
        }
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public abstract String[] show();

    public abstract String getType();
    
    public abstract String getSuperType();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (getClass() != o.getClass())
            return false;
        RollMaterial rm = (RollMaterial) o;
        if (rm.id.equals(id))
            return true;
        return false;
    }
}
