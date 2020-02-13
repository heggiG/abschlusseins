package modeltrain.trains;

public abstract class RollMaterial {

    private final boolean frontCoupling;
    private final boolean backCoupling;
    private final String model;
    private final String name;
    private final String id;

    public RollMaterial(String model, String name, boolean frontCoupling, boolean backCoupling) {
        this.frontCoupling = frontCoupling;
        this.backCoupling = backCoupling;
        this.model = model;
        this.name = name;
        this.id = model + "-" + name;
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
}
