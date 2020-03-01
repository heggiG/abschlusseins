package modeltrain.trains;

public class TrainSet extends RollMaterial {

    public TrainSet(String model, String name, boolean front, boolean back, int length) {
        super(model, name, front, back, length);
    }

    @Override
    public String getType() {
        return "trainset";
    }
    
    @Override
    public String[] show() {
        return new String[] { "         ++         ", "         ||         ", "_________||_________",
                "|  ___ ___ ___ ___ |", "|  |_| |_| |_| |_| |", "|__________________|", "|__________________|",
                "   (O)        (O)   "};
    }

}