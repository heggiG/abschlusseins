package modeltrain.trains;

import java.util.List;
import java.util.ArrayList;

public class Train {
    
    private final int id;
    private List<RollMaterial> wagons;
    private Boolean hasPoweredCar;
    
    public Train(int id) {
        this.id = id;
        wagons = new ArrayList<>();
    }
    
    public void add(Locomotive lo) {
        if(hasPoweredCar == null) {
            hasPoweredCar = false;
        }
        if (hasPoweredCar) {
            return;
        }
    }
    
    public void add(Coach w) {
        if(hasPoweredCar == null) {
            hasPoweredCar = false;
        }
        if (hasPoweredCar) {
            return;
        }
        if(wagons.get(wagons.size() - 1).getBackCoupling() == false) {
           throw new IllegalStateException("last wagon needs a back coupler");
        }
        if (w.getFrontCoupling() == false) {
            throw new IllegalStateException("coach needs a coupling on the front");
        }
    }
    
    public void add(PoweredCart pc) {
        if(hasPoweredCar == null) {
            hasPoweredCar = true;
        }
        if (!hasPoweredCar) {
            return;
        }
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o.getClass().equals(this.getClass())))
            return false;
        if (o.hashCode() == this.hashCode())
            return true;
        return false;
    }
    
}
