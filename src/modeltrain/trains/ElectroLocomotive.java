package modeltrain.trains;

public class ElectroLocomotive extends Locomotive {

    public ElectroLocomotive(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    @Override
    public String[] show() {
        return new String[] { "               ___    ", "                 \\    ", "  _______________/__  ",
                " /_| ____________ |_\\ ", "/   |____________|   \\", "\\                    /",
                " \\__________________/ ", "  (O)(O)      (O)(O)  " };
    }

}
