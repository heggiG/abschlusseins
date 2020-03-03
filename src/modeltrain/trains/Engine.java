package modeltrain.trains;

public abstract class Engine extends RollMaterial {

    public Engine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    @Override
    public String getSuperType() {
        return "engine";
    }

    protected StringBuilder getString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTrainNumber());
        sb.append(" # ");
        sb.append(getModel());
        sb.append(" ");
        sb.append(getName());
        sb.append(" ");
        sb.append(getLength());
        sb.append(" ");
        sb.append(getFrontCoupling());
        sb.append(" ");
        sb.append(getBackCoupling());
        return sb;
    }
}