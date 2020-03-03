package modeltrain.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import modeltrain.trains.*;

/**
 * Implements a garage which stores all Rolling stock and trains and can do
 * operations like adding to trains.
 * 
 * @author Florian Heck
 * @version 1.3
 */
public class Garage {

    private Map<String, Engine> engineGarage;
    private Map<String, Coach> coachGarage;
    private Map<String, TrainSet> pcGarage;
    private Map<String, RollMaterial> inTrains;
    private Map<Integer, Train> trainGarage;
    private int nextTrainId;
    private int nextCoachId;
    private List<Integer> nextFreeTrainId;

    /**
     * Standard constructor which sets up all attributes
     */
    public Garage() {
        engineGarage = new HashMap<>();
        coachGarage = new HashMap<>();
        pcGarage = new HashMap<>();
        trainGarage = new HashMap<>();
        inTrains = new HashMap<>();
        nextFreeTrainId = new ArrayList<>();
        nextTrainId = 1;
        nextCoachId = 1;
    }

    /**
     * Returns the engine with the given id or null if it dosen't exist
     * 
     * @param id The engines id
     * @return The engine with the id
     */
    public Engine getEngine(String id) {
        return engineGarage.get(id);
    }

    /**
     * Returns the train set with given id or null if it dosen't exist
     * 
     * @param id the train-sets id
     * @return The train-set with the given id
     */
    public TrainSet getPc(String id) {
        return pcGarage.get(id);
    }

    /**
     * Returns the coach with the given id or null if it dosen't exist
     * 
     * @param id The coaches id
     * @return The coach with the given id
     */
    public Coach getCoach(String id) {
        return coachGarage.get(id);
    }

    /**
     * Combines all 3 getters to find the fitting more generic rolling stock
     * 
     * @param id The rolling stocks id to find
     * @return The rolling stock with the given id or null if it dosen't exist
     */
    public RollMaterial getRollMaterial(String id) {
        if (id.charAt(0) == 'W') {
            return getCoach(id);
        } else {
            return getPc(id) != null ? getPc(id) : getEngine(id);
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
            toAdd = new ElectricalEngine(modelType, name, front, back, len);
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
            for (RollMaterial rm : trainGarage.get(id).getWagons()) {
                rm.setTrainNumber(-1); //train number reset id
            }
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
    }

    public void deleteTrain(int id) {
        if (trainGarage.containsKey(id)) {
            for (Map.Entry<String, RollMaterial> s : inTrains.entrySet()) {
                if (trainGarage.get(id).getWagons().contains(s.getValue())) {
                    inTrains.remove(s.getKey());
                }
            }
            trainGarage.remove(id);
            nextFreeTrainId.add(id);
        } else {
            throw new SemanticsException("no train with such id");
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
        if (getRollMaterial(rmId) == null) {
            throw new SemanticsException("no rollmaterial with such id");
        } else {
            newTrain(id);
            trainGarage.get(id).add(getRollMaterial(rmId));
            getRollMaterial(rmId).setTrainNumber(id);
        }
    }

    private void newTrain(int id) {
        if (!trainGarage.containsKey(id) && id == nextTrainId) {
            trainGarage.put(nextTrainId, new Train(nextTrainId));
            nextTrainId++;
        } else if (!nextFreeTrainId.isEmpty()) {
            if (nextFreeTrainId.contains(id)) {
                trainGarage.put(id, new Train(id));
                nextFreeTrainId.remove(nextFreeTrainId.indexOf(id));
            }
        } else if (trainGarage.containsKey(id)) {
            // all fine
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
            }
            for (int i = 0; i < ret.size(); i++) {
                if (Pattern.matches("(\\s)+", ret.get(0))) {
                    ret.remove(0);
                }
            }
            return ret;
        }
    }
}