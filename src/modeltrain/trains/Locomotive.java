package modeltrain.trains;

public abstract class Locomotive extends RollMaterial {

    protected final String id;
    
    public Locomotive(String modelSeries, String name, boolean front, boolean back) {
        super(front, back);
        id = modelSeries + "-" + name;
    }
    
    public String getId() {
        return this.id;
    }
    
}
