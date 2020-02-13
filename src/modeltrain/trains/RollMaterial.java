package modeltrain.trains;

/**
 * Class that implements rollmaterial, the parent class to anything that goes on train tracks
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

    public RollMaterial(String model, String name, boolean frontCoupling, boolean backCoupling, int length) {
        this.frontCoupling = frontCoupling;
        this.backCoupling = backCoupling;
        this.model = model;
        this.name = name;
        this.id = model + "-" + name;
        this.length = length;
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
}
