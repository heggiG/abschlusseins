package modeltrain.trains;

public abstract class Coach extends RollMaterial {

    private final String id;

    public Coach(Integer id, boolean front, boolean back, int length) {
        super("", "", front, back, length);
        this.id = id.toString();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        throw new IllegalStateException("coaches have no name");
    }

    @Override
    public String getModel() {
        throw new IllegalStateException("coaches have no model type");
    }
}