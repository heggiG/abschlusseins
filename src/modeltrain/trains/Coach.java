package modeltrain.trains;

import modeltrain.core.SemanticsException;

public abstract class Coach extends RollMaterial {

    private final String id;

    public Coach(Integer id, boolean front, boolean back, int length) {
        super("", "", front, back, length);
        this.id = "W" + id;
    }
    
    @Override
    public String getType() {
        return "coach";
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        throw new SemanticsException("coaches have no name");
    }

    @Override
    public String getModel() {
        throw new SemanticsException("coaches have no model type");
    }
}