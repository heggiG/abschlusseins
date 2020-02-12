package modeltrain.trains;

public abstract class RollMaterial {
    
    private final boolean frontCoupling;
    private final boolean backCoupling;
    
    public RollMaterial(boolean frontCoupling, boolean backCoupling) {
        this.frontCoupling = frontCoupling;
        this.backCoupling = backCoupling;
    }
    
    public boolean getFrontCoupling() {
        return frontCoupling;
    }
    
    public boolean getBackCoupling() {
        return backCoupling;
    }
}
