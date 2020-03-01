package modeltrain.trains;

public class DieselEngine extends Engine {

    public DieselEngine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    @Override
    public String[] show() {
        return new String[] { "                      ", "                      ", "  _____________|____  ",
                " /_| ____________ |_\\ ", "/   |____________|   \\", "\\                    /",
                " \\__________________/ ", "  (O)(O)      (O)(O)  " };
    }

    @Override
    public String toString() {
        StringBuilder sb = super.getString();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "d");
        return sb.toString();
    }
    
}
