package modeltrain.core;

import java.util.List;
import java.util.Map;
import edu.kit.informatik.Terminal;
import modeltrain.trains.Train;

public class Model {

    private Garage garage;
    private TrackNetwork tn;
    private int nextTrackId;

    public Model() {
        garage = new Garage();
        tn = new TrackNetwork();
        nextTrackId = 1;
    }

    public void deleteRS(String id) {
        try {
            garage.deleteRS(id);
            Terminal.printLine("OK");
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    public void deleteTrain(int id) {
        try {
            garage.deleteTrain(id);
            Terminal.printLine("OK");
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    public void createTrainSet(String modelType, String name, boolean front, boolean back, int len) {
        try {
            Terminal.printLine(garage.createPoweredCart(modelType, name, front, back, len));
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    public void createEngine(boolean front, boolean back, int len, String modelType, String name, String type) {
        try {
            Terminal.printLine(garage.createEngine(front, back, len, modelType, name, type));
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    public void createCoach(boolean front, boolean back, int len, String type) {
        try {
            Terminal.printLine(garage.createCoach(front, back, len, type));
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    public void showTrain(int id) {
        try {
            for (String s : garage.showTrain(id)) {
                Terminal.printLine(s);
            }
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    public void setSwitch(int id, Point p) { // TODO train on switch
        try {
            tn.toggleSwitch(id, p);
            Terminal.printLine("OK");
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

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

    public void addTrack(Point start, Point end, Point altEnd) {
        try {
            tn.addTrack(new SwitchTrack(start, end, altEnd, nextTrackId));
            Terminal.printLine(nextTrackId);
            nextTrackId++;
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    public void addToTrain(int trainId, String rmId) {
        try {
            garage.addToTrain(trainId, rmId);
            StringBuilder sb = new StringBuilder();
            sb.append(garage.getRollMaterial(rmId).getType());
            sb.append(" ");
            sb.append(rmId);
            sb.append(" added to train ");
            sb.append(trainId);
            Terminal.printLine(sb.toString());
        } catch (SemanticsException e) {
            Terminal.printError(e.getMessage());
        }
    }

    public void listTrains() {
        try {
            for (String s : garage.listTrains()) {
                Terminal.printLine(s);
            }
        } catch (SemanticsException e) {
            Terminal.printLine("No train exists");
        }
    }

    public void listTrainSets() {
        if (garage.listPC().isEmpty()) {
            Terminal.printError("no train-sets added yet");
        } else {
            for (String s : garage.listPC()) {
                Terminal.printLine(s);
            }
        }
    }

    public void listCoaches() {
        if (garage.listCoaches().isEmpty()) {
            Terminal.printError("no coaches added yet");
        } else {
            for (String s : garage.listCoaches()) {
                Terminal.printLine(s);
            }
        }
    }

    public void listEngines() {
        if (garage.listEngines().isEmpty()) {
            Terminal.printError("no engines added yet");
        } else {
            for (String s : garage.listEngines()) {
                Terminal.printLine(s);
            }
        }
    }

    public void listTracks() {
        if (tn.listTracks().isEmpty()) {
            Terminal.printError("no tracks yet");
        } else {
            for (Track t : tn.listTracks()) {
                Terminal.printLine(t.toString());
            }
        }
    }

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

    private boolean checkDiagonal(Point p1, Point p2) {
        if (p1.getXCord() != p2.getXCord() && p1.getYCord() != p2.getYCord()) {
            return false;
        }
        return true;
    }
}