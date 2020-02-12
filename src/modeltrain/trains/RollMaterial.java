package modeltrain.trains;

public abstract class RollMaterial {
    
    protected final boolean frontCoupling;
    protected final boolean backCoupling;
    
    public RollMaterial(boolean frontCoupling, boolean backCoupling) {
        this.frontCoupling = frontCoupling;
        this.backCoupling = backCoupling;
    }
}
