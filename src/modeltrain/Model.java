package modeltrain;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import modeltrain.trains.RollMaterial;
import modeltrain.trains.Train;


public class Model {
    
    private Set<Track> tracks;
    private Set<Train> trains;
    private Set<RollMaterial> wagons;
    private HashMap<Train, List<Track>> trainsOnTracks;
    
    public Model() {
        tracks = new HashSet<>();
    }
    
}
