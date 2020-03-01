package modeltrain.trains;

public abstract class Engine extends RollMaterial {

    public Engine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    @Override
    public String getType() {
        return "locomotive";
    }

    protected StringBuilder getString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTrainNumber());
        sb.append(" # ");
        sb.append(super.getModel());
        sb.append(" ");
        sb.append(super.getName());
        sb.append(" ");
        sb.append(super.getLength());
        sb.append(" ");
        sb.append(super.getFrontCoupling());
        sb.append(" ");
        sb.append(super.getBackCoupling());
        return sb;
    }
}