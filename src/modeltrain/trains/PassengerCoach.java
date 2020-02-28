package modeltrain.trains;

public class PassengerCoach extends Coach {

    public PassengerCoach(int id, boolean front, boolean back, int length) {
        super(id, front, back, length);
    }

    @Override
    public String[] show() {
        return new String[] { "                    ", "                    ", "____________________",
                "|  ___ ___ ___ ___ |", "|  ___ ___ ___ ___ |", "|__________________|", "|__________________|",
                "   (O)        (O)   " };
    }

}
