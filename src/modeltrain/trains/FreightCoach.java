package modeltrain.trains;

public class FreightCoach extends Coach {

    public FreightCoach(int id, boolean front, boolean back, int length) {
        super(id, front, back, length);
    }

    @Override
    public String[] show() {
        return new String[] { "                    ", "                    ", "                    ",
                "|                  |", "|                  |", "|                  |", "|__________________|",
                "   (O)        (O)   "};
    }

}
