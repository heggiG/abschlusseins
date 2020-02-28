package modeltrain.core;

import edu.kit.informatik.Terminal;
import modeltrain.trains.DieselLocomotive;
import modeltrain.trains.Train;

public class TrackTest {

    @SuppressWarnings({ "rawtypes", "unused" })
    public static void main(String args[]) {
        Model test = new Model();
        Point p = new Point(10, 0);
        test.addTrack(new Track(new Point(0, 0), p, 0));
        test.addTrack(new Track(new Point(10, 0), new Point(20, 0), 1));
        test.addTrack(new Track(new Point(20, 0), new Point(22, 0), 2));
        test.addTrack(new Track(new Point(22, 0), new Point(22, 4), 3));
        Train t = new Train(1);
        t.add(new DieselLocomotive("Bruh", "Homie", false, true, 1));
        test.putTrain(t, new Point(2, 0), new Point(1, 0));
        for(Tuple tu : test.step((short) 5).getFirst())
            Terminal.printLine(tu);
        Track st = new SwitchTrack(new Point(0, 0), new Point(1,0), new Point(0, 1), 1);
        if (test.removeTrack(2)) {
            System.out.println("alal");
        }
    }
}