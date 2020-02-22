package modeltrain.core;

import edu.kit.informatik.Terminal;
import modeltrain.trains.DieselLocomotive;
import modeltrain.trains.ElectroLocomotive;
import modeltrain.trains.Train;

public class TrackTest {

    public static void main(String args[]) {
        Model test = new Model();
        Point p = new Point(10, 0);
        test.addTrack(new Track(new Point(0, 0), p, 0));
        Train t = new Train(1);
        t.add(new DieselLocomotive("Bruh", "Homie", false, true, 5));
        test.putTrain(t, new Point(7, 0), new Point(1, 0));
//        Train k = new Train(2);
//        k.add(new ElectroLocomotive("Bitch", "Two", false, true, 1));
//        test.putTrain(k, new Point(9, 0), new Point(-1, 0));
        Terminal.printLine(test.step(5).getSecond());
        System.out.println("Bruh");
    }
}