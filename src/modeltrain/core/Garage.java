package modeltrain.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modeltrain.trains.*;

public class Garage {

    private Map<String, Locomotive> locGarage;
    private Map<String, Coach> coachGarage;
    private Map<String, PoweredCart> pcGarage;
    private Map<String, RollMaterial> inTrains;
    private Map<Integer, Train> trainGarage;
    private int nextTrainId;

    public Garage() {
        locGarage = new HashMap<>();
        coachGarage = new HashMap<>();
        pcGarage = new HashMap<>();
        trainGarage = new HashMap<>();
        inTrains = new HashMap<>();
        nextTrainId = 1;
    }

    public void createEngine(Locomotive lo) {
        
    }

    public void createCoach(Coach co) {

    }

    public void createPoweredCart(PoweredCart pc) {

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
        if (locGarage.containsKey(id)) {
            locGarage.remove(id);
        } else if (pcGarage.containsKey(id)) {
            pcGarage.remove(id);
        } else if (coachGarage.containsKey(id)) {
            coachGarage.remove(id);
        } else {
            throw new SemanticsException("no rolling stock with such id");
        }
    }

    public List<String> listEngines() {
        List<String> ret = new ArrayList<>();
        for (String i : locGarage.keySet()) {
            ret.add(locGarage.get(i).toString());
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
        if (locGarage.containsKey(rmId)) {
            if (!trainGarage.containsKey(id)) {
                trainGarage.put(nextTrainId, new Train(nextTrainId));
                nextTrainId++;
            }
            trainGarage.get(id).add(locGarage.get(rmId));
            inTrains.put(rmId, locGarage.get(rmId));
        } else if (pcGarage.containsKey(rmId)) {
            if (!trainGarage.containsKey(id)) {
                trainGarage.put(nextTrainId, new Train(nextTrainId));
                nextTrainId++;
            }
            trainGarage.get(id).add(pcGarage.get(rmId));
            inTrains.put(rmId, pcGarage.get(rmId));
        } else if (coachGarage.containsKey(rmId)) {
            if (!trainGarage.containsKey(id)) {
                trainGarage.put(nextTrainId, new Train(nextTrainId));
                nextTrainId++;
            }
            trainGarage.get(id).add(coachGarage.get(rmId));
            inTrains.put(rmId, coachGarage.get(rmId));
        } else {
            throw new SemanticsException("no rollmaterial with such id");
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
        for (Integer i : trainGarage.keySet()) {
            ret.add(trainGarage.get(i).toString());
        }
        return ret;
    }

    public String[] showTrain(int id) {
        if (!trainGarage.containsKey(id)) {
            throw new SemanticsException("no train with the id " + id);
        } else {
            return trainGarage.get(id).show();
        }
    }

}
