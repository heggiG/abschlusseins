package modeltrain.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import edu.kit.informatik.Terminal;
import modeltrain.trains.*;

public class Garage {

    private Map<String, Engine> engineGarage;
    private Map<String, Coach> coachGarage;
    private Map<String, TrainSet> pcGarage;
    private Map<String, RollMaterial> inTrains;
    private Map<Integer, Train> trainGarage;
    private int nextTrainId;
    private int nextCoachId;

    public Garage() {
        engineGarage = new HashMap<>();
        coachGarage = new HashMap<>();
        pcGarage = new HashMap<>();
        trainGarage = new HashMap<>();
        inTrains = new HashMap<>();
        nextTrainId = 1;
        nextCoachId = 1;
    }

    public Engine getLoc(String id) {
        return engineGarage.get(id);
    }

    public TrainSet getPc(String id) {
        return pcGarage.get(id);
    }

    public Coach getCoach(String id) {
        return coachGarage.get(id);
    }

    public RollMaterial getRollMaterial(String id) {
        if (id.charAt(0) == 'W') {
            return getCoach(id);
        } else {
            return getPc(id) != null ? getPc(id) : getLoc(id);
        }
    }

    public String createEngine(boolean front, boolean back, int len, String modelType, String name, String type) {
        Engine toAdd;
        switch (type) {
        case "diesel":
            toAdd = new DieselEngine(modelType, name, front, back, len);
            break;

        case "steam":
            toAdd = new SteamEngine(modelType, name, front, back, len);
            break;

        case "electrical":
            toAdd = new ElectricEngine(modelType, name, front, back, len);
            break;

        default:
            throw new SemanticsException("illegal engine type");
        }
        if (engineGarage.containsKey(toAdd.getId())) {
            throw new SemanticsException("engine id already exists");
        }
        engineGarage.put(toAdd.getId(), toAdd);
        return toAdd.getId();
    }

    public int createCoach(boolean front, boolean back, int len, String type) {
        Coach toAdd;
        switch (type) {
        case "passenger":
            toAdd = new PassengerCoach(nextCoachId, front, back, len);

            break;

        case "special":
            toAdd = new SpecialCoach(nextCoachId, front, back, len);
            break;

        case "freight":
            toAdd = new FreightCoach(nextCoachId, front, back, len);
            break;

        default:
            throw new SemanticsException("illegal coach type");
        }
        if (coachGarage.containsKey(toAdd.getId())) {
            throw new SemanticsException("coach id alredy exists");
        }
        coachGarage.put(toAdd.getId(), toAdd);
        return nextCoachId++;
    }

    public String createPoweredCart(String modelType, String name, boolean front, boolean back, int length) {
        TrainSet toAdd = new TrainSet(modelType, name, front, back, length);
        if (pcGarage.containsKey(toAdd.getId())) {
            throw new SemanticsException("engine id already exists");
        }
        pcGarage.put(toAdd.getId(), toAdd);
        return toAdd.getId();
    }

    public void removeTrain(int id) {
        if (trainGarage.containsKey(id)) {
            trainGarage.remove(id);
        } else {
            throw new SemanticsException("no train with such id");
        }
    }

    public void deleteRS(String id) {
        if (inTrains.containsKey(id)) {
            throw new SemanticsException("rolling stock is placed in a train");
        }
        if (engineGarage.containsKey(id)) {
            engineGarage.remove(id);
        } else if (pcGarage.containsKey(id)) {
            pcGarage.remove(id);
        } else if (coachGarage.containsKey(id)) {
            coachGarage.remove(id);
        } else {
            throw new SemanticsException("no rolling stock with such id");
        }
        Terminal.printLine("OK");
    }

    public void deleteTrain(int id) {
        if (trainGarage.containsKey(id)) {
            trainGarage.remove(id);
            Terminal.printLine("OK");
        } else {
            Terminal.printError("no train with such id");
        }
    }

    public List<String> listEngines() {
        List<String> ret = new ArrayList<>();
        for (String i : engineGarage.keySet()) {
            ret.add(engineGarage.get(i).toString());
        }
        Collections.sort(ret);
        return ret;
    }

    public List<String> listPC() {
        List<String> ret = new ArrayList<>();
        for (String i : pcGarage.keySet()) {
            ret.add(pcGarage.get(i).toString());
        }
        Collections.sort(ret);
        return ret;
    }

    public List<String> listCoaches() {
        List<String> ret = new ArrayList<>();
        for (String i : coachGarage.keySet()) {
            ret.add(coachGarage.get(i).toString());
        }
        Collections.sort(ret);
        return ret;
    }

    public void addToTrain(int id, String rmId) {
        if (inTrains.containsKey(rmId)) {
            throw new SemanticsException("rollmaterial is already in another train");
        }
        if (!pcGarage.containsKey(rmId) && !engineGarage.containsKey(rmId) && !coachGarage.containsKey(rmId)) {
            throw new SemanticsException("no rollmaterial with such id");
        } else if (engineGarage.containsKey(rmId)) {
            newTrain(id);
            trainGarage.get(id).add(engineGarage.get(rmId));
            inTrains.put(rmId, engineGarage.get(rmId));
        } else if (pcGarage.containsKey(rmId)) {
            newTrain(id);
            trainGarage.get(id).add(pcGarage.get(rmId));
            inTrains.put(rmId, pcGarage.get(rmId));
        } else if (coachGarage.containsKey(rmId)) {
            newTrain(id);
            trainGarage.get(id).add(coachGarage.get(rmId));
            inTrains.put(rmId, coachGarage.get(rmId));
        }
    }

    private void newTrain(int id) {
        if (!trainGarage.containsKey(id) && id == nextTrainId) {
            trainGarage.put(nextTrainId, new Train(nextTrainId));
            nextTrainId++;
        } else if (id != nextTrainId) {
            throw new SemanticsException("given id dosen't match the next expected id");
        }
    }

    public Train getTrain(int id) {
        return trainGarage.get(id);
    }

    public List<String> listTrains() {
        if (trainGarage.size() == 0) {
            throw new SemanticsException("no trains have been added yet");
        }
        List<String> ret = new ArrayList<>();
        for (Train i : trainGarage.values()) {
            ret.add(i.toString());
        }
        return ret;
    }

    public List<String> showTrain(int id) {
        if (!trainGarage.containsKey(id)) {
            throw new SemanticsException("no train with the id " + id);
        } else {
            List<String> ret = new ArrayList<>();
            for (int i = 0; i < trainGarage.get(id).show().length; i++) {
                ret.add(trainGarage.get(id).show()[i]);
                if (Pattern.matches("(\\s)+", ret.get(ret.size() - 1))) {
                    ret.remove(ret.size() - 1);
                }
            }
            return ret;
        }
    }
}