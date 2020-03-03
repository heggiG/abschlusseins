package modeltrain.trains;

public class ElectricalEngine extends Engine {

    public ElectricalEngine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    @Override
    public String[] show() {
        return new String[] { "               ___    ", "                 \\    ", "  _______________/__  ",
                " /_| ____________ |_\\ ", "/   |____________|   \\", "\\                    /",
                " \\__________________/ ", "  (O)(O)      (O)(O)  " };
    }

    @Override
    public String getType() {
        return "electrical engine";
    }
    
    @Override
    public String toString() {
        StringBuilder sb = super.getString();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "e");
        return sb.toString();
    }
}
