package modeltrain.trains;

public abstract class Locomotive extends RollMaterial {

    public Locomotive(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }
    
    @Override
    public String getType() {
        return "locomotive";
    }
}
