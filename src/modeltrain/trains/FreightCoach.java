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
    
    @Override
    public String getType() {
        return "freight coach";
    }
    
    @Override
    public String toString() {
        StringBuilder sb = super.getStringBuilder();
        sb.replace(sb.indexOf("#"), sb.indexOf("#") + 1, "f");
        return sb.toString();
    }
}
