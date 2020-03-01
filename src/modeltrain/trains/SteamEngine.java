package modeltrain.trains;

public class SteamEngine extends Engine {

    public SteamEngine(String modelSeries, String name, boolean front, boolean back, int length) {
        super(modelSeries, name, front, back, length);
    }

    @Override
    public String[] show() {
        return new String[] { "                    ", "     ++      +------", "     ||      |+-+ | ",
                "   /---------|| | | ", "  + ========  +-+ | ", " _|--/~\\------/~\\-+ ", "//// \\_/      \\_/   "};
    }

    @Override
    public String toString() {
        StringBuilder sb = super.getString();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "s");
        return sb.toString();
    }
}
