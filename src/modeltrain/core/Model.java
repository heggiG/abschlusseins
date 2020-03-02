package modeltrain.core;

import java.util.List;
import java.util.Map;
import edu.kit.informatik.Terminal;
import modeltrain.trains.Train;

/**
 * Combines both the garage and the tracknetwork and prints possible outputs
 * @author Florian Heck
 * @version 1.5
 */
public class Model {

    private Garage garage;
    private TrackNetwork tn;
    private int nextTrackId;

    /**
     * Constructor to set everything up
     */
    public Model() {
        garage = new Garage();
        tn = new TrackNetwork();
        nextTrackId = 1;
    }

    /**
     * Tries to delete rolling stock
     * @param id The rolling stocks id
     */
    public void deleteRS(String id) {
        try {
            garage.deleteRS(id);
            Terminal.printLine("OK");
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Tries to delete a train
     * @param id The trains id
     */
    public void deleteTrain(int id) {
        try {
            garage.deleteTrain(id);
            Terminal.printLine("OK");
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Tries to create a trainset
     * @param modelType The trainsets model
     * @param name The train sets name 
     * @param front Whether the trainset has front coupling
     * @param back Whether the trainset has back coupling
     * @param len The trainsets length
     */
    public void createTrainSet(String modelType, String name, boolean front, boolean back, int len) {
        try {
            Terminal.printLine(garage.createPoweredCart(modelType, name, front, back, len));
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Tries to create a new engine
     * @param front Whether the engine has front coupling
     * @param back Whether the engine has back coupling
     * @param len The engines length
     * @param modelType The engines model
     * @param name The engines name
     * @param type The engines type (electrical, steam or diesel)
     */
    public void createEngine(boolean front, boolean back, int len, String modelType, String name, String type) {
        try {
            Terminal.printLine(garage.createEngine(front, back, len, modelType, name, type));
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Tries to create a new Coach
     * @param front Whether the coach has front coupling
     * @param back Whether the coach has back coupling
     * @param len The coaches length
     * @param type The coaches type (passenger, freight or special)
     */
    public void createCoach(boolean front, boolean back, int len, String type) {
        try {
            Terminal.printLine(garage.createCoach(front, back, len, type));
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Tries to print a trains string (ASCII art) representation
     * @param id The trains id
     */
    public void showTrain(int id) {
        try {
            for (String s : garage.showTrain(id)) {
                Terminal.printLine(s);
            }
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Tries to switch a switchtrack to the given Point
     * @param id The switchtracks id which to switch
     * @param p The point to switch to
     */
    public void setSwitch(int id, Point p) { // TODO train on switch
        try {
            tn.toggleSwitch(id, p);
            Terminal.printLine("OK");
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }
    
    /**
     * Tries to place a train on the trackmap
     * @param id The id of the train to place
     * @param place The point on which to place the train
     * @param dir The direction in which the point is heading
     */
    public void putTrain(int id, Point place, Point dir) {
        Train toPlace = garage.getTrain(id);
        if (toPlace == null) {
            Terminal.printError("no train with such id");
        } else {
            try {
                tn.putTrain(toPlace, place, dir);
            } catch (SemanticsException e) {
                Terminal.printError(e.getMessage());
            }
        }
    }

    /**
     * Tries to add a track to the trackmap and gives it the next id and checks for diagonal tracks
     * @param start The tracks start
     * @param end The tracks end
     */
    public void addTrack(Point start, Point end) {
        if (!checkDiagonal(start, end)) {
            Terminal.printError("no diagonal tracks");
        } else {
            try {
                Track t = new Track(start, end, nextTrackId);
                tn.addTrack(t);
                Terminal.printLine(nextTrackId);
                nextTrackId++;
            } catch (SemanticsException e) {
                Terminal.printError(e.getMessage());
            }
        }
    }

    /**
     * Tries to add a Switch track to the trackmap
     * @param start The switchtracks start
     * @param end The switchtracks end
     * @param altEnd The swtitchtracks alternative end
     */
    public void addTrack(Point start, Point end, Point altEnd) { //TODO diagonal track
        try {
            tn.addTrack(new SwitchTrack(start, end, altEnd, nextTrackId));
            Terminal.printLine(nextTrackId);
            nextTrackId++;
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Adds rollingstock to a train and creates a new train if no train with the given id exists yet
     * @param trainId The train id to add to
     * @param rsId The rollingstocks id
     */
    public void addToTrain(int trainId, String rsId) {
        try {
            garage.addToTrain(trainId, rsId);
            StringBuilder sb = new StringBuilder();
            sb.append(garage.getRollMaterial(rsId).getType());
            sb.append(" ");
            sb.append(rsId);
            sb.append(" added to train ");
            sb.append(trainId);
            Terminal.printLine(sb.toString());
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Tries to print a list of all trains
     */
    public void listTrains() {
        try {
            for (String s : garage.listTrains()) {
                Terminal.printLine(s);
            }
        } catch (SemanticsException e) {
            Terminal.printLine("No train exists");
        }
    }
    
    /**
     * Tries to print a list of all trainsets
     */
    public void listTrainSets() {
        if (garage.listPC().isEmpty()) {
            Terminal.printError("no train-sets added yet");
        } else {
            for (String s : garage.listPC()) {
                Terminal.printLine(s);
            }
        }
    }

    /**
     * Tries to print a list with all coaches
     */
    public void listCoaches() {
        if (garage.listCoaches().isEmpty()) {
            Terminal.printError("no coaches added yet");
        } else {
            for (String s : garage.listCoaches()) {
                Terminal.printLine(s);
            }
        }
    }

    /**
     * Tries to print a list with all engines
     */
    public void listEngines() {
        if (garage.listEngines().isEmpty()) {
            Terminal.printError("no engines added yet");
        } else {
            for (String s : garage.listEngines()) {
                Terminal.printLine(s);
            }
        }
    }

    /**
     * Tries to print a list of all tracks
     */
    public void listTracks() {
        if (tn.listTracks().isEmpty()) {
            Terminal.printError("no tracks yet");
        } else {
            for (Track t : tn.listTracks()) {
                Terminal.printLine(t.toString());
            }
        }
    }

    /**
     * Tries to delete a track from the trackmap
     * @param id The tracks id
     */
    public void deleteTrack(int id) {
        try {
            if (tn.removeTrack(id)) {
                Terminal.printLine("OK");
            } else {
                Terminal.printError("no such track id");
            }
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    /**
     * Moves all trains n steps forward or backward and prints the positions of all trains and all crashes that happend
     * @param n The amount of steps to make
     */
    public void step(short n) {
        Map<Integer, Boolean> ret = tn.step(n);
        if (ret == null) {
            Terminal.printLine("OK");
        } else {
            for (Map.Entry<Train, List<Point>> me : tn.getTrainsOnTrack().entrySet()) {
                Terminal.printLine("Train " + me.getKey().getId() + " at " + me.getValue().get(0).toString());
            }
            if (!ret.isEmpty()) {
                StringBuilder crash = new StringBuilder();
                crash.append("Crash of trains ");
                for (Map.Entry<Integer, Boolean> id : ret.entrySet()) {
                    if (id.getValue()) {
                        garage.removeTrain(id.getKey());
                    }
                    crash.append(id.getKey());
                    crash.append(",");
                }
                crash.deleteCharAt(crash.lastIndexOf(","));
                Terminal.printLine(crash.toString());
            }
        }
    }

    //checks for diagonal tracks
    private boolean checkDiagonal(Point p1, Point p2) {
        if (p1.getXCord() != p2.getXCord() && p1.getYCord() != p2.getYCord()) {
            return false;
        }
        return true;
    }
}