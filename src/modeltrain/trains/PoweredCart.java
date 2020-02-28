package modeltrain.trains;

public class PoweredCart extends RollMaterial {

    public PoweredCart(String model, String name, boolean front, boolean back, int length) {
        super(model, name, front, back, length);
    }

    @Override
    public String[] show() {
        return new String[] { "         ++         ", "         ||         ", "_________||_________",
                "|  ___ ___ ___ ___ |", "|  |_| |_| |_| |_| |", "|__________________|", "|__________________|",
                "   (O)        (O)   "};
    }

}
