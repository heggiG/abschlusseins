package modeltrain.core;

public class Model extends Garage {

    private Garage garage;
    private TrackNetwork tn;
    
    public Model() {
        garage = new Garage();
        tn = new TrackNetwork();
    }
}