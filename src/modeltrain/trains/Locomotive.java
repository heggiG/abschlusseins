package modeltrain.trains;

public abstract class Locomotive extends RollMaterial {

    public Locomotive(String modelSeries, String name, boolean front, boolean back) {
        super(modelSeries, name, front, back);
    }
}
