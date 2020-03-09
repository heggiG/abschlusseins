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
    private Map<String, TrainSet> trainSetGarage;
    private Map<String, RollingStock> inTrains;
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
        trainSetGarage = new HashMap<>();
        trainGarage = new HashMap<>();
        inTrains = new HashMap<>();
        nextFreeTrainId = new ArrayList<>();
        nextTrainId = 1;
        nextCoachId = 1;
    }

    /**
     * Returns the generic rolling stock
     * 
     * @param id The stocks id
     * @return The stock with the given id
     */
    public RollingStock getRollingStock(String id) {
        if (coachGarage.containsKey(id)) {
            return coachGarage.get(id);
        } else {
            return engineGarage.get(id) != null ? engineGarage.get(id) : trainSetGarage.get(id);
        }
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
        return trainSetGarage.get(id);
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
     * Creates an engine and stores it in the engine garage
     * 
     * @param front       Whether the engine should have front coupling
     * @param back        Whether the engine should have back coupling
     * @param len         The engines length
     * @param modelSeries The engines model series
     * @param name        The engines name
     * @param type        The engines type (steam, electrical or diesel)
     * @return The created engines id
     * @throws SemanticsException If illegal engine type or if the id already exists
     */
    public String createEngine(boolean front, boolean back, int len, String modelSeries, String name, String type)
            throws SemanticsException {
        Engine toAdd;
        switch (type) {
            case "diesel":
                toAdd = new DieselEngine(modelSeries, name, front, back, len);
                break;
    
            case "steam":
                toAdd = new SteamEngine(modelSeries, name, front, back, len);
                break;
    
            case "electrical":
                toAdd = new ElectricalEngine(modelSeries, name, front, back, len);
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

    /**
     * Creates a coach and stores it in the coach garage
     * 
     * @param front Whether the coach should have front coupling
     * @param back  Whether the coach should have back coupling
     * @param len   The coachs length
     * @param type  The coachs type (passenger, freight or special)
     * @return The created coachs id
     * @throws SemanticsException If illegal coach type or id already exists
     */
    public int createCoach(boolean front, boolean back, int len, String type) throws SemanticsException {
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

    /**
     * Creates a trainset and stores it in the trainset garage
     * 
     * @param front       Whether the trainset should have front coupling
     * @param back        Whether the trainset should have back coupling
     * @param length      The trainsets length
     * @param modelSeries The trainsets model series
     * @param name        The trainsets name
     * @return The created trainsets id
     * @throws SemanticsException if the id already exists
     */
    public String createTrainSet(String modelSeries, String name, boolean front, boolean back, int length)
            throws SemanticsException {
        TrainSet toAdd = new TrainSet(modelSeries, name, front, back, length);
        if (trainSetGarage.containsKey(toAdd.getId())) {
            throw new SemanticsException("train-set id already exists");
        }
        trainSetGarage.put(toAdd.getId(), toAdd);
        return toAdd.getId();
    }

    /**
     * Removes a train from the garage
     * 
     * @param id The trains id
     * @throws SemanticsException if there's no train with such id
     */
    public void removeTrain(int id) throws SemanticsException {
        if (trainGarage.containsKey(id)) {
            for (RollingStock rm : trainGarage.get(id).getRollingStock()) {
                rm.setTrainNumber(-1); // train number reset parameter
            }
            trainGarage.remove(id);
        } else {
            throw new SemanticsException("no train with such id");
        }
    }

    /**
     * Delets rolling if its not placed in a train
     * 
     * @param id The rollingstocks id
     * @throws SemanticsException if there's no rolling stock with the given id
     */
    public void deleteRS(String id) throws SemanticsException {
        if (inTrains.containsKey(id)) {
            throw new SemanticsException("rolling stock is placed in a train");
        }
        if (engineGarage.containsKey(id)) {
            engineGarage.remove(id);
        } else if (trainSetGarage.containsKey(id)) {
            trainSetGarage.remove(id);
        } else if (coachGarage.containsKey(id)) {
            coachGarage.remove(id);
        } else {
            throw new SemanticsException("no rolling stock with such id");
        }
    }

    /**
     * Deletes a train from the garage
     * 
     * @param id The trains id
     * @throws SemanticsException if theres no train with such id
     */
    public void deleteTrain(int id) throws SemanticsException {
        if (trainGarage.containsKey(id)) {
            for (Map.Entry<String, RollingStock> s : inTrains.entrySet()) {
                if (trainGarage.get(id).getRollingStock().contains(s.getValue())) {
                    inTrains.remove(s.getKey());
                }
            }
            trainGarage.remove(id);
            nextFreeTrainId.add(id);
        } else {
            throw new SemanticsException("no train with such id");
        }
    }

    /**
     * 
     * @return a sorted list of all engines
     */
    public List<String> listEngines() {
        List<String> ret = new ArrayList<>();
        for (String i : engineGarage.keySet()) {
            ret.add(engineGarage.get(i).toString());
        }
        Collections.sort(ret);
        return ret;
    }

    /**
     * 
     * @return a sorted list of all train sets
     */
    public List<String> listTrainSets() {
        List<String> ret = new ArrayList<>();
        for (String i : trainSetGarage.keySet()) {
            ret.add(trainSetGarage.get(i).toString());
        }
        Collections.sort(ret);
        return ret;
    }

    /**
     * 
     * @return a sorted list of all coaches
     */
    public List<String> listCoaches() {
        List<String> ret = new ArrayList<>();
        for (String i : coachGarage.keySet()) {
            ret.add(coachGarage.get(i).toString());
        }
        Collections.sort(ret);
        return ret;
    }

    /**
     * Adds rolling stock to a train and creates the train if it dosen't exist yet
     * 
     * @param id   The trains id
     * @param rmId The rollingstocks id
     * @throws SemanticsException if the rollingstock is in another train or if
     *                            theres no such rollingstock
     */
    public void addToTrain(int id, String rmId) throws SemanticsException {
        if (inTrains.containsKey(rmId)) {
            throw new SemanticsException("rollmaterial is already in another train");
        }
        newTrain(id);
        if (coachGarage.containsKey(rmId)) {
            trainGarage.get(id).add(coachGarage.get(rmId));
            coachGarage.get(rmId).setTrainNumber(id);
        } else if (engineGarage.containsKey(rmId)) {
            trainGarage.get(id).add(engineGarage.get(rmId));
            engineGarage.get(rmId).setTrainNumber(id);
        } else if (trainSetGarage.containsKey(rmId)) {
            trainGarage.get(id).add(trainSetGarage.get(rmId));
            trainSetGarage.get(rmId).setTrainNumber(id);
        } else {
            throw new SemanticsException("no rollingstock with such id");
        }

    }

    // creates a new train if none with the given id exist yet
    private void newTrain(int id) throws SemanticsException {
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

    /**
     * 
     * @param id The trains id to get
     * @return Train getter
     */
    public Train getTrain(int id) {
        return trainGarage.get(id);
    }

    /**
     * 
     * @return a sorted list of all trains
     * @throws SemanticsException if no trains have been added yet
     */
    public List<String> listTrains() throws SemanticsException {
        if (trainGarage.size() == 0) {
            throw new SemanticsException("no trains have been added yet");
        }
        List<String> ret = new ArrayList<>();
        for (Train i : trainGarage.values()) {
            ret.add(i.toString());
        }
        return ret;
    }

    /**
     * Returns a list containing the trains ascii art representation
     * 
     * @param id The trains id
     * @return Ascii art in a list
     * @throws SemanticsException
     */
    public List<String> showTrain(int id) {
        if (!trainGarage.containsKey(id)) {
            return null;
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