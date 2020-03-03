package modeltrain.trains;

public class TrainSet extends RollMaterial {

    public TrainSet(String model, String name, boolean front, boolean back, int length) {
        super(model, name, front, back, length);
    }

    @Override
    public String getType() {
        return "train-set";
    }
    
    @Override
    public String getSuperType() {
        return getType();
    }
    
    @Override
    public String[] show() {
        return new String[] { "         ++         ", "         ||         ", "_________||_________",
                "|  ___ ___ ___ ___ |", "|  |_| |_| |_| |_| |", "|__________________|", "|__________________|",
                "   (O)        (O)   "};
    }

}
