package modeltrain.trains;

import modeltrain.core.SemanticsException;

public abstract class Coach extends RollMaterial {


    public Coach(Integer id, boolean front, boolean back, int length) {
        super("", id.toString(), front, back, length);
    }

    @Override
    public String getSuperType() {
        return "coach";
    }
    
    @Override
    public String getName() {
        throw new SemanticsException("coaches have no name");
    }

    @Override
    public String getModel() {
        throw new SemanticsException("coaches have no model type");
    }
    
    protected StringBuilder getStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getId().substring(1));
        sb.append(" ");
        sb.append(getTrainNumber());
        sb.append(" # "); //to be replaced in subclass
        sb.append(super.getLength());
        sb.append(" ");
        sb.append(super.getFrontCoupling());
        sb.append(" ");
        sb.append(super.getBackCoupling());
        return sb;
    }
}