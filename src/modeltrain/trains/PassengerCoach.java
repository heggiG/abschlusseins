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

    @Override
    public String getType() {
        return "passenger coach";
    }
    
    @Override
    public String toString() {
        StringBuilder sb = super.getStringBuilder();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "p");
        return sb.toString();
    }
}
